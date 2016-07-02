package io.chesslave.model;

import static javaslang.Predicates.is;

import io.chesslave.model.Movements.Regular;
import io.chesslave.support.Functions;
import javaslang.Function1;
import javaslang.Predicates;
import javaslang.Tuple;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Set;
import javaslang.collection.Stream;
import javaslang.control.Option;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Defines the chess logics.
 */
public final class Rules {

    private Rules() {
    }

    /**
     * TODO: Handle pawn promotion.
     *
     * @return All the available moves (excluding castling) of the piece placed at the given square for the specified position.
     */
    public static Set<Regular> moves(Position pos, Square from) {
        return pos.at(from)
                .map(piece -> {
                    final Function1<Square, Regular> regular = to -> Movements.regular(from, to);
                    final Predicate<Regular> isKingSafeAfterMove = move -> isKingSafe(move.apply(pos), piece.color);
                    final Predicate<Regular> isFreeOrWithOpponent = move -> isFreeOrWithOpponent(pos, move.to, piece);
                    final Predicate<Regular> isAvailable = Predicates.allOf(isKingSafeAfterMove, isFreeOrWithOpponent);
                    switch (piece.type) {
                        case PAWN:
                            return pawnMoves(pos, from).filter(isKingSafeAfterMove);
                        case KING:
                            return kingSquares(from).map(regular).filter(isAvailable);
                        case KNIGHT:
                            return knightSquares(from).map(regular).filter(isAvailable);
                        case BISHOP:
                            return bishopSquares(pos, from).map(regular).filter(isAvailable);
                        case ROOK:
                            return rookSquares(pos, from).map(regular).filter(isAvailable);
                        case QUEEN:
                        default:
                            return bishopSquares(pos, from)
                                    .addAll(rookSquares(pos, from))
                                    .map(regular)
                                    .filter(isAvailable);
                    }
                })
                .getOrElse(HashSet.empty());
    }

    private static boolean isFreeOrWithOpponent(Position position, Square square, Piece piece) {
        return !position.at(square).exists(piece::isFriend);
    }

    /**
     * @return True if the king of the given color is not under attack.
     */
    public static boolean isKingSafe(Position position, Color color) {
        final Square king = Square.all().find(sq -> position.at(sq).exists(is(color.king()))).get();
        return !isTargetForColor(position, king, color.opponent());
    }

    /**
     * @return True if the given square is under attack by pieces of the specified color.
     */
    public static boolean isTargetForColor(Position position, Square square, Color color) {
        final Set<Piece> maybeKnight = knightSquares(square).flatMap(position::at);
        final Set<Piece> maybeKing = kingSquares(square).flatMap(position::at);
        final int pawnDir = Pawns.direction(color.opponent());
        final Set<Piece> maybePawn = square.translateAll(Tuple.of(+1, pawnDir), Tuple.of(-1, pawnDir)).flatMap(position::at);
        final Option<Piece> n = square.walk(+0, +1).flatMap(position::at).headOption();
        final Option<Piece> ne = square.walk(+1, +1).flatMap(position::at).headOption();
        final Option<Piece> e = square.walk(+1, +0).flatMap(position::at).headOption();
        final Option<Piece> se = square.walk(+1, -1).flatMap(position::at).headOption();
        final Option<Piece> s = square.walk(+0, -1).flatMap(position::at).headOption();
        final Option<Piece> sw = square.walk(-1, -1).flatMap(position::at).headOption();
        final Option<Piece> w = square.walk(-1, +0).flatMap(position::at).headOption();
        final Option<Piece> nw = square.walk(-1, +1).flatMap(position::at).headOption();
        final Set<Piece> maybeRookOrQueen = HashSet.of(n, e, s, w).flatMap(Function.identity());
        final Set<Piece> maybeBishopOrQueen = HashSet.of(ne, se, sw, nw).flatMap(Function.identity());
        return maybeKnight.exists(is(color.knight()))
                || maybePawn.exists(is(color.pawn()))
                || maybeKing.exists(is(color.king()))
                || maybeRookOrQueen.exists(List.of(color.rook(), color.queen())::contains)
                || maybeBishopOrQueen.exists(List.of(color.bishop(), color.queen())::contains);
    }

    private static Set<Square> kingSquares(Square from) {
        return from.translateAll(
                Tuple.of(+0, +1),
                Tuple.of(+1, +1),
                Tuple.of(+1, +0),
                Tuple.of(+1, -1),
                Tuple.of(+0, -1),
                Tuple.of(-1, -1),
                Tuple.of(-1, +0),
                Tuple.of(-1, +1));
    }

    private static Set<Square> knightSquares(Square from) {
        return from.translateAll(
                Tuple.of(+1, +2),
                Tuple.of(+2, +1),
                Tuple.of(+2, -1),
                Tuple.of(+1, -2),
                Tuple.of(-1, -2),
                Tuple.of(-2, -1),
                Tuple.of(-2, +1),
                Tuple.of(-1, +2));
    }

    private static Set<Square> bishopSquares(Position position, Square from) {
        final Piece piece = position.at(from).get();
        return HashSet.of(from.walk(+1, +1), from.walk(+1, -1), from.walk(-1, -1), from.walk(-1, +1))
                .flatMap(Functions.of(Rules::takeWhileFreeOrWithOpponent).apply(position, piece));
    }

    private static Set<Square> rookSquares(Position position, Square from) {
        final Piece piece = position.at(from).get();
        return HashSet.of(from.walk(0, +1), from.walk(+1, 0), from.walk(0, -1), from.walk(-1, 0))
                .flatMap(Functions.of(Rules::takeWhileFreeOrWithOpponent).apply(position, piece));
    }

    private static Set<Regular> pawnMoves(Position position, Square from) {
        final Piece piece = position.at(from).get();
        final int direction = Pawns.direction(piece.color);
        final int initialRow = piece.color == Color.WHITE ? 1 : 6;
        final int push = from.row == initialRow ? 2 : 1;
        final Stream<Regular> forward = from.walk(0, direction)
                .takeWhile(sq -> position.at(sq).isEmpty())
                .take(push)
                .map(to -> Movements.regular(from, to));
        final Set<Regular> captures = from.translateAll(Tuple.of(-1, direction), Tuple.of(+1, direction))
                .filter(sq -> position.at(sq).exists(piece::isOpponent))
                .map(to -> Movements.regular(from, to));
        final int enPassantRow = piece.color == Color.WHITE ? 4 : 3;
        final Set<Regular> enPassantCaptures = from.translateAll(Tuple.of(-1, 0), Tuple.of(+1, 0))
                .filter(sq -> sq.row == enPassantRow)
                .filter(sq -> position.at(sq).exists(is(piece.color.opponent().pawn())))
                .map(sq -> sq.translate(0, direction).get())
                .map(to -> Movements.enPassant(from, to));
        return forward.appendAll(captures).appendAll(enPassantCaptures).toSet();
    }

    private static Stream<Square> takeWhileFreeOrWithOpponent(Position position, Piece moving, Stream<Square> walk) {
        return walk
                .splitAt(sq -> position.at(sq).isDefined())
                .map(
                        sqs -> sqs,
                        sqs -> sqs.headOption().filter(sq -> moving.isOpponent(position.at(sq).get())).toList())
                .transform(Stream::appendAll);
    }
}
