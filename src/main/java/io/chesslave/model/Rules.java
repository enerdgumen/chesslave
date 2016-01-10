package io.chesslave.model;

import io.chesslave.Functions;
import io.chesslave.model.Piece.Type;
import javaslang.Tuple;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Set;
import javaslang.collection.Stream;
import javaslang.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;

public class Rules {

    public static Set<Square> targets(Position pos, Square from) {
        return pos.at(from)
                .map(piece -> {
                    final Predicate<Square> isAvailable = to -> isFreeOrWithOpponent(pos, to, piece)
                            && isKingSafe(pos.move(Move.of(from, to)), piece.color);
                    switch (piece.type) {
                        case PAWN:
                            return pawnSquares(pos, from);
                        case KING:
                            return kingSquares(from).filter(isAvailable);
                        case KNIGHT:
                            return knightSquares(from).filter(isAvailable);
                        case BISHOP:
                            return bishopSquares(pos, from).filter(isAvailable);
                        case ROOK:
                            return rookSquares(pos, from).filter(isAvailable);
                        case QUEEN:
                            return HashSet.<Square>empty()
                                    .addAll(bishopSquares(pos, from))
                                    .addAll(rookSquares(pos, from))
                                    .filter(isAvailable);
                    }
                    return HashSet.<Square>empty();
                })
                .orElse(HashSet.empty());
    }

    private static boolean isFreeOrWithOpponent(Position position, Square square, Piece piece) {
        return !position.at(square).exists(piece::isFriend);
    }

    private static boolean isKingSafe(Position position, Color color) {
        final Square king = Square.all().findFirst(sq -> position.at(sq).exists(Piece.of(Type.KING, color)::equals)).get();
        return !isTargetForOpponent(position, king, color);
    }

    private static boolean isTargetForOpponent(Position position, Square square, Color color) {
        final Color opponent = color.opponent();
        final Piece rook = Piece.of(Type.ROOK, opponent);
        final Piece queen = Piece.of(Type.QUEEN, opponent);
        final Piece knight = Piece.of(Type.KNIGHT, opponent);
        final Piece bishop = Piece.of(Type.BISHOP, opponent);
        final Piece pawn = Piece.of(Type.PAWN, opponent);
        final Piece king = Piece.of(Type.KING, opponent);
        final Set<Piece> maybeKnight = knightSquares(square).flatMap(position::at);
        final Set<Piece> maybeKing = kingSquares(square).flatMap(position::at);
        final Option<Piece> n = square.walk(+0, +1).flatMap(position::at).headOption();
        final Option<Piece> ne = square.walk(+1, +1).flatMap(position::at).headOption();
        final Option<Piece> e = square.walk(+1, +0).flatMap(position::at).headOption();
        final Option<Piece> se = square.walk(+1, -1).flatMap(position::at).headOption();
        final Option<Piece> s = square.walk(+0, -1).flatMap(position::at).headOption();
        final Option<Piece> sw = square.walk(-1, -1).flatMap(position::at).headOption();
        final Option<Piece> w = square.walk(-1, +0).flatMap(position::at).headOption();
        final Option<Piece> nw = square.walk(-1, +1).flatMap(position::at).headOption();
        final int pawnDir = opponent == Color.WHITE ? -1 : +1;
        final Set<Piece> maybePawn = square.translateAll(Tuple.of(+1, pawnDir), Tuple.of(-1, pawnDir)).flatMap(position::at);
        return maybeKnight.exists(knight::equals)
                || maybePawn.exists(pawn::equals)
                || maybeKing.exists(king::equals)
                || List.of(n, e, s, w).flatMap(Function.identity()).exists(List.of(rook, queen)::contains)
                || List.of(ne, se, sw, nw).flatMap(Function.identity()).exists(List.of(bishop, queen)::contains);
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

    private static Set<Square> pawnSquares(Position position, Square from) {
        final Piece piece = position.at(from).get();
        final int direction = piece.color == Color.WHITE ? +1 : -1;
        final int initialRow = piece.color == Color.WHITE ? 1 : 6;
        final int push = from.row == initialRow ? 2 : 1;
        final Stream<Square> forward = from.walk(0, direction)
                .takeWhile(sq -> position.at(sq).isEmpty())
                .take(push);
        final Set<Square> captures = from.translateAll(Tuple.of(-1, direction), Tuple.of(+1, direction))
                .filter(sq -> position.at(sq).exists(piece::isOpponent));
        final int enPassantRow = piece.color == Color.WHITE ? 4 : 3;
        final Set<Square> enPassantCaptures = from.translateAll(Tuple.of(-1, 0), Tuple.of(+1, 0))
                .filter(sq -> sq.row == enPassantRow)
                .filter(sq -> position.at(sq).exists(Piece.of(Type.PAWN, piece.color.opponent())::equals))
                .map(sq -> sq.translate(0, direction).get());
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
