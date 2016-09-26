package io.chesslave.hands;

import io.chesslave.model.Square;
import io.chesslave.visual.model.BoardImage;

/**
 * A bot able to move pieces through a point and click strategy.
 */
public class ClickBotMover extends BaseBotMover {

    public ClickBotMover(BoardImage board) {
        super(board);
    }

    @Override
    public void move(Square from, Square to) throws MoverException {
        try {
            pointer.click(getSquareCoords(from));
            pointer.click(getSquareCoords(to));
        } catch (RuntimeException re) {
            throw new MoverException(re);
        }
    }
}
