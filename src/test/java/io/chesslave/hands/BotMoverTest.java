package io.chesslave.hands;

import static org.junit.Assume.assumeThat;
import static org.hamcrest.CoreMatchers.notNullValue;

import io.chesslave.eyes.Images;
import io.chesslave.model.Square;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Image;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

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
    public Region resetButtonArea;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        // open https://www.chess.com/analysis-board-editor
        final Screen screen = Screen.getPrimaryScreen();

        Region boardArea = null;
        try {
            final BufferedImage initialBoardImage = Images.read(IMAGE_INITIAL_BOARD);
            boardArea = BotMoverTest.findRegion(screen, initialBoardImage);
        } catch (FindFailed findFailed) {
            // ignore
        }

        Region buttonArea = null;
        try {
            final BufferedImage buttonImage1 = Images.read(IMAGE_RESET_BUTTON_1);
            buttonArea = BotMoverTest.findRegion(screen, buttonImage1);
        } catch (FindFailed findFailed) {
            final BufferedImage buttonImage2 = Images.read(IMAGE_RESET_BUTTON_2);
            try {
                buttonArea = BotMoverTest.findRegion(screen, buttonImage2);
            } catch (FindFailed findFailed1) {
                // ignore
            }
        }

        return Arrays.asList(new Object[][]{
                {boardArea != null ? new ClickBotMover(boardArea) : null, buttonArea},
                {boardArea != null ? new DragBotMover(boardArea) : null, buttonArea}
        });
    }

    private static Region findRegion(Screen screen, BufferedImage image) throws FindFailed {
        return screen.find(new Image(image));
    }

    @Before
    public void setUp() throws Exception {
        assumeThat(botMover, notNullValue());
        assumeThat(resetButtonArea, notNullValue());

        // reset board position after each test
        resetButtonArea.click();
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
