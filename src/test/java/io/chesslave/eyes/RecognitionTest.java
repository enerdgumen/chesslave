package io.chesslave.eyes;

import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Piece.Type;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import io.chesslave.rendering.BoardRenderer;
import io.chesslave.visual.BoardImage;
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
            final BoardImage board = new BoardImage(BoardRenderer.render(position, chessSet));
            final Set<Square> got = Recognition.filledSquares(board).map(Recognition.SquareGlance::square);
            assertEquals(position.toMap().keySet(), got);
        }
    }

    @Ignore
    public static class GuessPieceColorTest extends BaseRecognitionTest {

        @Test
        public void whiteRookOnDarkSquare() throws Exception {
            assertEquals(Color.WHITE, guess(Square.of("a1"), Piece.of(Type.ROOK, Color.WHITE)));
        }

        @Test
        public void blackRookOnDarkSquare() throws Exception {
            assertEquals(Color.BLACK, guess(Square.of("a1"), Piece.of(Type.ROOK, Color.BLACK)));
        }

        @Test
        public void whiteRookOnLightSquare() throws Exception {
            assertEquals(Color.WHITE, guess(Square.of("b1"), Piece.of(Type.ROOK, Color.WHITE)));
        }

        @Test
        public void blackRookOnLightSquare() throws Exception {
            assertEquals(Color.BLACK, guess(Square.of("b1"), Piece.of(Type.ROOK, Color.BLACK)));
        }

        @Test
        public void whiteKnightOnDarkSquare() throws Exception {
            assertEquals(Color.WHITE, guess(Square.of("a1"), Piece.of(Type.KNIGHT, Color.WHITE)));
        }

        @Test
        public void blackKnightOnDarkSquare() throws Exception {
            assertEquals(Color.BLACK, guess(Square.of("a1"), Piece.of(Type.KNIGHT, Color.BLACK)));
        }

        @Test
        public void whiteKnightOnLightSquare() throws Exception {
            assertEquals(Color.WHITE, guess(Square.of("b1"), Piece.of(Type.KNIGHT, Color.WHITE)));
        }

        @Test
        public void blackKnightOnLightSquare() throws Exception {
            assertEquals(Color.BLACK, guess(Square.of("b1"), Piece.of(Type.KNIGHT, Color.BLACK)));
        }

        @Test
        public void whitePawnOnDarkSquare() throws Exception {
            assertEquals(Color.WHITE, guess(Square.of("a1"), Piece.of(Type.PAWN, Color.WHITE)));
        }

        @Test
        public void blackPawnOnDarkSquare() throws Exception {
            assertEquals(Color.BLACK, guess(Square.of("a1"), Piece.of(Type.PAWN, Color.BLACK)));
        }

        @Test
        public void whitePawnOnLightSquare() throws Exception {
            assertEquals(Color.WHITE, guess(Square.of("b1"), Piece.of(Type.PAWN, Color.WHITE)));
        }

        @Test
        public void blackPawnOnLightSquare() throws Exception {
            assertEquals(Color.BLACK, guess(Square.of("b1"), Piece.of(Type.PAWN, Color.BLACK)));
        }

        @Test
        public void whiteQueenOnDarkSquare() throws Exception {
            assertEquals(Color.WHITE, guess(Square.of("a1"), Piece.of(Type.QUEEN, Color.WHITE)));
        }

        @Test
        public void blackQueenOnDarkSquare() throws Exception {
            assertEquals(Color.BLACK, guess(Square.of("a1"), Piece.of(Type.QUEEN, Color.BLACK)));
        }

        @Test
        public void whiteQueenOnLightSquare() throws Exception {
            assertEquals(Color.WHITE, guess(Square.of("b1"), Piece.of(Type.QUEEN, Color.WHITE)));
        }

        @Test
        public void blackQueenOnLightSquare() throws Exception {
            assertEquals(Color.BLACK, guess(Square.of("b1"), Piece.of(Type.QUEEN, Color.BLACK)));
        }

        // FAIL
        @Test
        public void whiteKingOnDarkSquare() throws Exception {
            assertEquals(Color.WHITE, guess(Square.of("a1"), Piece.of(Type.KING, Color.WHITE)));
        }

        @Test
        public void blackKingOnDarkSquare() throws Exception {
            assertEquals(Color.BLACK, guess(Square.of("a1"), Piece.of(Type.KING, Color.BLACK)));
        }

        @Test
        public void whiteKingOnLightSquare() throws Exception {
            assertEquals(Color.WHITE, guess(Square.of("b1"), Piece.of(Type.KING, Color.WHITE)));
        }

        @Test
        public void blackKingOnLightSquare() throws Exception {
            assertEquals(Color.BLACK, guess(Square.of("b1"), Piece.of(Type.KING, Color.BLACK)));
        }

        @Test
        public void whiteBishopOnDarkSquare() throws Exception {
            assertEquals(Color.WHITE, guess(Square.of("a1"), Piece.of(Type.BISHOP, Color.WHITE)));
        }

        @Test
        public void blackBishopOnDarkSquare() throws Exception {
            assertEquals(Color.BLACK, guess(Square.of("a1"), Piece.of(Type.BISHOP, Color.BLACK)));
        }

        @Test
        public void whiteBishopOnLightSquare() throws Exception {
            assertEquals(Color.WHITE, guess(Square.of("b1"), Piece.of(Type.BISHOP, Color.WHITE)));
        }

        @Test
        public void blackBishopOnLightSquare() throws Exception {
            assertEquals(Color.BLACK, guess(Square.of("b1"), Piece.of(Type.BISHOP, Color.BLACK)));
        }

        private Color guess(Square square, Piece piece) throws Exception {
            final Position position = new Position.Builder().withPiece(square, piece).build();
            final BoardImage board = new BoardImage(BoardRenderer.render(position, chessSet));
            final BufferedImage image = board.squareImage(square).image();
            return Recognition.guessPieceSide(image);
        }
    }
}