package io.chesslave.visual.recognition;

import io.chesslave.model.Square;
import io.chesslave.visual.BoardMap;
import javaslang.collection.Set;
import javaslang.collection.Stream;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Recognition {

    public static Set<Square> filledSquares(BufferedImage boardImg) {
        return Square.all().filter(square -> isSquareFilled(boardImg, square));
    }

    private static boolean isSquareFilled(BufferedImage image, Square square) {
        final BoardMap map = new BoardMap(image.getWidth());
        final int offset = map.squareSize() / 16;
        final int left = map.left(square) + offset;
        final int y = map.middleY(square);
        final Color example = new Color(image.getRGB(left, y));
        return Stream.rangeBy(left, map.right(square) - offset, offset)
                .map(x -> new Color(image.getRGB(x, y)))
                .exists(c -> !Colors.areSimilar(example, c));
    }
}
