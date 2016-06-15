package io.chesslave.hands;

import io.chesslave.model.Square;

import java.awt.Rectangle;

/**
 * A bot able to move pieces through a point and click strategy.
 */
public class ClickBotMover extends BaseBotMover {

    public ClickBotMover(Rectangle boardArea) {
        super(boardArea);
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
