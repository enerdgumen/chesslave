package io.chesslave.eyes;

import io.chesslave.model.Board;
import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import io.chesslave.visual.BoardImage;
import javaslang.collection.Set;
import java.awt.Rectangle;

public class PositionRecogniser {

    private final Vision vision;
    private final BoardConfiguration config;

    public PositionRecogniser(Vision vision, BoardConfiguration config) {
        this.vision = vision;
        this.config = config;
    }

    /**
     * Detects the position represented by the given board image.
     */
    public Position position(BoardImage board) {
        final Vision.Recogniser recogniser = vision.recognise(board.image());
        final Position.Builder position = new Position.Builder();
        Piece.all().forEach(piece -> findAllPieces(recogniser, piece)
                .forEach(square -> position.withPiece(square, piece)));
        return position.build();
    }

    private Set<Square> findAllPieces(Vision.Recogniser recogniser, Piece piece) {
        return recogniser.matches(config.pieces.apply(piece))
                .map(match -> {
                    final Rectangle region = match.region();
                    final int col = (int) (Board.SIZE * region.getCenterX() / match.source().getWidth());
                    final int row = Board.SIZE - (int) (Board.SIZE * region.getCenterY() / match.source().getHeight());
                    return new Square(col, row - 1);
                })
                .toSet();
    }
}
