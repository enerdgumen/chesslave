package io.chesslave.visual;

import io.chesslave.model.Board;

public final class BoardMap {

    private final Board board;
    private final int squareSize;

    public BoardMap(Board board, int imageSize) {
        this.board = board;
        this.squareSize = imageSize / board.size;
    }

    public int squareSize() {
        return squareSize;
    }

    public int left(Board.Square square) {
        return squareSize * square.col;
    }

    public int right(Board.Square square) {
        return squareSize * (square.col + 1);
    }

    public int top(Board.Square square) {
        return squareSize * (board.size - square.row - 1);
    }

    public int middleY(Board.Square square) {
        return top(square) + squareSize / 2;
    }
}
