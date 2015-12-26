package io.chesslave.recognition;

import io.chesslave.model.Board;
import javaslang.collection.List;
import javaslang.collection.Set;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import java.awt.*;
import java.util.function.BiFunction;

public class PieceRecogniser implements BiFunction<ScreenRegion, Target, Set<Board.Square>> {

    private final Board board;

    @Override
    public Set<Board.Square> apply(ScreenRegion region, Target target) {
        final Rectangle bounds = region.getBounds();
        final List<ScreenRegion> regions = List.ofAll(region.findAll(target));
        return regions
                .map(match -> {
                    final Rectangle piece = match.getBounds();
                    final int col = (int) (board.size * (piece.getCenterX() - bounds.x) / bounds.width);
                    final int row = board.size - (int) (board.size * (piece.getCenterY() - bounds.y) / bounds.height);
                    return board.square(col, row - 1);
                })
                .toSet();
    }

    public PieceRecogniser(Board board) {
        this.board = board;
    }
}
