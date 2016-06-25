package io.chesslave.rendering;

import static org.junit.Assert.assertTrue;

import io.chesslave.eyes.Images;
import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Piece.Type;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import org.junit.Before;
import org.junit.Test;
import java.awt.image.BufferedImage;
import java.io.File;

public class BoardRendererTest {
    private static final String DIR_IMAGES = "/images/";
    private static final String DIR_CHESS_SET = DIR_IMAGES + "set1/";
    private static final String DIR_RENDERING = DIR_IMAGES + "rendering/";

    private ChessSet chessSet;

    @Before
    public void setUp() {
        chessSet = ChessSet.read(DIR_CHESS_SET);
    }

    @Test
    public void renderEmptyPositionTest() throws Exception {
        final BufferedImage got = BoardRenderer.using(chessSet)
                .toBoardImage().image();
        final BufferedImage expected = Images.read(DIR_RENDERING + "no-position-no-custom-bg.png");
        assertTrue(Images.areEquals(expected, got));
    }

    @Test
    public void renderEmptyPositionCustomBackgroundTest() throws Exception {
        final BufferedImage got = BoardRenderer.using(chessSet)
                .withBackground(Square.of("d4"), java.awt.Color.RED)
                .withBackground(Square.of("e5"), java.awt.Color.YELLOW)
                .toBoardImage().image();
        final BufferedImage expected = Images.read(DIR_RENDERING + "no-position-with-custom-bg.png");
        Images.write(got, new File("C:\\Users\\User\\IdeaProjects\\empty.png"));
        assertTrue(Images.areEquals(expected, got));
    }

    @Test
    public void renderPositionTest() throws Exception {
        final BufferedImage got = BoardRenderer.using(chessSet)
                .withPosition(position())
                .toBoardImage().image();
        final BufferedImage expected = Images.read(DIR_RENDERING + "with-position-no-custom-bg.png");
        assertTrue(Images.areEquals(expected, got));
    }

    @Test
    public void renderPositionCustomBackgroundTest() throws Exception {
        final BufferedImage got = BoardRenderer.using(chessSet)
                .withPosition(position())
                .withBackground(Square.of("h8"), java.awt.Color.BLUE)
                .toBoardImage().image();
        final BufferedImage expected = Images.read(DIR_RENDERING + "with-position-with-custom-bg.png");
        Images.write(got, new File("C:\\Users\\User\\IdeaProjects\\position.png"));
        assertTrue(Images.areEquals(expected, got));
    }

    private Position position() {
        return new Position.Builder()
                    .withPiece(Square.of("a1"), Piece.of(Type.ROOK, Color.WHITE))
                    .withPiece(Square.of("b2"), Piece.of(Type.KNIGHT, Color.WHITE))
                    .withPiece(Square.of("c3"), Piece.of(Type.BISHOP, Color.WHITE))
                    .withPiece(Square.of("d4"), Piece.of(Type.QUEEN, Color.WHITE))
                    .withPiece(Square.of("e5"), Piece.of(Type.KING, Color.WHITE))
                    .withPiece(Square.of("f6"), Piece.of(Type.PAWN, Color.WHITE))
                    .withPiece(Square.of("h8"), Piece.of(Type.ROOK, Color.BLACK))
                    .withPiece(Square.of("g7"), Piece.of(Type.KNIGHT, Color.BLACK))
                    .withPiece(Square.of("f5"), Piece.of(Type.BISHOP, Color.BLACK))
                    .withPiece(Square.of("e4"), Piece.of(Type.QUEEN, Color.BLACK))
                    .withPiece(Square.of("d3"), Piece.of(Type.KING, Color.BLACK))
                    .withPiece(Square.of("c2"), Piece.of(Type.PAWN, Color.BLACK))
                    .build();
    }
}