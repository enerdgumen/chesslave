package io.chesslave.model.notations;

import io.chesslave.model.MoveDescription;

/**
 * A notation to describe chess moves.
 */
public interface MoveNotation {

    String print(MoveDescription description);
}
