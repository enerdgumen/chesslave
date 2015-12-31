package io.chesslave.visual;

import io.chesslave.model.Board;
import io.chesslave.model.Square;

public final class BoardMap {

    private final int squareSize;

    public BoardMap(int imageSize) {
        this.squareSize = imageSize / Board.SIZE;
    }

    public int squareSize() {
        return squareSize;
    }

    public int left(Square square) {
        return squareSize * square.col;
    }

    public int right(Square square) {
        return squareSize * (square.col + 1);
    }

    public int top(Square square) {
        return squareSize * (Board.SIZE - square.row - 1);
    }

    public int middleY(Square square) {
        return top(square) + squareSize / 2;
    }
}
