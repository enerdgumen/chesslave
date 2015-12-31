package io.chesslave.recognition;

import io.chesslave.model.Board;
import javaslang.collection.Set;
import javaslang.collection.Stream;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Recognition {

    public static Set<Board.Square> filledSquares(BufferedImage boardImg) {
        return Board.standard.allSquares().filter(square -> isSquareFilled(boardImg, square));
    }

    private static boolean isSquareFilled(BufferedImage image, Board.Square square) {
        final BoardMap map = new BoardMap(Board.standard, image.getWidth());
        final int offset = map.squareSize() / 10;
        final int left = map.left(square) + offset;
        final int y = map.middleY(square);
        final Color example = new Color(image.getRGB(left, y));
        return Stream.rangeBy(left, map.right(square) - offset, offset)
                .map(x -> new Color(image.getRGB(x, y)))
                .exists(c -> !Colors.areSimilar(example, c));
    }
}
