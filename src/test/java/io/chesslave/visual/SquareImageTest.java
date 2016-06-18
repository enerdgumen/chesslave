package io.chesslave.visual;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.chesslave.eyes.Images;
import io.chesslave.model.Board;
import io.chesslave.model.Square;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.image.BufferedImage;

public class SquareImageTest {
    private static final String DIR_IMAGES = "/images/";
    private static final String DIR_RESOURCES = DIR_IMAGES + "visual/";
    private static final String IMAGE_BOARD = DIR_RESOURCES + "board.png";
    private static final String IMAGE_KNIGHT = DIR_RESOURCES + "knight.png";

    private static BufferedImage board;
    private static BufferedImage knight;

    private SquareImage squareImage;
    private Square square;

    @BeforeClass
    public static void setUpBeforeClass() {
        board = Images.read(IMAGE_BOARD);
        knight = Images.read(IMAGE_KNIGHT);
    }

    @Before
    public void setUp() {
        square = Square.of("g1");
        squareImage = new SquareImage(board, square);
    }

    @Test
    public void imageTest() {
        assertTrue(Images.areEquals(knight, squareImage.image()));
    }

    @Test
    public void sizeTest() {
        assertEquals(board.getWidth() / Board.SIZE, squareImage.size());
    }

    @Test
    public void leftTest() {
        assertEquals(squareImage.size() * square.col, squareImage.left());
    }

    @Test
    public void rightTest() {
        assertEquals(squareImage.size() * (square.col + 1), squareImage.right());
    }

    @Test
    public void topTest() {
        assertEquals(squareImage.size() * (Board.SIZE - square.row - 1), squareImage.top());
    }

    @Test
    public void bottomTest() {
        assertEquals(squareImage.size() * (Board.SIZE - square.row), squareImage.bottom());
    }

    @Test
    public void middleXTest() {
        assertEquals(squareImage.size() / 2 + squareImage.size() * square.col, squareImage.middleX());
    }

    @Test
    public void middleYTest() {
        assertEquals(squareImage.size() / 2 + squareImage.size() * (Board.SIZE - square.row - 1), squareImage.middleY());
    }
}
