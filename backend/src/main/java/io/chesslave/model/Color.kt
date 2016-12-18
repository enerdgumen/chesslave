package io.chesslave.model

import io.chesslave.model.Piece.Type

enum class Color {

    WHITE,
    BLACK;

    fun opponent(): Color = if (this == WHITE) BLACK else WHITE

    fun pawn() = Piece(Type.PAWN, this)

    fun bishop() = Piece(Type.BISHOP, this)

    fun knight() = Piece(Type.KNIGHT, this)

    fun rook() = Piece(Type.ROOK, this)

    fun queen() = Piece(Type.QUEEN, this)

    fun king() = Piece(Type.KING, this)
}
