package io.chesslave.visual.recognition;

import io.chesslave.model.*;
import javaslang.collection.List;
import org.sikuli.script.Image;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardObserver {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void start(BoardConfiguration config) throws Exception {
        final PositionRecogniser positionRecogniser = new PositionRecogniser(
                new PieceTargetFactory(config),
                new PieceRecogniser());
        final MoveRecognizer moveRecognizer = new MoveRecognizer();
        final Screen screen = Screen.getScreen(1);
        final Match match = screen.find(new Image(config.board.image()));
        screen.setRect(match.getRect());
        screen.highlight(1);
        Position previousPos = positionRecogniser.apply(screen);
        logger.debug("initial position:\n{}", previousPos.render());
        Game game = new Game(previousPos, List.empty(), Color.WHITE);
        while (true) {
            final Position currentPos = positionRecogniser.apply(screen);
            if (previousPos.equals(currentPos)) {
                logger.debug("nothing is changed...", currentPos);
                Thread.sleep(2000);
                continue;
            }
            logger.debug("current position:\n{}", currentPos.render());
            final Move move = moveRecognizer.detect(previousPos, currentPos);
            logger.info("detected move {}", move);
            game = game.move(move);
            previousPos = currentPos;
        }
    }
}