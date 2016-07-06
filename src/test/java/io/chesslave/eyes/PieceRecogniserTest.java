package io.chesslave.eyes;

import io.chesslave.eyes.sikuli.SikuliVision;
import io.chesslave.model.Game;
import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import io.chesslave.model.Square;
import io.chesslave.visual.model.BoardImage;
import io.chesslave.visual.rendering.BoardRenderer;
import javaslang.control.Option;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class PieceRecogniserTest extends SinglePieceRecognitionTest {

    private PieceRecogniser recogniser;

    @Before
    public void setUp() throws Exception {
        final Position initialPosition = Game.initialPosition().position();
        final BoardImage initialBoard = BoardRenderer.using(chessSet, initialPosition).toBoardImage();
        final BoardConfiguration config = new BoardAnalyzer().analyze(initialBoard.image());
        this.recogniser = new PieceRecogniser(new SikuliVision(), config);
    }

    @Override
    public void withPieceOnSquare(Square square, Piece piece) throws Exception {
        final Position position = new Position.Builder().withPiece(square, piece).build();
        final BoardImage board = BoardRenderer.using(chessSet, position).toBoardImage();
        final Option<Piece> got = recogniser.piece(board.squareImage(square), Piece.all().toList());
        assertEquals(Option.of(piece), got);
    }
}