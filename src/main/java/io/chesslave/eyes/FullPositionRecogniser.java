package io.chesslave.eyes;

import io.chesslave.model.Board;
import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import javaslang.collection.Set;
import java.awt.Rectangle;
import java.util.Optional;

public class FullPositionRecogniser implements PositionRecogniser {

    private final Vision vision;
    private final BoardConfiguration config;

    public FullPositionRecogniser(Vision vision, BoardConfiguration config) {
        this.vision = vision;
        this.config = config;
    }

    @Override
    public Optional<Position> begin(BoardImage image) {
        return recognise(image);
    }

    @Override
    public Optional<Position> next(Position previousPosition, BoardImage previousImage, BoardImage currentImage) {
        return recognise(currentImage);
    }

    private Optional<Position> recognise(BoardImage board) {
        final Vision.Recognizer recognizer = vision.recognise(board.image());
        final Position.Builder position = new Position.Builder();
        Piece.all().forEach(piece -> findAllPieces(recognizer, piece)
                .forEach(square -> position.withPiece(square, piece)));
        return Optional.of(position.build());
    }

    private Set<Square> findAllPieces(Vision.Recognizer recognizer, Piece piece) {
        return recognizer.matches(config.pieces.apply(piece))
                .map(match -> {
                    final Rectangle region = match.region();
                    final int col = (int) (Board.SIZE * region.getCenterX() / match.source().getWidth());
                    final int row = Board.SIZE - (int) (Board.SIZE * region.getCenterY() / match.source().getHeight());
                    return new Square(col, row - 1);
                })
                .toSet();
    }
}
