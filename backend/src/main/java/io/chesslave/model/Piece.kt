package io.chesslave.model

import javaslang.collection.HashSet
import javaslang.collection.Set

enum class Color {

    WHITE,
    BLACK;

    fun opponent(): Color = if (this == WHITE) BLACK else WHITE
}

/**
 * A chess piece.
 */
data class Piece(val type: Type, val color: Color) {

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

        val whitePawn = pawnOf(Color.WHITE)
        val whiteBishop = bishopOf(Color.WHITE)
        val whiteKnight = knightOf(Color.WHITE)
        val whiteRook = rookOf(Color.WHITE)
        val whiteQueen = queenOf(Color.WHITE)
        val whiteKing = kingOf(Color.WHITE)
        val blackPawn = pawnOf(Color.BLACK)
        val blackBishop = bishopOf(Color.BLACK)
        val blackKnight = knightOf(Color.BLACK)
        val blackRook = rookOf(Color.BLACK)
        val blackQueen = queenOf(Color.BLACK)
        val blackKing = kingOf(Color.BLACK)

        fun pawnOf(color: Color) = Piece(Type.PAWN, color)
        fun bishopOf(color: Color) = Piece(Type.BISHOP, color)
        fun knightOf(color: Color) = Piece(Type.KNIGHT, color)
        fun rookOf(color: Color) = Piece(Type.ROOK, color)
        fun queenOf(color: Color) = Piece(Type.QUEEN, color)
        fun kingOf(color: Color) = Piece(Type.KING, color)

        /**
         * All chess pieces available.
         */
        val all: Set<Piece>
            = Type.values()
            .flatMap { type -> Color.values().map { color -> Piece(type, color) } }
            .let { HashSet.ofAll(it) }
    }
}