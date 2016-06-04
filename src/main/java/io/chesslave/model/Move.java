package io.chesslave.model;

/**
 * A chess move.
 */
public interface Move {

    /**
     * Applies the move to the specified position and returns the resulting position.
     * It assumes that the position is compatible with this move.
     */
    Position apply(Position position);
}
