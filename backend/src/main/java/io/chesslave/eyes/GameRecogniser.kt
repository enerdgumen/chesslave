package io.chesslave.eyes

import io.chesslave.model.Color
import io.chesslave.model.Game
import io.chesslave.model.Move
import io.chesslave.visual.model.BoardImage
import javaslang.collection.List
import javaslang.control.Try
import java.util.function.Supplier

class GameRecogniser(private val positionRecogniser: PositionRecogniser,
                     private val moveRecogniserByImageDiff: MoveRecogniserByImageDiff,
                     private val moveRecogniserByPositionDiff: MoveRecogniserByPositionDiff) {

    /**
     * Recognise the initial position of a game by analyzing the board image.
     */
    fun begin(board: BoardImage, turn: Color): Game {
        val position = positionRecogniser.position(board)
        return Game(position, List.empty(), turn)
    }

    /**
     * Recognise the next move of a game by analyzing the differences between the board images.
     *
     * @return the detected move or null if none move was done
     * @throws Exception if the detection fail
     */
    fun next(previousGame: Game, previousBoard: BoardImage, currentBoard: BoardImage): Move?
        = Try.of { moveRecogniserByImageDiff.detect(previousGame.position(), previousBoard, currentBoard) }
        .orElse(Try.of {
            val currentPosition = positionRecogniser.position(currentBoard)
            moveRecogniserByPositionDiff.detect(previousGame.position(), currentPosition)
        })
        .getOrElseThrow(Supplier { RuntimeException("Cannot detect next move!") })
}
