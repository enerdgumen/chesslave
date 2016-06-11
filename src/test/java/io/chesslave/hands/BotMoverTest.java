package io.chesslave.hands;

import io.chesslave.model.BoardImageMap;
import io.chesslave.eyes.Images;
import io.chesslave.model.Square;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Image;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
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
    public Point resetPosition;

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws Exception {
        final BufferedImage initialBoardImage = Images.read(IMAGE_INITIAL_BOARD);
        final BoardImageMap boardMap = new BoardImageMap(initialBoardImage.getWidth());
        final Point topLeftCorner = BotMoverTest.getTopLeftBoardCorner(initialBoardImage);

        Point resetButtonPosition;
        try {
            final BufferedImage buttonImage1 = Images.read(IMAGE_RESET_BUTTON_1);
            resetButtonPosition = BotMoverTest.getResetButtonPosition(buttonImage1);
        } catch (FindFailed findFailed) {
            final BufferedImage buttonImage2 = Images.read(IMAGE_RESET_BUTTON_2);
            resetButtonPosition = BotMoverTest.getResetButtonPosition(buttonImage2);
        }

        return Arrays.asList(new Object[][]{
                {new ClickBotMover(boardMap, topLeftCorner), resetButtonPosition},
                {new DragBotMover(boardMap, topLeftCorner), resetButtonPosition}
        });
    }

    private static Point getTopLeftBoardCorner(BufferedImage boardImage) throws FindFailed {
        // open http://www.chess.com/analysis-board-editor
        final Screen screen = Screen.getPrimaryScreen();
        final Match match = screen.find(new Image(boardImage));
        return new Point(match.getX(), match.getY());
    }

    private static Point getResetButtonPosition(BufferedImage buttonImage) throws FindFailed {
        final Screen screen = Screen.getPrimaryScreen();
        final Match match = screen.find(new Image(buttonImage));
        final Location centerPoint = match.getCenter();
        return new Point(centerPoint.getX(), centerPoint.getY());
    }

    @Before
    public void setUp() throws Exception {
        // FIXME simple code to reset board position after each test
        Robot robot = new Robot();
        robot.delay(2000);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseMove(resetPosition.x, resetPosition.y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
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
