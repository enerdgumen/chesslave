package io.chesslave.model

import javaslang.control.Option

/**
 * A chess move.
 */
sealed class Move {

    /**
     * Applies the move to the specified position and returns the resulting position.
     * It assumes that the position is compatible with this move.
     */
    abstract fun apply(position: Position): Position

    /**
     * Moves a piece from a square to another square.
     * @param enPassant True if the move is a pawn capturing the opponent pawn en passant
     * @param promotion The eventual piece to replace the promoted pawn
     */
    // TODO: separate Regular from EnPassant and Promotion
    data class Regular(val from: Square, val to: Square,
                       val enPassant: Boolean = false,
                       val promotion: Option<Piece.Type> = Option.none()) : Move() {

        override fun apply(position: Position): Position {
            val piece = position.at(from).get()
            val direction = Pawns.direction(piece.color)
            val moved = position.move(from, to)
            val promoted = promotion.toSet().foldLeft(moved) { pos, type -> pos.put(to, Piece(type, piece.color)) }
            val captured = if (enPassant) promoted.remove(to.translate(0, -direction).get()) else promoted
            return captured
        }
    }

    /**
     * Performs a castling on the king side of the specified color.
     */
    data class ShortCastling(val color: Color) : Move() {

        override fun apply(position: Position): Position {
            val row = if (color === Color.WHITE) 0 else 7
            return position
                .move(Square(4, row), Square(6, row))
                .move(Square(7, row), Square(5, row))
        }
    }

    /**
     * Performs a castling on the queen side of the specified color.
     */
    data class LongCastling(val color: Color) : Move() {

        override fun apply(position: Position): Position {
            val row = if (color === Color.WHITE) 0 else 7
            return position
                .move(Square(4, row), Square(2, row))
                .move(Square(0, row), Square(3, row))
        }
    }
}
