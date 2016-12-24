package io.chesslave.model

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
     */
    data class Regular(val from: Square, val to: Square,
                       val variation: Variation? = null) : Move() {

        sealed class Variation {
            /**
             * If the move is a pawn capturing the opponent pawn en passant.
             */
            data class EnPassant(val dummy: Boolean = true) : Variation()

            /**
             * The eventual piece to replace the promoted pawn.
             */
            data class Promotion(val type: Piece.Type) : Variation()
        }

        override fun apply(position: Position): Position {
            val piece = position.at(from).get()
            val moved = position.move(from, to)
            return when (variation) {
                is Variation.EnPassant -> {
                    val direction = Pawns.direction(piece.color)
                    moved.remove(to.translate(0, -direction).get())
                }
                is Variation.Promotion -> moved.put(to, Piece(variation.type, piece.color))
                else -> moved
            }
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
