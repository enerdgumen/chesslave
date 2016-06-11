package io.chesslave.eyes;

import io.chesslave.model.Position;
import io.chesslave.eyes.BoardImage;
import java.util.Optional;

public interface PositionRecogniser {

    Optional<Position> begin(BoardImage image);

    Optional<Position> next(Position previousPosition, BoardImage previousImage, BoardImage currentImage);
}
