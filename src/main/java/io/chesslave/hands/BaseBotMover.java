package io.chesslave.hands;

import io.chesslave.model.BoardImageMap;
import io.chesslave.model.Square;
import org.sikuli.script.Location;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

/**
 * Base implementation of a bot which can move pieces on the board.
 */
public abstract class BaseBotMover implements Mover {
    protected final Screen screen;

    private final BoardImageMap boardMap;
    private final Region boardArea;

    protected BaseBotMover(Region boardArea) {
        screen = Screen.getPrimaryScreen();
        this.boardArea = boardArea;
        boardMap = new BoardImageMap(boardArea.getW());
    }

    protected Location getSquareLocation(Square square) {
        final int offsetX = boardMap.middleX(square);
        final int offsetY = boardMap.middleY(square);
        return new Location(boardArea.x + offsetX, boardArea.y + offsetY);
    }
}