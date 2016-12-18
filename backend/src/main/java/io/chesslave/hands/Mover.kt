package io.chesslave.hands

import io.chesslave.model.Square

/**
 * An entity able to move pieces on the board.
 */
interface Mover {

    /**
     * Move a piece.
     *
     * @param from starting square
     * @param to destination square
     * @throws MoverException if something goes wrong executing the move
     */
    fun move(from: Square, to: Square)
}