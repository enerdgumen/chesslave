package io.chesslave.hands;

import io.chesslave.visual.BoardImage;
import io.chesslave.model.Square;

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
            mouse.click(getSquareCoords(from));
            mouse.click(getSquareCoords(to));
        } catch (RuntimeException re) {
            throw new MoverException(re);
        }
    }
}
