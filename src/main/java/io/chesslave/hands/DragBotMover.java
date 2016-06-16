package io.chesslave.hands;

import io.chesslave.visual.BoardImage;
import io.chesslave.model.Square;

/**
 * A bot able to move pieces through a dragFrom and drop strategy.
 */
public class DragBotMover extends BaseBotMover {

    public DragBotMover(BoardImage board) {
        super(board);
    }

    @Override
    public void move(Square from, Square to) throws MoverException {
        try {
            mouse.dragFrom(getSquareCoords(from));
            mouse.dropAt(getSquareCoords(to));
        } catch (RuntimeException re) {
            throw new MoverException(re);
        }
    }
}
