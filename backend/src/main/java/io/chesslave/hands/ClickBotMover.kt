package io.chesslave.hands

import io.chesslave.model.Square

/**
 * A bot able to move pieces through a point and click strategy.
 */
class ClickBotMover(val pointer: Pointer, val points: SquarePoints) : Mover {

    override fun move(from: Square, to: Square) {
        try {
            pointer.click(points.of(from))
            pointer.click(points.of(to))
        } catch (re: RuntimeException) {
            throw MoverException(re)
        }
    }
}
