package io.chesslave.eyes;

import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import io.chesslave.visual.rendering.BoardRenderer;
import io.chesslave.visual.model.BoardImage;
import javaslang.collection.Set;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import java.awt.image.BufferedImage;
import static org.junit.Assert.assertEquals;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RecognitionTest.FilledSquaresTest.class,
        RecognitionTest.GuessPieceColorTest.class
})
public class RecognitionTest {

    public static class FilledSquaresTest extends BaseRecognitionTest {

        @Test
        public void canFindFilledSquares() throws Exception {
            final Position position = new Position.Builder()
                    .withPiece(Square.of("a1"), Piece.of(Piece.Type.ROOK, Color.WHITE))
                    .withPiece(Square.of("b2"), Piece.of(Piece.Type.KNIGHT, Color.WHITE))
                    .withPiece(Square.of("c3"), Piece.of(Piece.Type.BISHOP, Color.WHITE))
                    .withPiece(Square.of("d4"), Piece.of(Piece.Type.QUEEN, Color.WHITE))
                    .withPiece(Square.of("e5"), Piece.of(Piece.Type.KING, Color.WHITE))
                    .withPiece(Square.of("f6"), Piece.of(Piece.Type.PAWN, Color.WHITE))
                    .withPiece(Square.of("h8"), Piece.of(Piece.Type.ROOK, Color.BLACK))
                    .withPiece(Square.of("g7"), Piece.of(Piece.Type.KNIGHT, Color.BLACK))
                    .withPiece(Square.of("f5"), Piece.of(Piece.Type.BISHOP, Color.BLACK))
                    .withPiece(Square.of("e4"), Piece.of(Piece.Type.QUEEN, Color.BLACK))
                    .withPiece(Square.of("d3"), Piece.of(Piece.Type.KING, Color.BLACK))
                    .withPiece(Square.of("c2"), Piece.of(Piece.Type.PAWN, Color.BLACK))
                    .build();
            final BoardImage board = BoardRenderer.using(chessSet, position).toBoardImage();
            final Set<Square> got = Recognition.filledSquares(board).map(Recognition.SquareGlance::square);
            assertEquals(position.toMap().keySet(), got);
        }
    }

    @Ignore
    public static class GuessPieceColorTest extends SinglePieceRecognitionTest {

        @Override
        void withPieceOnSquare(Square square, Piece piece) throws Exception {
            final Position position = new Position.Builder().withPiece(square, piece).build();
            final BoardImage board = BoardRenderer.using(chessSet, position).toBoardImage();
            final BufferedImage image = board.squareImage(square).image();
            final Color got = Recognition.guessPieceSide(image);
            assertEquals(piece.color, got);
        }
    }
}