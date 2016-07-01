package io.chesslave.eyes;

import static org.junit.Assert.assertEquals;

import io.chesslave.eyes.sikuli.SikuliVision;
import io.chesslave.model.Game;
import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import io.chesslave.model.Positions;
import io.chesslave.model.Square;
import io.chesslave.visual.rendering.BoardRenderer;
import io.chesslave.visual.model.BoardImage;
import javaslang.control.Option;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import java.util.Optional;

@Ignore
public class FullPositionRecogniserTest extends SinglePieceRecognitionTest {

    private FullPositionRecogniser recogniser;

    @Before
    public void setUp() throws Exception {
        final Position initialPosition = Game.initialPosition().position();
        final BoardImage initialBoard = BoardRenderer.using(chessSet, initialPosition).toBoardImage();
        final BoardConfiguration config = new BoardAnalyzer().analyze(initialBoard.image());
        this.recogniser = new FullPositionRecogniser(new SikuliVision(), config);
    }

    @Test
    public void recognisePosition() throws Exception {
        final Position position = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | | | | | | | ",
                " | | |p|P| | | ",
                " | | | | |N| | ",
                "P|P|P| | |P|P|P",
                "R|N|B|Q|K|B| |R");
        final BoardImage board = BoardRenderer.using(chessSet, position).toBoardImage();
        final Option<Position> got = recogniser.begin(board);
        assertEquals(Optional.of(position), got);
    }

    public void withPieceOnSquare(Square square, Piece piece) throws Exception {
        final Position position = new Position.Builder().withPiece(square, piece).build();
        final BoardImage board = BoardRenderer.using(chessSet, position).toBoardImage();
        final Option<Position> got = recogniser.begin(board);
        assertEquals(Optional.of(position), got);
    }
}