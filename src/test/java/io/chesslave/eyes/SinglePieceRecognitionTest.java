package io.chesslave.eyes;

import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Square;
import org.junit.Test;

public abstract class SinglePieceRecognitionTest extends BaseRecognitionTest {

    @Test
    public void whiteRookOnDarkSquare() throws Exception {
        withPieceOnSquare(Square.of("a1"), Piece.of(Piece.Type.ROOK, Color.WHITE));
    }

    @Test
    public void blackRookOnDarkSquare() throws Exception {
        withPieceOnSquare(Square.of("a1"), Piece.of(Piece.Type.ROOK, Color.BLACK));
    }

    @Test
    public void whiteRookOnLightSquare() throws Exception {
        withPieceOnSquare(Square.of("b1"), Piece.of(Piece.Type.ROOK, Color.WHITE));
    }

    @Test
    public void blackRookOnLightSquare() throws Exception {
        withPieceOnSquare(Square.of("b1"), Piece.of(Piece.Type.ROOK, Color.BLACK));
    }

    @Test
    public void whiteKnightOnDarkSquare() throws Exception {
        withPieceOnSquare(Square.of("a1"), Piece.of(Piece.Type.KNIGHT, Color.WHITE));
    }

    @Test
    public void blackKnightOnDarkSquare() throws Exception {
        withPieceOnSquare(Square.of("a1"), Piece.of(Piece.Type.KNIGHT, Color.BLACK));
    }

    @Test
    public void whiteKnightOnLightSquare() throws Exception {
        withPieceOnSquare(Square.of("b1"), Piece.of(Piece.Type.KNIGHT, Color.WHITE));
    }

    @Test
    public void blackKnightOnLightSquare() throws Exception {
        withPieceOnSquare(Square.of("b1"), Piece.of(Piece.Type.KNIGHT, Color.BLACK));
    }

    @Test
    public void whitePawnOnDarkSquare() throws Exception {
        withPieceOnSquare(Square.of("a1"), Piece.of(Piece.Type.PAWN, Color.WHITE));
    }

    @Test
    public void blackPawnOnDarkSquare() throws Exception {
        withPieceOnSquare(Square.of("a1"), Piece.of(Piece.Type.PAWN, Color.BLACK));
    }

    @Test
    public void whitePawnOnLightSquare() throws Exception {
        withPieceOnSquare(Square.of("b1"), Piece.of(Piece.Type.PAWN, Color.WHITE));
    }

    @Test
    public void blackPawnOnLightSquare() throws Exception {
        withPieceOnSquare(Square.of("b1"), Piece.of(Piece.Type.PAWN, Color.BLACK));
    }

    @Test
    public void whiteQueenOnDarkSquare() throws Exception {
        withPieceOnSquare(Square.of("a1"), Piece.of(Piece.Type.QUEEN, Color.WHITE));
    }

    @Test
    public void blackQueenOnDarkSquare() throws Exception {
        withPieceOnSquare(Square.of("a1"), Piece.of(Piece.Type.QUEEN, Color.BLACK));
    }

    @Test
    public void whiteQueenOnLightSquare() throws Exception {
        withPieceOnSquare(Square.of("b1"), Piece.of(Piece.Type.QUEEN, Color.WHITE));
    }

    @Test
    public void blackQueenOnLightSquare() throws Exception {
        withPieceOnSquare(Square.of("b1"), Piece.of(Piece.Type.QUEEN, Color.BLACK));
    }

    // FAIL
    @Test
    public void whiteKingOnDarkSquare() throws Exception {
        withPieceOnSquare(Square.of("a1"), Piece.of(Piece.Type.KING, Color.WHITE));
    }

    @Test
    public void blackKingOnDarkSquare() throws Exception {
        withPieceOnSquare(Square.of("a1"), Piece.of(Piece.Type.KING, Color.BLACK));
    }

    @Test
    public void whiteKingOnLightSquare() throws Exception {
        withPieceOnSquare(Square.of("b1"), Piece.of(Piece.Type.KING, Color.WHITE));
    }

    @Test
    public void blackKingOnLightSquare() throws Exception {
        withPieceOnSquare(Square.of("b1"), Piece.of(Piece.Type.KING, Color.BLACK));
    }

    @Test
    public void whiteBishopOnDarkSquare() throws Exception {
        withPieceOnSquare(Square.of("a1"), Piece.of(Piece.Type.BISHOP, Color.WHITE));
    }

    @Test
    public void blackBishopOnDarkSquare() throws Exception {
        withPieceOnSquare(Square.of("a1"), Piece.of(Piece.Type.BISHOP, Color.BLACK));
    }

    @Test
    public void whiteBishopOnLightSquare() throws Exception {
        withPieceOnSquare(Square.of("b1"), Piece.of(Piece.Type.BISHOP, Color.WHITE));
    }

    @Test
    public void blackBishopOnLightSquare() throws Exception {
        withPieceOnSquare(Square.of("b1"), Piece.of(Piece.Type.BISHOP, Color.BLACK));
    }

    @Test
    public void emptyLightSquare() throws Exception {
        withEmptySquare(Square.of("b1"));
    }

    @Test
    public void emptyDarkSquare() throws Exception {
        withEmptySquare(Square.of("a1"));
    }

    public void withPieceOnSquare(Square square, Piece piece) throws Exception {
    }

    public void withEmptySquare(Square square) throws Exception {
    }
}
