package io.chesslave.visual.recognition;

import io.chesslave.model.Board;
import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import io.chesslave.visual.BoardImage;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import javaslang.control.Try;
import org.sikuli.script.Image;
import org.sikuli.script.Match;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.Iterator;
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

    private Optional<Position> recognise(BoardImage image) {
        final Position.Builder position = new Position.Builder();
        final Image region = new Image(image.image());
        Piece.all().forEach(piece -> findAllPieces(region, piece)
                .forEach(square -> position.withPiece(square, piece)));
        return Optional.of(position.build());
    }

    private Set<Square> findAllPieces(Image region, Piece piece) {
        final Image target = new Image(config.pieces.apply(piece));
        final Iterator<Match> matches = Try.of(() -> region.findAll(target)).getOrElse(Collections.emptyIterator());
        final Dimension bounds = region.getSize();
        return HashSet.ofAll(() -> matches)
                .map(match -> {
                    final Rectangle rect = match.getRect();
                    final int col = (int) (Board.SIZE * rect.getCenterX() / bounds.width);
                    final int row = Board.SIZE - (int) (Board.SIZE * rect.getCenterY() / bounds.height);
                    return new Square(col, row - 1);
                });
    }
}
