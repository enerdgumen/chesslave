package io.chesslave.visual;

import io.chesslave.model.Board;
import io.chesslave.model.Square;
import javaslang.Lazy;
import java.awt.image.BufferedImage;

public final class SquareImage {

    private final Lazy<BufferedImage> image;
    private final Square square;
    private final boolean flipped;
    private final int size;

    public SquareImage(BufferedImage board, Square square, boolean flipped) {
        this.square = square;
        this.flipped = flipped;
        this.size = board.getWidth() / Board.SIZE;
        this.image = Lazy.of(() -> board.getSubimage(left(), top(), size, size));
    }

    public BufferedImage image() {
        return image.get();
    }

    public int size() {
        return size;
    }

    public int left() {
        return size * horizontalOffset(square.col);
    }

    public int right() {
        return size * (horizontalOffset(square.col) + 1);
    }

    public int top() {
        return size * verticalOffset(square.row);
    }

    public int bottom() {
        return size * (verticalOffset(square.row) + 1);
    }

    public int middleX() {
        return left() + size / 2;
    }

    public int middleY() {
        return top() + size / 2;
    }

    private int horizontalOffset(int col) {
        return flipped ? Board.SIZE - 1 - col : col;
    }

    private int verticalOffset(int row) {
        return flipped ? row : Board.SIZE - 1 - row;
    }
}
