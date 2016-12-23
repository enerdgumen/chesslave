package io.chesslave.model

object Pawns {

    fun direction(color: Color): Int = when (color) {
        Color.WHITE -> +1
        Color.BLACK -> -1
    }

    fun inPromotion(color: Color, square: Square): Boolean =
        square.row == (if (color === Color.WHITE) 7 else 0)

    // TODO: check
    fun isCapture(pawnMove: Movements.Regular): Boolean =
        pawnMove.enPassant || pawnMove.from.col != pawnMove.to.col

    // TODO: review this code
    fun isEnPassantAvailable(pawnSquare: Square, position: Position): Boolean {
        val piece = position.at(pawnSquare)
        return piece.isDefined && Piece.Type.PAWN == piece.get().type
            && (Color.WHITE == piece.get().color && pawnSquare.row == 4
            || Color.BLACK == piece.get().color && pawnSquare.row == 3)
            && (pawnSquare.translate(-1, 0).flatMap { position.at(it) }
            .contains(piece.get().color.opponent().pawn())
            && pawnSquare.translate(-1, direction(piece.get().color))
            .flatMap { position.at(it) }.isEmpty
            || pawnSquare.translate(+1, 0).flatMap { position.at(it) }
            .contains(piece.get().color.opponent().pawn())
            && pawnSquare.translate(+1, direction(piece.get().color))
            .flatMap { position.at(it) }.isEmpty)
    }
}
