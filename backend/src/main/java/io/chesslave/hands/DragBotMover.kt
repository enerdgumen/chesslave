package io.chesslave.hands

import io.chesslave.model.Square
import io.chesslave.visual.model.BoardImage

/**
 * A bot able to move pieces through a drag and drop strategy.
 */
class DragBotMover(val pointer: Pointer, board: BoardImage) : BaseBotMover(board) {

    override fun move(from: Square, to: Square) {
        try {
            pointer.dragFrom(getSquareCoords(from))
            pointer.dropAt(getSquareCoords(to))
        } catch (re: RuntimeException) {
            throw MoverException(re)
        }
    }
}
