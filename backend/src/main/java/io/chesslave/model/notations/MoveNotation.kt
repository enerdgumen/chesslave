package io.chesslave.model.notations

import io.chesslave.model.MoveDescription

/**
 * A notation to describe chess moves.
 */
interface MoveNotation {

    fun print(move: MoveDescription): String
}
