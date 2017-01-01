package io.chesslave.model

import io.chesslave.extensions.concat
import io.chesslave.extensions.getOrNull
import io.chesslave.model.MoveDescription.Status
import io.chesslave.model.Piece.Type
import javaslang.collection.HashMap

/**
 * A notation to describe chess moves.
 */
typealias MoveNotation = (move: MoveDescription) -> String

private object Symbols {
    val shortCastling = "0-0"
    val longCastling = "0-0-0"
    val Capture = "x"
    val status = HashMap.of<Status, String>(
        Status.CHECK, "+",
        Status.CHECKMATE, "#")
    val pieces = HashMap.of<Type, String>(
        Type.KNIGHT, "N",
        Type.BISHOP, "B",
        Type.ROOK, "R",
        Type.QUEEN, "Q",
        Type.KING, "K")
}

/**
 * Format a move description using the standard Algebraic Notation.
 */
val standardAlgebraicNotation: MoveNotation = { move ->

    fun notation(square: MoveDescription.Square)
        = (square.piece?.let { Symbols.pieces.getOrNull(it) } ?: "") concat
        (square.col?.let { ('a' + it).toString() } ?: "") concat
        (square.row?.let { ('1' + it).toString() } ?: "")

    fun notation(status: Status) = Symbols.status.getOrNull(status) ?: ""

    when (move) {
        is MoveDescription.Castling
        -> (if (move.short) Symbols.shortCastling else Symbols.longCastling) concat
            notation(move.status)

        is MoveDescription.Regular
        -> notation(move.fromSquare) concat
            (if (move.capture) Symbols.Capture else "") concat
            notation(move.toSquare) concat
            // TODO: en passant
            // TODO: promotion
            notation(move.status)
    }
}