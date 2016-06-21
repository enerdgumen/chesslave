package io.chesslave.eyes;

import io.chesslave.model.Position;
import io.chesslave.visual.BoardImage;
import javaslang.control.Option;

public interface PositionRecogniser {

    Option<Position> begin(BoardImage image);

    Option<Position> next(Position previousPosition, BoardImage previousImage, BoardImage currentImage);
}
