package io.chesslave.hands

import io.chesslave.model.Square
import io.chesslave.visual.model.BoardImage

/**
 * A bot able to move pieces through a point and click strategy.
 */
class ClickBotMover(val pointer: Pointer, board: BoardImage) : BaseBotMover(board) {

    override fun move(from: Square, to: Square) {
        try {
            pointer.click(getSquareCoords(from))
            pointer.click(getSquareCoords(to))
        } catch (re: RuntimeException) {
            throw MoverException(re)
        }
    }
}
