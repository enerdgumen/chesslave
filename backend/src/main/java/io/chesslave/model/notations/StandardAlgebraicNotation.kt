package io.chesslave.model.notations

import io.chesslave.model.MoveDescription
import io.chesslave.model.Piece
import javaslang.collection.HashMap
import java.util.Optional

/**
 * Format a move description using the standard Algebraic Notation.
 */
class StandardAlgebraicNotation : MoveNotation {

    override fun print(move: MoveDescription) = when (move) {
        is MoveDescription.Castling -> {
            val notation = StringBuilder()
            notation.append(if (move.short) SHORT_CASTLING else LONG_CASTLING)
            notation.append(statusNotation(move.status))
            notation.toString()
        }
        is MoveDescription.Regular -> {
            val notation = StringBuilder()
            notation.append(squareNotation(move.fromSquare))
            notation.append(if (move.capture) CAPTURE_SYMBOL else "")
            notation.append(squareNotation(move.toSquare))
            // TODO: en passant
            // TODO: promotion
            notation.append(statusNotation(move.status))
            notation.toString()
        }
    }

    private fun squareNotation(square: MoveDescription.Square): String {
        val notation = StringBuilder()
        Optional.ofNullable(square.piece).ifPresent { notation.append(PIECE_NAMES.get(it).getOrElse("")) }
        Optional.ofNullable(square.col).ifPresent { notation.append('a' + it!!) }
        Optional.ofNullable(square.row).ifPresent { notation.append('1' + it!!) }
        return notation.toString()
    }

    private fun statusNotation(status: MoveDescription.Status) =
        STATUS_SYMBOLS.get(status).getOrElse("")

    private companion object {

        val SHORT_CASTLING = "0-0"
        val LONG_CASTLING = "0-0-0"
        val CAPTURE_SYMBOL = "x"
        val STATUS_SYMBOLS = HashMap.of<MoveDescription.Status, String>(
            MoveDescription.Status.CHECK, "+",
            MoveDescription.Status.CHECKMATE, "#")
        val PIECE_NAMES = HashMap.of<Piece.Type, String>(
            Piece.Type.KNIGHT, "N",
            Piece.Type.BISHOP, "B",
            Piece.Type.ROOK, "R",
            Piece.Type.QUEEN, "Q",
            Piece.Type.KING, "K")
    }
}
