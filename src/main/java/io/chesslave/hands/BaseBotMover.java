package io.chesslave.hands;

import io.chesslave.hands.sikuli.SikuliPointer;
import io.chesslave.model.Square;
import io.chesslave.visual.BoardImage;
import io.chesslave.visual.SquareImage;

import java.awt.Point;

/**
 * Base implementation of a bot which can move pieces on the board.
 */
public abstract class BaseBotMover implements Mover {

    protected final Pointer pointer;
    private final BoardImage board;

    protected BaseBotMover(BoardImage board) {
        pointer = new SikuliPointer();
        this.board = board;
    }

    protected Point getSquareCoords(Square square) {
        final SquareImage squareImage = board.squareImage(square);
        return new Point(
                board.offset().x + squareImage.middleX(),
                board.offset().y + squareImage.middleY());
    }
}