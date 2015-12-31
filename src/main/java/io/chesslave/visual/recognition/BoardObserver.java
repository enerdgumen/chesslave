package io.chesslave.visual.recognition;

import io.chesslave.model.Square;
import javaslang.collection.Set;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Image;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.image.BufferedImage;

public class BoardObserver {

    private BufferedImage currentImg;

    public void start(BoardConfiguration config) throws Exception {
        final Logger logger = LoggerFactory.getLogger(getClass());
        final Screen screen = Screen.getScreen(1);
        currentImg = config.boardImage;
        findBoard(screen);
        while (true) {
            currentImg = screen.capture().getImage();
            final Set<Square> glance = Recognition.filledSquares(currentImg);
            logger.debug("filled squares: {}", glance);
            Thread.sleep(2000);
        }
    }

    private void findBoard(Screen screen) throws FindFailed {
        final Match match = screen.find(new Image(currentImg));
        screen.setRect(match.getRect());
        screen.highlight(1);
    }
}