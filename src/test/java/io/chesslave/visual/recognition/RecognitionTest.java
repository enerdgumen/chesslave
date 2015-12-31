package io.chesslave.visual.recognition;

import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import io.chesslave.visual.rendering.BoardRenderer;
import javaslang.collection.Set;
import org.junit.Assert;
import org.junit.Test;
import java.awt.image.BufferedImage;

public class RecognitionTest extends ChessSetTest {

    @Test
    public void canFindFilledSquares() throws Exception {
        final Position position = new Position.Builder()
                .withPiece(Square.of("a1"), new Piece(Piece.Type.ROOK, Color.WHITE))
                .withPiece(Square.of("b2"), new Piece(Piece.Type.KNIGHT, Color.WHITE))
                .withPiece(Square.of("c3"), new Piece(Piece.Type.BISHOP, Color.WHITE))
                .withPiece(Square.of("d4"), new Piece(Piece.Type.QUEEN, Color.WHITE))
                .withPiece(Square.of("e5"), new Piece(Piece.Type.KING, Color.WHITE))
                .withPiece(Square.of("f6"), new Piece(Piece.Type.PAWN, Color.WHITE))
                .withPiece(Square.of("h8"), new Piece(Piece.Type.ROOK, Color.BLACK))
                .withPiece(Square.of("g7"), new Piece(Piece.Type.KNIGHT, Color.BLACK))
                .withPiece(Square.of("f5"), new Piece(Piece.Type.BISHOP, Color.BLACK))
                .withPiece(Square.of("e4"), new Piece(Piece.Type.QUEEN, Color.BLACK))
                .withPiece(Square.of("d3"), new Piece(Piece.Type.KING, Color.BLACK))
                .withPiece(Square.of("c2"), new Piece(Piece.Type.PAWN, Color.BLACK))
                .build();
        final BufferedImage image = BoardRenderer.render(position, set);
        final Set<Square> got = Recognition.filledSquares(image);
        Assert.assertEquals(position.get().keySet(), got);
    }
}