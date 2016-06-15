package io.chesslave.hands;

import io.chesslave.model.Square;

import java.awt.Rectangle;

/**
 * A bot able to move pieces through a dragFrom and drop strategy.
 */
public class DragBotMover extends BaseBotMover {

    public DragBotMover(Rectangle boardArea) {
        super(boardArea);
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
