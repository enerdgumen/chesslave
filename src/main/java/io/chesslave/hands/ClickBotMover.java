package io.chesslave.hands;

import io.chesslave.model.BoardImageMap;
import io.chesslave.model.Square;

import java.awt.Point;
import java.awt.event.InputEvent;

/**
 * A bot able to move pieces through a point and click strategy.
 */
public class ClickBotMover extends BaseBotMover {

    public ClickBotMover(BoardImageMap boardMap, Point topLeftBoardCorner) {
        super(boardMap, topLeftBoardCorner);
    }

    @Override
    public void move(Square from, Square to) {
        Point fromCoords = getSquareCoords(from);
        robot.mouseMove(fromCoords.x, fromCoords.y);
        mouseClick();

        Point toCoords = getSquareCoords(to);
        robot.mouseMove(toCoords.x, toCoords.y);
        mouseClick();
    }

    private void mouseClick() {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
