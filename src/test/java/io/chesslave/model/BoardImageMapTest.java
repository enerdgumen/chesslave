package io.chesslave.model;

import io.chesslave.model.Board;
import io.chesslave.model.BoardImageMap;
import io.chesslave.model.Square;
import javaslang.collection.Stream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BoardImageMapTest {
    private static final int IMAGE_SIZE = 512;
    private static final int SQUARE_SIZE = 64;
    private static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;

    private BoardImageMap boardMap;

    @Before
    public void setUp() {
        boardMap = new BoardImageMap(IMAGE_SIZE);
    }

    @Test
    public void squareSizeTest() {
        assertEquals(SQUARE_SIZE, boardMap.squareSize());
    }

    @Test
    public void leftTest() {
        Stream.from(0).take(8)
                .map(i -> new Square(i, 0))
                .forEach(square -> assertEquals(SQUARE_SIZE * square.col, boardMap.left(square)));
    }

    @Test
    public void rightTest() {
        Stream.from(0).take(8)
                .map(i -> new Square(i, 0))
                .forEach(square -> assertEquals(SQUARE_SIZE * (square.col + 1), boardMap.right(square)));
    }

    @Test
    public void topTest() {
        Stream.from(0).take(8)
                .map(i -> new Square(0, i))
                .forEach(square -> assertEquals(SQUARE_SIZE * (Board.SIZE - square.row - 1), boardMap.top(square)));
    }

    @Test
    public void bottomTest() {
        Stream.from(0).take(8)
                .map(i -> new Square(0, i))
                .forEach(square -> assertEquals(SQUARE_SIZE * (Board.SIZE - square.row), boardMap.bottom(square)));
    }

    @Test
    public void middleXTest() {
        Stream.from(0).take(8)
                .map(i -> new Square(i, 0))
                .forEach(square -> assertEquals(SQUARE_SIZE * square.col + HALF_SQUARE_SIZE, boardMap.middleX(square)));
    }

    @Test
    public void middleYTest() {
        Stream.from(0).take(8)
                .map(i -> new Square(0, i))
                .forEach(square -> assertEquals(
                        (SQUARE_SIZE * (Board.SIZE - square.row - 1)) + HALF_SQUARE_SIZE, boardMap.middleY(square)));
    }
}
