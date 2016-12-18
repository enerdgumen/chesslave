package io.chesslave.hands

import io.chesslave.model.Square
import java.awt.Point

/**
 * A bot able to move pieces through a drag and drop strategy.
 */
class DragMover(val pointer: Pointer, val points: SquarePoints) : Mover {

    override fun move(from: Square, to: Square) {
        try {
            pointer.dragFrom(points.of(from))
            pointer.dropAt(points.of(to))
        } catch (re: RuntimeException) {
            throw MoverException(re)
        }
    }
}
