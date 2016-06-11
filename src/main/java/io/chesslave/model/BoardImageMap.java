package io.chesslave.model;

import io.chesslave.model.Board;
import io.chesslave.model.Square;

public final class BoardImageMap {

    private final int squareSize;

    public BoardImageMap(int boardSize) {
        this.squareSize = boardSize / Board.SIZE;
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

    public int bottom(Square square) {
        return squareSize * (Board.SIZE - square.row);
    }

    public int middleX(Square square) {
        return left(square) + squareSize / 2;
    }

    public int middleY(Square square) {
        return top(square) + squareSize / 2;
    }
}
