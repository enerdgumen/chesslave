package io.chesslave.rendering;

import io.chesslave.eyes.Images;
import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Piece.Type;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import org.junit.Test;
import java.awt.image.BufferedImage;
import static org.junit.Assert.assertTrue;

public class BoardRendererTest {
    private static final String DIR_IMAGES = "/images/";
    private static final String DIR_CHESS_SET = DIR_IMAGES + "set1/";
    private static final String DIR_RENDERING = DIR_IMAGES + "rendering/";

    @Test
    public void canRenderChessboard() throws Exception {
        final Position position = new Position.Builder()
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
        final BufferedImage got = BoardRenderer.render(position, ChessSet.read(DIR_CHESS_SET)).image();
        final BufferedImage expected = Images.read(DIR_RENDERING + "expected.png");
        assertTrue(Images.areEquals(expected, got));
    }
}