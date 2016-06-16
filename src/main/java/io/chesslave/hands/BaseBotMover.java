package io.chesslave.hands;

import io.chesslave.visual.BoardImage;
import io.chesslave.visual.SquareImage;
import io.chesslave.hands.sikuli.SikuliMouse;
import io.chesslave.model.Square;
import java.awt.Point;

/**
 * Base implementation of a bot which can move pieces on the board.
 */
public abstract class BaseBotMover implements Mover {

    protected final Mouse mouse;
    private final BoardImage board;

    protected BaseBotMover(BoardImage board) {
        mouse = new SikuliMouse();
        this.board = board;
    }

    protected Point getSquareCoords(Square square) {
        final SquareImage squareImage = board.squareImage(square);
        return new Point(
                board.offset().x + squareImage.middleX(),
                board.offset().y + squareImage.middleY());
    }
}