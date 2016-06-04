package io.chesslave.visual;

import io.chesslave.model.Square;
import java.awt.image.BufferedImage;

public final class BoardImage {
    private static final int DEFAULT_PADDING = 2;

    private final BufferedImage image;
    private final BoardImageMap boardImageMap;

    public BoardImage(BufferedImage image) {
        this.image = image;
        this.boardImageMap = new BoardImageMap(image.getWidth());
    }

    public BufferedImage image() {
        return image;
    }

    public BufferedImage squareImage(Square square) {
       return squareImage(square, DEFAULT_PADDING);
    }

    public BufferedImage squareImage(Square square, int padding) {
        return image.getSubimage(
                boardImageMap.left(square) + padding,
                boardImageMap.top(square) + padding,
                boardImageMap.squareSize() - padding * 2,
                boardImageMap.squareSize() - padding * 2);
    }
}
