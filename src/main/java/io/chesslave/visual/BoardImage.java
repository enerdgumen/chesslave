package io.chesslave.visual;

import io.chesslave.model.Square;

import java.awt.Point;
import java.awt.image.BufferedImage;

public final class BoardImage {

    private final BufferedImage image;
    private final Point offset;
    private final boolean flipped;

    public BoardImage(BufferedImage image) {
        this(image, new Point(0, 0), false);
    }

    public BoardImage(BufferedImage image, Point offset) {
        this(image, offset, false);
    }

    public BoardImage(BufferedImage image, boolean flipped) {
        this(image, new Point(0, 0), flipped);
    }

    public BoardImage(BufferedImage image, Point offset, boolean flipped) {
        this.image = image;
        this.offset = offset;
        this.flipped = flipped;
    }

    public BufferedImage image() {
        return image;
    }

    public Point offset() {
        return offset;
    }

    public boolean flipped() {
        return flipped;
    }

    public SquareImage squareImage(Square square) {
        return new SquareImage(image, square, flipped);
    }
}
