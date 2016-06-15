package io.chesslave.hands;

import static org.junit.Assume.assumeThat;
import static org.hamcrest.CoreMatchers.notNullValue;

import io.chesslave.eyes.Images;
import io.chesslave.eyes.Vision;
import io.chesslave.eyes.sikuli.SikuliScreen;
import io.chesslave.eyes.sikuli.SikuliVision;
import io.chesslave.hands.sikuli.SikuliMouse;
import io.chesslave.model.Square;
import javaslang.collection.Stream;
import javaslang.control.Option;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.Desktop;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BotMoverTest {
    private static final String DIR_IMAGES = "/images/";
    private static final String DIR_PIECE_SET = DIR_IMAGES + "set1/";
    private static final String DIR_HANDS = DIR_IMAGES + "hands/";
    private static final String IMAGE_INITIAL_BOARD = DIR_PIECE_SET + "initial-board.png";
    private static final String IMAGE_RESET_BUTTON_1 = DIR_HANDS + "chesscom-reset-button-unpressed.png";
    private static final String IMAGE_RESET_BUTTON_2 = DIR_HANDS + "chesscom-reset-button-pressed.png";
    private static final String CHESS_BOARD_WEB_PAGE = "https://www.chess.com/analysis-board-editor";

    @Parameterized.Parameter
    public BaseBotMover botMover;

    @Parameterized.Parameter(value = 1)
    public Rectangle resetButtonArea;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Rectangle boardArea = null;
        Rectangle button = null;
        try {
            Desktop.getDesktop().browse(new URI(CHESS_BOARD_WEB_PAGE));
            // 5 seconds to open the browser
            Thread.sleep(5000);

            io.chesslave.eyes.Screen screen = new SikuliScreen();
            final Vision.Recogniser recogniser = new SikuliVision().recognise(screen.captureAll());

            boardArea = BotMoverTest.findRectangle(recogniser, IMAGE_INITIAL_BOARD);
            button = BotMoverTest.findRectangle(recogniser, IMAGE_RESET_BUTTON_1, IMAGE_RESET_BUTTON_2);
        } catch (Exception e) {
            // ignore
        }

        return Arrays.asList(new Object[][]{
                {boardArea != null ? new ClickBotMover(boardArea) : null, button},
                {boardArea != null ? new DragBotMover(boardArea) : null, button}
        });
    }

    private static Rectangle findRectangle(Vision.Recogniser recogniser, String...images) {
        Option<Vision.Match> match = Stream.of(images)
                .map(image -> recogniser.bestMatch(Images.read(image)))
                .takeUntil(Option::isEmpty)
                .head();
        return match.isDefined() ? match.get().region() : null;
    }

    @Before
    public void setUp() throws Exception {
        assumeThat(botMover, notNullValue());
        assumeThat(resetButtonArea, notNullValue());

        // reset board position after each test
        Point point = new Point((int) resetButtonArea.getCenterX(), (int) resetButtonArea.getCenterY());
        new SikuliMouse().click(point);
    }

    @Test
    public void sicilianTest() throws Exception {
        botMover.move(Square.of("e2"), Square.of("e4"));
        botMover.move(Square.of("c7"), Square.of("c5"));
        botMover.move(Square.of("g1"), Square.of("f3"));
        botMover.move(Square.of("d7"), Square.of("d6"));
        botMover.move(Square.of("d2"), Square.of("d4"));
        botMover.move(Square.of("c5"), Square.of("d4"));
    }

    @Test
    public void spanishTest() throws Exception {
        botMover.move(Square.of("e2"), Square.of("e4"));
        botMover.move(Square.of("e7"), Square.of("e5"));
        botMover.move(Square.of("g1"), Square.of("f3"));
        botMover.move(Square.of("b8"), Square.of("c6"));
        botMover.move(Square.of("f1"), Square.of("b5"));
        botMover.move(Square.of("g8"), Square.of("f6"));
    }
}
