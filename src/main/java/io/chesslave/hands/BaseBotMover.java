package io.chesslave.hands;

import io.chesslave.model.BoardImageMap;
import io.chesslave.model.Square;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;

/**
 * Base implementation of a bot which can move pieces on the board.
 */
public abstract class BaseBotMover implements Mover {
    protected final Robot robot;

    private final BoardImageMap boardMap;
    private final Point topLeftBoard;

    protected BaseBotMover(BoardImageMap boardMap, Point topLeftBoardCorner) {
        try {
            robot = new Robot();
        } catch (AWTException | SecurityException e) {
            throw new UninstantiableMoverException(e);
        }
        this.boardMap = boardMap;
        this.topLeftBoard = topLeftBoardCorner;
    }

    protected Point getSquareCoords(Square square) {
        final int offsetX = boardMap.middleX(square);
        final int offsetY = boardMap.middleY(square);
        Point squareCoords = new Point(topLeftBoard);
        squareCoords.translate(offsetX, offsetY);
        return squareCoords;
    }
}