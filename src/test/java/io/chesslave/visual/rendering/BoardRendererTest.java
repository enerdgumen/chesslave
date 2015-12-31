package io.chesslave.visual.rendering;

import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Piece.Type;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import io.chesslave.visual.Images;
import org.junit.Assert;
import org.junit.Test;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

public class BoardRendererTest {

    @Test
    public void canRenderChessboard() throws Exception {
        final Position position = new Position.Builder()
                .withPiece(Square.of("a1"), new Piece(Type.ROOK, Color.WHITE))
                .withPiece(Square.of("b2"), new Piece(Type.KNIGHT, Color.WHITE))
                .withPiece(Square.of("c3"), new Piece(Type.BISHOP, Color.WHITE))
                .withPiece(Square.of("d4"), new Piece(Type.QUEEN, Color.WHITE))
                .withPiece(Square.of("e5"), new Piece(Type.KING, Color.WHITE))
                .withPiece(Square.of("f6"), new Piece(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("h8"), new Piece(Type.ROOK, Color.BLACK))
                .withPiece(Square.of("g7"), new Piece(Type.KNIGHT, Color.BLACK))
                .withPiece(Square.of("f5"), new Piece(Type.BISHOP, Color.BLACK))
                .withPiece(Square.of("e4"), new Piece(Type.QUEEN, Color.BLACK))
                .withPiece(Square.of("d3"), new Piece(Type.KING, Color.BLACK))
                .withPiece(Square.of("c2"), new Piece(Type.PAWN, Color.BLACK))
                .build();
        final BufferedImage got = BoardRenderer.render(position, ChessSet.read(Paths.get("/images/set1")));
        final BufferedImage expected = Images.read("/images/rendering/expected.png");
        Assert.assertEquals(true, Images.areEquals(expected, got));
    }
}