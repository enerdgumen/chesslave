package io.chesslave.visual;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.chesslave.eyes.Images;
import io.chesslave.model.Board;
import io.chesslave.model.Square;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SquareImageTest {
    private static final String DIR_IMAGES = "/images/";
    private static final String DIR_RESOURCES = DIR_IMAGES + "visual/";
    private static final String IMAGE_UNFLIPPED_BOARD = DIR_RESOURCES + "board.png";
    private static final String IMAGE_FLIPPED_BOARD = DIR_RESOURCES + "flipped-board.png";
    private static final String IMAGE_KNIGHT = DIR_RESOURCES + "knight-dark-square.png";

    private SquareImage squareImage;
    private Square square;

    @Parameterized.Parameter
    public BufferedImage board;

    @Parameterized.Parameter(value = 1)
    public boolean flipped;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Images.read(IMAGE_UNFLIPPED_BOARD), false},
                {Images.read(IMAGE_FLIPPED_BOARD), true}
        });
    }

    @Before
    public void setUp() {
        square = Square.of("g1");
        squareImage = new SquareImage(board, square, flipped);
    }

    @Test
    public void imageTest() {
        final BufferedImage knight = Images.read(IMAGE_KNIGHT);
        assertTrue(Images.areEquals(knight, squareImage.image()));
    }

    @Test
    public void sizeTest() {
        assertEquals(board.getWidth() / Board.SIZE, squareImage.size());
    }

    @Test
    public void leftTest() {
        assertEquals(squareImage.size() * horizontalOffset(square.col), squareImage.left());
    }

    @Test
    public void rightTest() {
        assertEquals(squareImage.size() * (horizontalOffset(square.col) + 1), squareImage.right());
    }

    @Test
    public void topTest() {
        assertEquals(squareImage.size() * verticalOffset(square.row), squareImage.top());
    }

    @Test
    public void bottomTest() {
        assertEquals(squareImage.size() * (verticalOffset(square.row) + 1), squareImage.bottom());
    }

    @Test
    public void middleXTest() {
        assertEquals(squareImage.size() / 2 + squareImage.size() * horizontalOffset(square.col), squareImage.middleX());
    }

    @Test
    public void middleYTest() {
        assertEquals(squareImage.size() / 2 + squareImage.size() * verticalOffset(square.row), squareImage.middleY());
    }

    private int horizontalOffset(int col) {
        return flipped ? Board.SIZE - 1 - col : col;
    }

    private int verticalOffset(int row) {
        return flipped ? row : Board.SIZE - 1 - row;
    }
}
