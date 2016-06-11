package io.chesslave.hands;

import io.chesslave.model.Square;

/**
 * An entity able to move pieces on the board.
 */
public interface Mover {

    /**
     * Move a piece.
     *
     * @param from starting square
     * @param to destination square
     * @throws MoverException if something goes wrong executing the move
     */
    void move(Square from, Square to) throws MoverException;
}