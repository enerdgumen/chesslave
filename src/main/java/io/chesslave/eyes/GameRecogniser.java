package io.chesslave.eyes;

import io.chesslave.model.Color;
import io.chesslave.model.Game;
import io.chesslave.model.Move;
import io.chesslave.model.Position;
import io.chesslave.visual.BoardImage;
import javaslang.collection.List;
import javaslang.control.Option;
import javaslang.control.Try;

public class GameRecogniser {

    private final PositionRecogniser positionRecogniser;
    private final MoveRecogniserByImageDiff moveRecogniserByImageDiff;
    private final MoveRecogniserByPositionDiff moveRecogniserByPositionDiff;

    public GameRecogniser(PositionRecogniser positionRecogniser,
                          MoveRecogniserByImageDiff moveRecogniserByImageDiff,
                          MoveRecogniserByPositionDiff moveRecogniserByPositionDiff) {
        this.positionRecogniser = positionRecogniser;
        this.moveRecogniserByImageDiff = moveRecogniserByImageDiff;
        this.moveRecogniserByPositionDiff = moveRecogniserByPositionDiff;
    }

    /**
     * Recognise the initial position of a game by analyzing the board image.
     */
    public Game begin(BoardImage board, Color turn) {
        final Position position = positionRecogniser.position(board);
        return new Game(position, List.empty(), turn);
    }

    /**
     * Recognise the next move of a game by analyzing the differences between the board images.
     *
     * @return the detected move or nothing if none move was done
     * @throws Exception if the detection fail
     */
    public Option<Move> next(Game previousGame, BoardImage previousBoard, BoardImage currentBoard) {
        return Try.of(() -> moveRecogniserByImageDiff.detect(previousGame.position(), previousBoard, currentBoard))
                .orElse(Try.of(() -> {
                    final Position currentPosition = positionRecogniser.position(currentBoard);
                    return moveRecogniserByPositionDiff.detect(previousGame.position(), currentPosition);
                }))
                .getOrElseThrow(() -> new RuntimeException("Cannot detect next move!"));
    }
}
