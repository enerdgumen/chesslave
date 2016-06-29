package io.chesslave.eyes;

import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import io.chesslave.rendering.BoardRenderer;
import io.chesslave.visual.BoardImage;
import static org.junit.Assert.assertEquals;

public class EmptySquareRecogniserTest extends SinglePieceRecognitionTest {

    @Override
    public void withPieceOnSquare(Square square, Piece piece) throws Exception {
        final Position position = new Position.Builder().withPiece(square, piece).build();
        final BoardImage board = BoardRenderer.using(chessSet, position).toBoardImage();
        final boolean got = new EmptySquareRecogniser().isEmpty(board.squareImage(square));
        assertEquals(false, got);
    }

    @Override
    public void withEmptySquare(Square square) throws Exception {
        final Position position = new Position.Builder().build();
        final BoardImage board = BoardRenderer.using(chessSet, position).toBoardImage();
        final boolean got = new EmptySquareRecogniser().isEmpty(board.squareImage(square));
        assertEquals(true, got);
    }
}