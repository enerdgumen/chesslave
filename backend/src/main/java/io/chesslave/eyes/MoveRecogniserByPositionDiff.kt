package io.chesslave.eyes

import io.chesslave.model.Move
import io.chesslave.model.Position
import io.chesslave.model.moves
import org.slf4j.LoggerFactory

class MoveRecogniserByPositionDiff {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Detects the move by analyzing the differences between the two positions.

     * @return the detected move or null if none move was done
     * @throws Exception if the detection fails
     */
    fun detect(previous: Position, current: Position): Move? {
        if (previous == current) {
            return null
        }
        val from = previous.toSet().diff(current.toSet())
        val to = current.toSet().diff(previous.toSet())
        logger.debug("changed squares: from={}, to={}", from, to)
        if (to.length() != 1) {
            throw UnexpectedMoveException(
                String.format("cannot detect move (from: %s, to: %s)", previous, current))
        }
        val movedPiece = to.get()._2
        val fromSquare = from.find { it._2 == movedPiece }.map { it._1 }.get()
        val toSquare = to.get()._1
        // TODO: validate move outer
        return previous.moves(fromSquare)
            .filter { it.to == toSquare }
            .getOrElseThrow { UnexpectedMoveException("invalid move $fromSquare:$toSquare (from: $previous, to: $current)") }
    }
}
