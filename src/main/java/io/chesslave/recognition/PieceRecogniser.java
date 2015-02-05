package io.chesslave.recognition;

import io.chesslave.model.Board;
import java.awt.Rectangle;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class PieceRecogniser implements BiFunction<ScreenRegion, Target, Set<Board.Square>> {

    private final Board board;

    @Override
    public Set<Board.Square> apply(ScreenRegion region, Target target) {
        final Rectangle bounds = region.getBounds();
        final List<ScreenRegion> regions = region.findAll(target);
        return regions.stream()
                .map(match -> {
                    final Rectangle piece = match.getBounds();
                    final int col = (int) (board.size * (piece.getCenterX() - bounds.x) / bounds.width);
                    final int row = board.size - (int) (board.size * (piece.getCenterY() - bounds.y) / bounds.height);
                    return board.square(col, row - 1);
                })
                .collect(Collectors.toSet());
    }

    public PieceRecogniser(Board board) {
        this.board = board;
    }
}
