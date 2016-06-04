package io.chesslave.visual.recognition;

import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Piece.Type;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import io.chesslave.visual.BoardImage;
import io.chesslave.visual.rendering.BoardRenderer;
import javaslang.collection.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import java.awt.image.BufferedImage;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RecognitionTest.FilledSquaresTest.class,
        RecognitionTest.GuessPieceColorTest.class
})
public class RecognitionTest {

    public static class FilledSquaresTest extends ChessSetTest {

        @Test
        public void findsFilledSquares() throws Exception {
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
            final BoardImage board = new BoardImage(BoardRenderer.render(position, set));
            final Set<Square> got = Recognition.filledSquares(board).map(it -> it.square());
            Assert.assertEquals(position.get().keySet(), got);
        }
    }

    public static class GuessPieceColorTest extends ChessSetTest {

        @Test
        public void whiteRookOnDarkSquare() throws Exception {
            Assert.assertEquals(Color.WHITE, guess(Square.of("a1"), Piece.of(Type.ROOK, Color.WHITE)));
        }

        @Test
        public void blackRookOnDarkSquare() throws Exception {
            Assert.assertEquals(Color.BLACK, guess(Square.of("a1"), Piece.of(Type.ROOK, Color.BLACK)));
        }

        @Test
        public void whiteRookOnLightSquare() throws Exception {
            Assert.assertEquals(Color.WHITE, guess(Square.of("b1"), Piece.of(Type.ROOK, Color.WHITE)));
        }

        @Test
        public void blackRookOnLightSquare() throws Exception {
            Assert.assertEquals(Color.BLACK, guess(Square.of("b1"), Piece.of(Type.ROOK, Color.BLACK)));
        }

        @Test
        public void whiteKnightOnDarkSquare() throws Exception {
            Assert.assertEquals(Color.WHITE, guess(Square.of("a1"), Piece.of(Type.KNIGHT, Color.WHITE)));
        }

        @Test
        public void blackKnightOnDarkSquare() throws Exception {
            Assert.assertEquals(Color.BLACK, guess(Square.of("a1"), Piece.of(Type.KNIGHT, Color.BLACK)));
        }

        @Test
        public void whiteKnightOnLightSquare() throws Exception {
            Assert.assertEquals(Color.WHITE, guess(Square.of("b1"), Piece.of(Type.KNIGHT, Color.WHITE)));
        }

        @Test
        public void blackKnightOnLightSquare() throws Exception {
            Assert.assertEquals(Color.BLACK, guess(Square.of("b1"), Piece.of(Type.KNIGHT, Color.BLACK)));
        }

        @Test
        public void whitePawnOnDarkSquare() throws Exception {
            Assert.assertEquals(Color.WHITE, guess(Square.of("a1"), Piece.of(Type.PAWN, Color.WHITE)));
        }

        @Test
        public void blackPawnOnDarkSquare() throws Exception {
            Assert.assertEquals(Color.BLACK, guess(Square.of("a1"), Piece.of(Type.PAWN, Color.BLACK)));
        }

        @Test
        public void whitePawnOnLightSquare() throws Exception {
            Assert.assertEquals(Color.WHITE, guess(Square.of("b1"), Piece.of(Type.PAWN, Color.WHITE)));
        }

        @Test
        public void blackPawnOnLightSquare() throws Exception {
            Assert.assertEquals(Color.BLACK, guess(Square.of("b1"), Piece.of(Type.PAWN, Color.BLACK)));
        }

        @Test
        public void whiteQueenOnDarkSquare() throws Exception {
            Assert.assertEquals(Color.WHITE, guess(Square.of("a1"), Piece.of(Type.QUEEN, Color.WHITE)));
        }

        @Test
        public void blackQueenOnDarkSquare() throws Exception {
            Assert.assertEquals(Color.BLACK, guess(Square.of("a1"), Piece.of(Type.QUEEN, Color.BLACK)));
        }

        @Test
        public void whiteQueenOnLightSquare() throws Exception {
            Assert.assertEquals(Color.WHITE, guess(Square.of("b1"), Piece.of(Type.QUEEN, Color.WHITE)));
        }

        @Test
        public void blackQueenOnLightSquare() throws Exception {
            Assert.assertEquals(Color.BLACK, guess(Square.of("b1"), Piece.of(Type.QUEEN, Color.BLACK)));
        }

        // FAIL
        @Test
        public void whiteKingOnDarkSquare() throws Exception {
            Assert.assertEquals(Color.WHITE, guess(Square.of("a1"), Piece.of(Type.KING, Color.WHITE)));
        }

        @Test
        public void blackKingOnDarkSquare() throws Exception {
            Assert.assertEquals(Color.BLACK, guess(Square.of("a1"), Piece.of(Type.KING, Color.BLACK)));
        }

        @Test
        public void whiteKingOnLightSquare() throws Exception {
            Assert.assertEquals(Color.WHITE, guess(Square.of("b1"), Piece.of(Type.KING, Color.WHITE)));
        }

        @Test
        public void blackKingOnLightSquare() throws Exception {
            Assert.assertEquals(Color.BLACK, guess(Square.of("b1"), Piece.of(Type.KING, Color.BLACK)));
        }

        @Test
        public void whiteBishopOnDarkSquare() throws Exception {
            Assert.assertEquals(Color.WHITE, guess(Square.of("a1"), Piece.of(Type.BISHOP, Color.WHITE)));
        }

        @Test
        public void blackBishopOnDarkSquare() throws Exception {
            Assert.assertEquals(Color.BLACK, guess(Square.of("a1"), Piece.of(Type.BISHOP, Color.BLACK)));
        }

        @Test
        public void whiteBishopOnLightSquare() throws Exception {
            Assert.assertEquals(Color.WHITE, guess(Square.of("b1"), Piece.of(Type.BISHOP, Color.WHITE)));
        }

        @Test
        public void blackBishopOnLightSquare() throws Exception {
            Assert.assertEquals(Color.BLACK, guess(Square.of("b1"), Piece.of(Type.BISHOP, Color.BLACK)));
        }

        private Color guess(Square square, Piece piece) throws Exception {
            final Position position = new Position.Builder().withPiece(square, piece).build();
            final BoardImage board = new BoardImage(BoardRenderer.render(position, set));
            final BufferedImage image = board.squareImage(square);
            return Recognition.guessPieceSide(image);
        }
    }
}