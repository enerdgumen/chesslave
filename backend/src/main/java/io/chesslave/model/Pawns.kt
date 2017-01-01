package io.chesslave.model

import io.chesslave.extensions.undefined
import io.chesslave.model.Move.Regular.Variation.EnPassant

object Pawns {

    fun direction(color: Color): Int = when (color) {
        Color.WHITE -> +1
        Color.BLACK -> -1
    }

    fun inPromotion(color: Color, square: Square): Boolean =
        square.row == (if (color === Color.WHITE) 7 else 0)

    // TODO: check
    fun isCapture(pawnMove: Move.Regular): Boolean =
        pawnMove.variation is EnPassant
            || pawnMove.from.col != pawnMove.to.col

    // TODO: review this code
    fun isEnPassantAvailable(square: Square, position: Position): Boolean {
        val piece = position.at(square)
        return piece != null && Piece.Type.PAWN == piece.type &&
            (Color.WHITE == piece.color && square.row == 4 || Color.BLACK == piece.color && square.row == 3)
            &&
            (square.translate(-1, 0)?.let { position.at(it) } == Piece.pawnOf(piece.color.opponent()) && square.translate(-1, direction(piece.color))?.let { position.at(it) }.undefined
                ||
                square.translate(+1, 0)?.let { position.at(it) } == Piece.pawnOf(piece.color.opponent()) && square.translate(+1, direction(piece.color))?.let { position.at(it) }.undefined)

    }
}
