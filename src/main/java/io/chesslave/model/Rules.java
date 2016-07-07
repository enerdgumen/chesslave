package io.chesslave.model;

import static io.chesslave.model.Movements.Regular;
import static javaslang.Predicates.is;

import io.chesslave.support.Functions;
import javaslang.Function1;
import javaslang.Predicates;
import javaslang.Tuple;
import javaslang.collection.HashSet;
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
     * @return all the available moves (excluding castling) of the piece placed at the given square for the specified position.
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
                            return queenSquares(pos, from)
                                    .map(regular)
                                    .filter(isAvailable);
                    }
                })
                .getOrElse(HashSet.empty());
    }

    /**
     * @return true if the king of the given color is not under attack.
     */
    public static boolean isKingSafe(Position position, Color color) {
        final Square kingSquare = Square.all().find(sq -> position.at(sq).exists(is(color.king()))).get();
        return !isTargetForColor(position, kingSquare, color.opponent());
    }

    /**
     * @return true if the given square is under attack by pieces of the specified color.
     */
    public static boolean isTargetForColor(Position position, Square square, Color color) {
        return attackingKnightSquares(square, color, position).nonEmpty()
                || attackingBishopSquares(square, color, position).nonEmpty()
                || attackingRookSquares(square, color, position).nonEmpty()
                || attackingQueenSquares(square, color, position).nonEmpty()
                || attackingKingSquares(square, color, position).nonEmpty()
                || attackingPawnSquares(square, color, position).nonEmpty();
    }

    public static Set<Square> attackingPawnSquares(Square target, Color attackingColor, Position position) {
        final int pawnDirection = Pawns.direction(attackingColor.opponent());
        Set<Square> squares = target.translateAll(Tuple.of(-1, pawnDirection), Tuple.of(+1, pawnDirection));

        final Option<Piece> targetPiece = position.at(target);
        if (targetPiece.isDefined() && Piece.Type.PAWN.equals(targetPiece.get().type)) {
            squares = squares.addAll(target.translateAll(Tuple.of(-1, 0), Tuple.of(+1, 0))
                    .filter(square -> Pawns.isEnPassantAvailable(square, position)));
        }
        return squares.filter(new FindPiece(Piece.Type.PAWN, attackingColor, position));
    }

    public static Set<Square> attackingKingSquares(Square target, Color attackingColor, Position position) {
        return kingSquares(target)
                .filter(new FindPiece(Piece.Type.KING, attackingColor, position));
    }

    public static Set<Square> attackingKnightSquares(Square target, Color attackingColor, Position position) {
        return knightSquares(target)
                .filter(new FindPiece(Piece.Type.KNIGHT, attackingColor, position));
    }

    public static Set<Square> attackingBishopSquares(Square target, Color attackingColor, Position position) {
        return baseAttackingBishopSquares(target, position)
                .filter(new FindPiece(Piece.Type.BISHOP, attackingColor, position));
    }

    public static Set<Square> attackingRookSquares(Square target, Color attackingColor, Position position) {
        return baseAttackingRookSquares(target, position)
                .filter(new FindPiece(Piece.Type.ROOK, attackingColor, position));
    }

    public static Set<Square> attackingQueenSquares(Square target, Color attackingColor, Position position) {
        return baseAttackingBishopSquares(target, position)
                .addAll(baseAttackingRookSquares(target, position))
                .filter(new FindPiece(Piece.Type.QUEEN, attackingColor, position));
    }

    private static Set<Square> baseAttackingBishopSquares(Square target, Position position) {
        final Predicate<Square> pieceIsFound = square -> position.at(square).isDefined();
        final Option<Square> ne = target.walk(+1, +1).filter(pieceIsFound).headOption();
        final Option<Square> se = target.walk(+1, -1).filter(pieceIsFound).headOption();
        final Option<Square> sw = target.walk(-1, -1).filter(pieceIsFound).headOption();
        final Option<Square> nw = target.walk(-1, +1).filter(pieceIsFound).headOption();
        return HashSet.of(ne, se, sw, nw).flatMap(Function.identity());
    }

    private static Set<Square> baseAttackingRookSquares(Square target, Position position) {
        final Predicate<Square> pieceIsFound = square -> position.at(square).isDefined();
        final Option<Square> n = target.walk(0, +1).filter(pieceIsFound).headOption();
        final Option<Square> s = target.walk(0, -1).filter(pieceIsFound).headOption();
        final Option<Square> w = target.walk(+1, 0).filter(pieceIsFound).headOption();
        final Option<Square> e = target.walk(-1, 0).filter(pieceIsFound).headOption();
        return HashSet.of(n, s, w, e).flatMap(Function.identity());
    }

    private static boolean isFreeOrWithOpponent(Position position, Square square, Piece piece) {
        return !position.at(square).exists(piece::isFriend);
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

    private static Set<Square> queenSquares(Position position, Square from) {
        return bishopSquares(position, from).addAll(rookSquares(position, from));
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

        Set<Regular> enPassantCaptures = HashSet.empty();
        if (Pawns.isEnPassantAvailable(from, position)) {
            enPassantCaptures = from.translateAll(Tuple.of(-1, 0), Tuple.of(+1, 0))
                    .filter(sq -> position.at(sq).exists(is(piece.color.opponent().pawn())))
                    .map(sq -> sq.translate(0, direction).get())
                    .map(to -> Movements.enPassant(from, to));
        }
        return forward.appendAll(captures).appendAll(enPassantCaptures).toSet();
    }

    private static Stream<Square> takeWhileFreeOrWithOpponent(Position position, Piece moving, Stream<Square> walk) {
        return walk
                .splitAt(sq -> position.at(sq).isDefined())
                .map(sqs -> sqs,
                     sqs -> sqs.headOption().filter(sq -> moving.isOpponent(position.at(sq).get())).toList())
                .transform(Stream::appendAll);
    }

    private static class FindPiece implements Predicate<Square> {
        private final Piece.Type type;
        private final Color color;
        private final Position position;

        public FindPiece(Piece.Type type, Color color, Position position) {
            this.type = type;
            this.color = color;
            this.position = position;
        }

        @Override
        public boolean test(Square square) {
            return position.at(square).isDefined()
                    && color.equals(position.at(square).get().color)
                    && type.equals(position.at(square).get().type);
        }
    }
}
