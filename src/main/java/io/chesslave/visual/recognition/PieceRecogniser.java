package io.chesslave.visual.recognition;

import io.chesslave.model.Board;
import io.chesslave.model.Square;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import javaslang.control.Try;
import org.sikuli.script.Image;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.BiFunction;

public class PieceRecogniser implements BiFunction<Region, Image, Set<Square>> {

    @Override
    public Set<Square> apply(Region region, Image target) {
        final Rectangle bounds = region.getRect();
        final Iterator<Match> matches = Try.of(() -> region.findAll(target)).getOrElse(Collections.emptyIterator());
        return HashSet.ofAll(() -> matches)
                .map(match -> {
                    final Rectangle piece = match.getRect();
                    final int col = (int) (Board.SIZE * (piece.getCenterX() - bounds.x) / bounds.width);
                    final int row = Board.SIZE - (int) (Board.SIZE * (piece.getCenterY() - bounds.y) / bounds.height);
                    return new Square(col, row - 1);
                });
    }
}
