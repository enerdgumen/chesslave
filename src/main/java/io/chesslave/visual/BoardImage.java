package io.chesslave.visual;

import io.chesslave.model.Square;
import java.awt.image.BufferedImage;

public final class BoardImage {

    private final BufferedImage image;
    private final BoardImageMap map;

    public BoardImage(BufferedImage image) {
        this.image = image;
        this.map = new BoardImageMap(image.getWidth());
    }

    public BufferedImage image() {
        return image;
    }

    public BufferedImage squareImage(Square square) {
        final int padding = 2;
        return image.getSubimage(
                map.left(square) + padding,
                map.top(square) + padding,
                map.squareSize() - padding * 2,
                map.squareSize() - padding * 2);
    }
}
