package io.chesslave.model

object Pawns {

    fun direction(color: Color): Int = when (color) {
        Color.WHITE -> +1
        Color.BLACK -> -1
    }
}
