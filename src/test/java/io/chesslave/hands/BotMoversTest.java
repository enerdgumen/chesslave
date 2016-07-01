package io.chesslave.hands;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assume.assumeThat;

import io.chesslave.visual.Images;
import io.chesslave.eyes.Vision;
import io.chesslave.eyes.sikuli.SikuliScreen;
import io.chesslave.eyes.sikuli.SikuliVision;
import io.chesslave.hands.sikuli.SikuliPointer;
import io.chesslave.model.Square;
import io.chesslave.visual.model.BoardImage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.Desktop;
import java.awt.Point;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BotMoversTest {
    private static final String DIR_IMAGES = "/images/";
    private static final String DIR_HANDS = DIR_IMAGES + "hands/";
    private static final String DIR_VISUAL = DIR_IMAGES + "visual/";
    private static final String IMAGE_UNFLIPPED_BOARD = DIR_VISUAL + "board.png";
    private static final String IMAGE_FLIPPED_BOARD = DIR_VISUAL + "flipped-board.png";
    private static final String IMAGE_RESET_BUTTON = DIR_HANDS + "chesscom-reset-button.png";
    private static final String IMAGE_FLIP_BUTTON = DIR_HANDS + "chesscom-flip-board-button.png";
    private static final String CHESS_BOARD_WEB_PAGE = "https://www.chess.com/analysis-board-editor";

    @Parameterized.Parameter
    public BaseBotMover botMover;

    @Parameterized.Parameter(value = 1)
    public Point resetButtonPoint;

    @Parameterized.Parameter(value = 2)
    public Point flipButtonPoint;

    private Pointer pointer;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        BoardImage unflippedBoard = null;
        BoardImage flippedBoard = null;
        Point resetPoint = null;
        Point flipPoint = null;
        try {
            Desktop.getDesktop().browse(new URI(CHESS_BOARD_WEB_PAGE));
            // 5 seconds to open the browser
            Thread.sleep(5000L);

            final SikuliScreen screen = new SikuliScreen();
            Vision.Recogniser recogniser = new SikuliVision().recognise(screen.captureAll());

            unflippedBoard = recogniser.bestMatch(Images.read(IMAGE_UNFLIPPED_BOARD))
                    .map(match -> new BoardImage(match.image(), match.region().getLocation(), false))
                    .getOrElse((BoardImage) null);
            resetPoint = recogniser.bestMatch(Images.read(IMAGE_RESET_BUTTON))
                    .map(Vision.Match::region)
                    .map(rect -> new Point((int) rect.getCenterX(), (int) rect.getCenterY()))
                    .getOrElse((Point) null);

            // flip board
            flipPoint = recogniser.bestMatch(Images.read(IMAGE_FLIP_BUTTON))
                    .map(Vision.Match::region)
                    .map(rect -> new Point((int) rect.getCenterX(), (int) rect.getCenterY()))
                    .getOrElse((Point) null);
            if (flipPoint != null) {
                new SikuliPointer().click(flipPoint);
            }
            // wait webapp's response
            Thread.sleep(500);
            // recapture screen after flip
            recogniser = new SikuliVision().recognise(screen.captureAll());

            flippedBoard = recogniser.bestMatch(Images.read(IMAGE_FLIPPED_BOARD))
                    .map(match -> new BoardImage(match.image(), match.region().getLocation(), true))
                    .getOrElse((BoardImage) null);
        } catch (Exception e) {
            // ignore
        }

        return Arrays.asList(new Object[][]{
                {unflippedBoard != null ? new ClickBotMover(unflippedBoard) : null, resetPoint, flipPoint},
                {unflippedBoard != null ? new DragBotMover(unflippedBoard) : null, resetPoint, null},
                {flippedBoard != null ? new ClickBotMover(flippedBoard) : null, resetPoint, flipPoint},
                {flippedBoard != null ? new DragBotMover(flippedBoard) : null, resetPoint, null}
        });
    }

    @Before
    public void setUp() throws Exception {
        assumeThat(botMover, notNullValue());
        assumeThat(resetButtonPoint, notNullValue());

        pointer = new SikuliPointer();

        // flip board when necessary
        if (flipButtonPoint != null) {
            pointer.click(flipButtonPoint);
        }

        // reset board position after each sequence
        pointer.click(resetButtonPoint);
    }

    @Test
    public void spanishOpeningTest() throws Exception {
        botMover.move(Square.of("e2"), Square.of("e4"));
        botMover.move(Square.of("e7"), Square.of("e5"));
        botMover.move(Square.of("g1"), Square.of("f3"));
        botMover.move(Square.of("b8"), Square.of("c6"));
        botMover.move(Square.of("f1"), Square.of("b5"));
        botMover.move(Square.of("g8"), Square.of("f6"));
        botMover.move(Square.of("e1"), Square.of("g1"));
        botMover.move(Square.of("f8"), Square.of("e7"));
        botMover.move(Square.of("f1"), Square.of("e1"));
        botMover.move(Square.of("a7"), Square.of("a6"));
        // awkward move, but it tests captures ;)
        botMover.move(Square.of("b5"), Square.of("c6"));
    }
}
