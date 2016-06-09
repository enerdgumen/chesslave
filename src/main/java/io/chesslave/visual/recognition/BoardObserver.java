package io.chesslave.visual.recognition;

import io.chesslave.model.*;
import io.chesslave.visual.BoardImage;
import javaslang.collection.List;
import org.sikuli.script.Image;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardObserver {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void start(BoardConfiguration config) throws Exception {
        final PositionRecogniser positionRecogniser = new FullPositionRecogniser(config);
        final MoveRecognizer moveRecognizer = new MoveRecognizer();
        final Screen screen = Screen.getScreen(1);
        final Match match = screen.find(new Image(config.board.image()));
        screen.setRect(match.getRect());
        screen.highlight(1);
        BoardImage previousImage = new BoardImage(screen.capture().getImage());
        Position previousPos = positionRecogniser.begin(previousImage).get();
        logger.debug("initial position:\n{}", Positions.toText(previousPos));
        Game game = new Game(previousPos, List.empty(), Color.WHITE);
        while (true) {
            final BoardImage currentImage = new BoardImage(screen.capture().getImage());
            final Position currentPos = positionRecogniser.next(previousPos, previousImage, currentImage).get();
            if (previousPos.equals(currentPos)) {
                logger.debug("nothing is changed...", currentPos);
                Thread.sleep(2000);
                continue;
            }
            logger.debug("current position:\n{}", Positions.toText(currentPos));
            final Move move = moveRecognizer.detect(previousPos, currentPos);
            logger.info("detected move {}", move);
            game = game.move(move);
            previousImage = currentImage;
            previousPos = currentPos;
        }
    }
}