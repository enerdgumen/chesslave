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
        final BufferedImage got = BoardRenderer.render(position, ChessSet.read(Paths.get("/images/set1")));
        final BufferedImage expected = Images.read("/images/rendering/expected.png");
        Assert.assertEquals(true, Images.areEquals(expected, got));
    }
}