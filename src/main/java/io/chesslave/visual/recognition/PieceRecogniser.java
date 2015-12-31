package io.chesslave.visual.recognition;

import io.chesslave.model.Board;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import javaslang.control.Try;
import org.sikuli.script.Image;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import java.awt.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.BiFunction;

public class PieceRecogniser implements BiFunction<Region, Image, Set<Board.Square>> {

    private final Board board;

    public PieceRecogniser(Board board) {
        this.board = board;
    }

    @Override
    public Set<Board.Square> apply(Region region, Image target) {
        final Rectangle bounds = region.getRect();
        final Iterator<Match> matches = Try.of(() -> region.findAll(target)).orElse(Collections.emptyIterator());
        return HashSet.ofAll(() -> matches)
                .map(match -> {
                    final Rectangle piece = match.getRect();
                    final int col = (int) (board.size * (piece.getCenterX() - bounds.x) / bounds.width);
                    final int row = board.size - (int) (board.size * (piece.getCenterY() - bounds.y) / bounds.height);
                    return board.square(col, row - 1);
                });
    }
}
