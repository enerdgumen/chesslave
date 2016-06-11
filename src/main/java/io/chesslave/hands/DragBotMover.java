package io.chesslave.hands;

import io.chesslave.model.BoardImageMap;
import io.chesslave.model.Square;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * A bot able to move pieces through a drag and drop strategy.
 */
public class DragBotMover extends BaseBotMover {

    public DragBotMover(BoardImageMap boardMap, Point topLeftBoardCorner) {
        super(boardMap, topLeftBoardCorner);
    }

    @Override
    public void move(Square from, Square to) {
        Point fromCoords = getSquareCoords(from);
        robot.mouseMove(fromCoords.x, fromCoords.y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(100);

        Point toCoords = getSquareCoords(to);
        robot.mouseMove(toCoords.x, toCoords.y);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
