package io.chesslave.hands;

import static org.junit.Assume.assumeThat;
import static org.hamcrest.CoreMatchers.notNullValue;

import io.chesslave.eyes.Images;
import io.chesslave.eyes.Vision;
import io.chesslave.eyes.sikuli.SikuliScreen;
import io.chesslave.eyes.sikuli.SikuliVision;
import io.chesslave.hands.sikuli.SikuliMouse;
import io.chesslave.model.Square;
import javaslang.control.Option;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
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

    @Parameterized.Parameter
    public BaseBotMover botMover;

    @Parameterized.Parameter(value = 1)
    public Rectangle resetButtonArea;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        // open https://www.chess.com/analysis-board-editor
        io.chesslave.eyes.Screen screen = new SikuliScreen();
        Vision.Recogniser recogniser = new SikuliVision().recognise(screen.captureAll());

        final BufferedImage initialBoardImage = Images.read(IMAGE_INITIAL_BOARD);
        Option<Vision.Match> boardAreaMatch = recogniser.bestMatch(initialBoardImage);
        final Rectangle boardArea = BotMoverTest.getRectangleOrNull(boardAreaMatch);

        final BufferedImage buttonImage1 = Images.read(IMAGE_RESET_BUTTON_1);
        Option<Vision.Match> buttonMatch = recogniser.bestMatch(buttonImage1);
        if (buttonMatch.isEmpty()) {
            final BufferedImage buttonImage2 = Images.read(IMAGE_RESET_BUTTON_2);
            buttonMatch = recogniser.bestMatch(buttonImage2);
        }
        final Rectangle button = BotMoverTest.getRectangleOrNull(buttonMatch);

        return Arrays.asList(new Object[][]{
                {boardArea != null ? new ClickBotMover(boardArea) : null, button},
                {boardArea != null ? new DragBotMover(boardArea) : null, button}
        });
    }

    private static Rectangle getRectangleOrNull(Option<Vision.Match> visionMatch) {
        return visionMatch.isDefined() ? visionMatch.get().region() : null;
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
