package io.chesslave.rendering;

import io.chesslave.model.Board;
import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Piece.Type;
import io.chesslave.model.Position;
import io.chesslave.recognition.Images;
import org.junit.Assert;
import org.junit.Test;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

public class BoardRendererTest {

    @Test
    public void canRenderChessboard() throws Exception {
        final Position position = new Position.Builder()
                .withPiece(Board.standard.square("a1"), new Piece(Type.ROOK, Color.WHITE))
                .withPiece(Board.standard.square("b2"), new Piece(Type.KNIGHT, Color.WHITE))
                .withPiece(Board.standard.square("c3"), new Piece(Type.BISHOP, Color.WHITE))
                .withPiece(Board.standard.square("d4"), new Piece(Type.QUEEN, Color.WHITE))
                .withPiece(Board.standard.square("e5"), new Piece(Type.KING, Color.WHITE))
                .withPiece(Board.standard.square("f6"), new Piece(Type.PAWN, Color.WHITE))
                .withPiece(Board.standard.square("h8"), new Piece(Type.ROOK, Color.BLACK))
                .withPiece(Board.standard.square("g7"), new Piece(Type.KNIGHT, Color.BLACK))
                .withPiece(Board.standard.square("f5"), new Piece(Type.BISHOP, Color.BLACK))
                .withPiece(Board.standard.square("e4"), new Piece(Type.QUEEN, Color.BLACK))
                .withPiece(Board.standard.square("d3"), new Piece(Type.KING, Color.BLACK))
                .withPiece(Board.standard.square("c2"), new Piece(Type.PAWN, Color.BLACK))
                .build();
        final BufferedImage got = BoardRenderer.render(position, ChessSet.read(Paths.get("/images/set1")));
        final BufferedImage expected = Images.read("/images/rendering/expected.png");
        Assert.assertEquals(true, Images.areEquals(expected, got));
    }
}