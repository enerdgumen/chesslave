package io.chesslave.model.notations;

import io.chesslave.model.Position;
import io.chesslave.model.Move;

/**
 * A notation to describe chess moves.
 */
public interface MoveNotation {

    /**
     * @param move the move to made
     * @param position the position before the move
     * @return the move's notation
     */
    String print(Move move, Position position);
}
