package io.chesslave.visual;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.chesslave.eyes.Images;
import io.chesslave.model.Square;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class BoardImageTest {
    private static final String DIR_IMAGES = "/images/";
    private static final String DIR_RESOURCES = DIR_IMAGES + "visual/";
    private static final String IMAGE_BOARD = DIR_RESOURCES + "board.png";
    private static final String IMAGE_KNIGHT = DIR_RESOURCES + "knight.png";

    private static BufferedImage board;
    private static BufferedImage knight;

    @BeforeClass
    public static void setUpBeforeClass() {
        board = Images.read(IMAGE_BOARD);
        knight = Images.read(IMAGE_KNIGHT);
    }

    @Test
    public void constructorImageTest() {
        final BoardImage boardImage = new BoardImage(board);
        assertEquals(new Point(0, 0), boardImage.offset());
        validateBoardImage(boardImage);
    }

    @Test
    public void constructorImageOffsetTest() {
        final Point offset = new Point(1, 1);
        final BoardImage boardImage = new BoardImage(board, offset);
        assertEquals(offset, boardImage.offset());
        validateBoardImage(boardImage);
    }

    private void validateBoardImage(BoardImage boardImage) {
        assertTrue(Images.areEquals(boardImage.image(), board));
        assertTrue(Images.areEquals(boardImage.squareImage(Square.of("g1")).image(), knight));
    }
}
