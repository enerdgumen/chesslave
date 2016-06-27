package io.chesslave.hands;

import io.chesslave.model.Square;
import io.chesslave.visual.BoardImage;

/**
 * A bot able to move pieces through a drag and drop strategy.
 */
public class DragBotMover extends BaseBotMover {

    public DragBotMover(BoardImage board) {
        super(board);
    }

    @Override
    public void move(Square from, Square to) throws MoverException {
        try {
            pointer.dragFrom(getSquareCoords(from));
            pointer.dropAt(getSquareCoords(to));
        } catch (RuntimeException re) {
            throw new MoverException(re);
        }
    }
}
