package io.chesslave.eyes;

import io.chesslave.model.Square;
import java.awt.image.BufferedImage;

public final class BoardImage {
    private static final int DEFAULT_PADDING = 2;

    private final BufferedImage image;
    private final BoardImageMap boardMap;

    public BoardImage(BufferedImage image) {
        this.image = image;
        this.boardMap = new BoardImageMap(image.getWidth());
    }

    public BufferedImage image() {
        return image;
    }

    public BufferedImage squareImage(Square square) {
       return squareImage(square, DEFAULT_PADDING);
    }

    public BufferedImage squareImage(Square square, int padding) {
        return image.getSubimage(
                boardMap.left(square) + padding,
                boardMap.top(square) + padding,
                boardMap.squareSize() - padding * 2,
                boardMap.squareSize() - padding * 2);
    }
}
