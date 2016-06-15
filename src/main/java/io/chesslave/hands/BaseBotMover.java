package io.chesslave.hands;

import io.chesslave.hands.sikuli.SikuliMouse;
import io.chesslave.model.BoardImageMap;
import io.chesslave.model.Square;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Base implementation of a bot which can move pieces on the board.
 */
public abstract class BaseBotMover implements Mover {
    protected final Mouse mouse;

    private final BoardImageMap boardMap;
    private final Rectangle boardArea;

    protected BaseBotMover(Rectangle boardArea) {
        mouse = new SikuliMouse();
        this.boardArea = boardArea;
        boardMap = new BoardImageMap(boardArea.getSize().width);
    }

    protected Point getSquareCoords(Square square) {
        final int offsetX = boardMap.middleX(square);
        final int offsetY = boardMap.middleY(square);
        return new Point(boardArea.x + offsetX, boardArea.y + offsetY);
    }
}