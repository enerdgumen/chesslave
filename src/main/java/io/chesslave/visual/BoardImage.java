package io.chesslave.visual;

import io.chesslave.model.Square;
import java.awt.Point;
import java.awt.image.BufferedImage;

public final class BoardImage {

    private final BufferedImage image;
    private final Point offset;

    public BoardImage(BufferedImage image) {
        this(image, new Point(0, 0));
    }

    public BoardImage(BufferedImage image, Point offset) {
        this.image = image;
        this.offset = offset;
    }

    public BufferedImage image() {
        return image;
    }

    public Point offset() {
        return offset;
    }

    public SquareImage squareImage(Square square) {
        return new SquareImage(image, square);
    }
}
