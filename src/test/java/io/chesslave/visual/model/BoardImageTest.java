package io.chesslave.visual.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.chesslave.eyes.Images;
import io.chesslave.model.Square;
import io.chesslave.visual.model.BoardImage;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class BoardImageTest {
    private static final String DIR_IMAGES = "/images/";
    private static final String DIR_RESOURCES = DIR_IMAGES + "visual/";
    private static final String IMAGE_UNFLIPPED_BOARD = DIR_RESOURCES + "board.png";
    private static final String IMAGE_FLIPPED_BOARD = DIR_RESOURCES + "flipped-board.png";
    private static final String IMAGE_KNIGHT = DIR_RESOURCES + "knight-dark-square.png";
    private static final String IMAGE_PAWN = DIR_RESOURCES + "pawn-light-square.png";
    private static final String IMAGE_DARK_SQUARE = DIR_RESOURCES + "dark-square.png";
    private static final String IMAGE_LIGHT_SQUARE = DIR_RESOURCES + "light-square.png";

    private static BufferedImage unflippedBoard;
    private static BufferedImage flippedBoard;
    private static BufferedImage knight;
    private static BufferedImage pawn;
    private static BufferedImage darkSquare;
    private static BufferedImage lightSquare;

    @BeforeClass
    public static void setUpBeforeClass() {
        unflippedBoard = Images.read(IMAGE_UNFLIPPED_BOARD);
        flippedBoard = Images.read(IMAGE_FLIPPED_BOARD);

        knight = Images.read(IMAGE_KNIGHT);
        pawn = Images.read(IMAGE_PAWN);
        darkSquare = Images.read(IMAGE_DARK_SQUARE);
        lightSquare = Images.read(IMAGE_LIGHT_SQUARE);
    }

    @Test
    public void constructorImageTest() {
        final BoardImage boardImage = new BoardImage(unflippedBoard);
        assertEquals(new Point(0, 0), boardImage.offset());
        assertFalse(boardImage.flipped());
        validateBoardImage(boardImage);
    }

    @Test
    public void constructorImageFlippedTest() {
        final boolean flipped = true;
        final BoardImage boardImage = new BoardImage(flippedBoard, flipped);
        assertEquals(new Point(0, 0), boardImage.offset());
        assertTrue(boardImage.flipped());
        validateBoardImage(boardImage);
    }

    @Test
    public void constructorImageOffsetTest() {
        final Point offset = new Point(1, 1);
        final BoardImage boardImage = new BoardImage(unflippedBoard, offset);
        assertEquals(offset, boardImage.offset());
        assertFalse(boardImage.flipped());
        validateBoardImage(boardImage);
    }

    @Test
    public void constructorImageOffsetFlippedTest() {
        final Point offset = new Point(1, 1);
        final boolean flipped = true;
        final BoardImage boardImage = new BoardImage(flippedBoard, offset, flipped);
        assertEquals(offset, boardImage.offset());
        assertTrue(boardImage.flipped());
        validateBoardImage(boardImage);
    }

    private void validateBoardImage(BoardImage boardImage) {
        assertTrue(Images.areEquals(boardImage.image(), boardImage.flipped() ? flippedBoard : unflippedBoard));
        assertTrue(Images.areEquals(boardImage.squareImage(Square.of("g1")).image(), knight));
        assertTrue(Images.areEquals(boardImage.squareImage(Square.of("d7")).image(), pawn));
        assertTrue(Images.areEquals(boardImage.squareImage(Square.of("e4")).image(), lightSquare));
        assertTrue(Images.areEquals(boardImage.squareImage(Square.of("e5")).image(), darkSquare));
    }
}
