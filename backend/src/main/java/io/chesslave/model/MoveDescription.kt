package io.chesslave.model

sealed class MoveDescription {

    data class Regular(val fromSquare: Square, val toSquare: Square,
                       val capture: Boolean, val enPassant: Boolean,
                       val promotion: Piece.Type?, val status: Status) : MoveDescription()

    data class Castling(val short: Boolean, val status: Status) : MoveDescription()

    data class Square(val piece: Piece.Type? = null,
                      val col: Int? = null,
                      val row: Int? = null) {
        init {
            assert(piece != null || col != null || row != null) { "Wrong square description!" }
        }
    }

    enum class Status {
        RELAX,
        CHECK,
        CHECKMATE
    }
}
