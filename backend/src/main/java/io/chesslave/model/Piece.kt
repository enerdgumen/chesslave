package io.chesslave.model

import javaslang.collection.HashSet
import javaslang.collection.Set

/**
 * A chess piece.
 */
data class Piece(val type: Piece.Type, val color: Color) {

    enum class Type(val value: Int) {
        PAWN(1),
        KNIGHT(3),
        BISHOP(3),
        ROOK(5),
        QUEEN(9),
        KING(0)
    }

    /**
     * @return True if the given piece has the same color of this piece.
     */
    fun isFriend(piece: Piece): Boolean = this.color === piece.color

    /**
     * @return True if the given piece has not the same color of this piece.
     */
    fun isOpponent(piece: Piece): Boolean = !isFriend(piece)

    companion object {

        /**
         * @return All chess pieces available.
         */
        fun all(): Set<Piece> =
            HashSet.ofAll(Piece.Type.values().flatMap { type ->
                Color.values().map { color -> Piece(type, color) }
            })
    }
}
