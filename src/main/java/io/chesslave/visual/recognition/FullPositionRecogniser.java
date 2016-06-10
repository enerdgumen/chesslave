package io.chesslave.visual.recognition;

import io.chesslave.model.Board;
import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import io.chesslave.visual.BoardImage;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import org.sikuli.script.Finder;
import org.sikuli.script.Image;
import org.sikuli.script.ImageFinder;
import java.awt.Rectangle;
import java.util.Optional;

public class FullPositionRecogniser implements PositionRecogniser {

    private final BoardConfiguration config;

    public FullPositionRecogniser(BoardConfiguration config) {
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
        final Position.Builder position = new Position.Builder();
        Piece.all().forEach(piece -> findAllPieces(board, piece)
                .forEach(square -> position.withPiece(square, piece)));
        return Optional.of(position.build());
    }

    private Set<Square> findAllPieces(BoardImage board, Piece piece) {
        final Image target = new Image(config.pieces.apply(piece));
        final Finder matches = new Finder(new Image(board.image()));
        matches.findAll(target);
        return HashSet.ofAll(() -> matches)
                .map(match -> {
                    final Rectangle rect = match.getRect();
                    final int col = (int) (Board.SIZE * rect.getCenterX() / board.image().getWidth());
                    final int row = Board.SIZE - (int) (Board.SIZE * rect.getCenterY() / board.image().getHeight());
                    return new Square(col, row - 1);
                });
    }
}
