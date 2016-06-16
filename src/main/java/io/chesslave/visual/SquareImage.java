package io.chesslave.visual;

import io.chesslave.model.Board;
import io.chesslave.model.Square;
import javaslang.Lazy;
import java.awt.image.BufferedImage;

public final class SquareImage {

    private final Lazy<BufferedImage> image;
    private final Square square;
    private final int size;

    public SquareImage(BufferedImage board, Square square) {
        this.square = square;
        this.size = board.getWidth() / Board.SIZE;
        this.image = Lazy.of(() -> board.getSubimage(left(), top(), size(), size()));
    }

    public BufferedImage image() {
        return image.get();
    }

    public int size() {
        return size;
    }

    public int left() {
        return size * square.col;
    }

    public int right() {
        return size * (square.col + 1);
    }

    public int top() {
        return size * (Board.SIZE - square.row - 1);
    }

    public int bottom() {
        return size * (Board.SIZE - square.row);
    }

    public int middleX() {
        return left() + size / 2;
    }

    public int middleY() {
        return top() + size / 2;
    }
}
