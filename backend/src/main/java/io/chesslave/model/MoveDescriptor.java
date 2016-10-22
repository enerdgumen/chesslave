package io.chesslave.model;

import io.chesslave.model.Movements.LongCastling;
import io.chesslave.model.Movements.ShortCastling;
import io.chesslave.model.Piece.Type;
import javaslang.collection.Set;
import static io.chesslave.model.Movements.Regular;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

public class MoveDescriptor {

    /**
     * Describe a move in a minimal non-ambiguous way.
     *
     * @param move     the move to made
     * @param position the position before the move
     * @return the move's description
     */
    public MoveDescription describe(Move move, Position position) {
        return Match(move).of(
                Case(instanceOf(ShortCastling.class), mv ->
                        ImmutableMoveDescription.Castling.builder()
                                .isShort(true)
                                .status(status(move, position, mv.color.opponent()))
                                .build()),
                Case(instanceOf(LongCastling.class), mv ->
                        ImmutableMoveDescription.Castling.builder()
                                .isShort(false)
                                .status(status(move, position, mv.color.opponent()))
                                .build()),
                Case(instanceOf(Regular.class), mv ->
                        ImmutableMoveDescription.Regular.builder()
                                .fromSquare(fromSquareDescription(mv, position))
                                .toSquare(toSquareDescription(mv.to))
                                .capture(isCapture(mv, position))
                                .enPassant(mv.enPassant)
                                .promotion(mv.promotion.toJavaOptional())
                                .status(status(move, position, position.at(mv.from).get().color.opponent()))
                                .build())
        );
    }

    private MoveDescription.Square fromSquareDescription(Regular move, Position position) {
        final Piece piece = position.at(move.from).get();
        final Set<Square> ambiguousSquares = ambiguousSquares(piece, move, position);
        final ImmutableMoveDescription.Square.Builder des = ImmutableMoveDescription.Square.builder();
        des.piece(piece.type);
        if (ambiguousSquares.size() == 1) {
            if (ambiguousSquares.head().col == move.from.col) {
                des.row(move.from.row);
            } else {
                des.col(move.from.col);
            }
        } else if (ambiguousSquares.size() > 1) {
            des.row(move.from.row);
            des.col(move.from.col);
        } else if (move.enPassant || (Type.PAWN.equals(piece.type) && position.at(move.to).isDefined())) {
            des.col(move.from.col);
        }
        return des.build();
    }

    private MoveDescription.Square toSquareDescription(Square square) {
        return ImmutableMoveDescription.Square.builder()
                .row(square.row)
                .col(square.col)
                .build();
    }

    private Set<Square> ambiguousSquares(Piece piece, Regular move, Position position) {
        return position.toSet()
                .filter(square -> !square._1.equals(move.from) && square._2.equals(piece))
                .flatMap(square -> Rules.moves(position, square._1))
                .filter(mv -> mv.to.equals(move.to))
                .map(mv -> mv.from);
    }

    private boolean isCapture(Movements.Regular move, Position position) {
        return move.enPassant || position.at(move.to).isDefined();
    }

    private MoveDescription.Status status(Move move, Position position, Color opponentColor) {
        final Position nextPosition = move.apply(position);
        if (Rules.isKingSafe(nextPosition, opponentColor)) {
            return MoveDescription.Status.RELAX;
        }
        if (Rules.allMoves(nextPosition, opponentColor).isEmpty()) {
            return MoveDescription.Status.CHECKMATE;
        }
        return MoveDescription.Status.CHECK;
    }
}
