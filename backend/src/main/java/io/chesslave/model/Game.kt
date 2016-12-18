package io.chesslave.model

import io.chesslave.model.Piece.Type
import javaslang.collection.List

/**
 * An immutable chess match tracker.
 */
class Game(private val initialPosition: Position, private val moves: List<Move>, private val turn: Color) {

    /**
     * Applies the move to the current position.

     * @param move the move to apply to the position
     * *
     * @return the resulting game
     */
    fun move(move: Move): Game =
        Game(initialPosition, moves.append(move), turn.opponent())

    /**
     * @return the current position.
     */
    fun position(): Position =
        moves.foldLeft(initialPosition) { pos, move -> move.apply(pos) }

    /**
     * @return the color to move next
     */
    fun turn(): Color = turn

    /**
     * @return the list of moves
     */
    fun moves(): List<Move> = moves

    companion object {

        /**
         * @return a game from the initial position.
         */
        @JvmStatic fun initialPosition(): Game {
            val position = Position.Builder()
                .withPiece(Square.of("a1"), Piece(Type.ROOK, Color.WHITE))
                .withPiece(Square.of("b1"), Piece(Type.KNIGHT, Color.WHITE))
                .withPiece(Square.of("c1"), Piece(Type.BISHOP, Color.WHITE))
                .withPiece(Square.of("d1"), Piece(Type.QUEEN, Color.WHITE))
                .withPiece(Square.of("e1"), Piece(Type.KING, Color.WHITE))
                .withPiece(Square.of("f1"), Piece(Type.BISHOP, Color.WHITE))
                .withPiece(Square.of("g1"), Piece(Type.KNIGHT, Color.WHITE))
                .withPiece(Square.of("h1"), Piece(Type.ROOK, Color.WHITE))
                .withPiece(Square.of("a2"), Piece(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("b2"), Piece(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("c2"), Piece(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("d2"), Piece(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("e2"), Piece(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("f2"), Piece(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("g2"), Piece(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("h2"), Piece(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("a8"), Piece(Type.ROOK, Color.BLACK))
                .withPiece(Square.of("b8"), Piece(Type.KNIGHT, Color.BLACK))
                .withPiece(Square.of("c8"), Piece(Type.BISHOP, Color.BLACK))
                .withPiece(Square.of("d8"), Piece(Type.QUEEN, Color.BLACK))
                .withPiece(Square.of("e8"), Piece(Type.KING, Color.BLACK))
                .withPiece(Square.of("f8"), Piece(Type.BISHOP, Color.BLACK))
                .withPiece(Square.of("g8"), Piece(Type.KNIGHT, Color.BLACK))
                .withPiece(Square.of("h8"), Piece(Type.ROOK, Color.BLACK))
                .withPiece(Square.of("a7"), Piece(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("b7"), Piece(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("c7"), Piece(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("d7"), Piece(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("e7"), Piece(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("f7"), Piece(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("g7"), Piece(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("h7"), Piece(Type.PAWN, Color.BLACK))
                .build()
            return Game(position, List.empty(), Color.WHITE)
        }
    }
}