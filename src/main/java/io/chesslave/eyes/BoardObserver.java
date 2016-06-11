package io.chesslave.eyes;

import io.chesslave.eyes.sikuli.SikuliScreen;
import io.chesslave.eyes.sikuli.SikuliVision;
import io.chesslave.model.*;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class BoardObserver {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void start(BoardConfiguration config) throws Exception {
        final Screen screen = new SikuliScreen();
        final Vision vision = new SikuliVision();
        final PositionRecogniser positionRecogniser = new FullPositionRecogniser(vision, config);
        final MoveRecogniser moveRecogniser = new MoveRecogniser();

        // detecting initial position
        final BufferedImage desktop = screen.captureAll();
        final Vision.Match match = vision.recognise(desktop).bestMatch(config.board.image()).get();
        screen.highlight(match.region(), 1, TimeUnit.SECONDS);
        final BoardImage initImage = new BoardImage(match.image());
        final Position initPosition = positionRecogniser.begin(initImage).get();
        final Game initGame = new Game(initPosition, List.empty(), Color.WHITE);
        logger.debug("initial position:\n{}", Positions.toText(initPosition));

        // following game
        final Observable<BufferedImage> captures = Observable.interval(1, TimeUnit.SECONDS)
                .map(count -> screen.capture(match.region()));
        final Observable<Tuple2<BoardImage, BoardImage>> boards = captures.zipWith(captures.skip(1), Tuple::of)
                .filter(it -> {
                    if (Images.areDifferent(it._1, it._2)) return true;
                    logger.debug("nothing is changed...");
                    return false;
                })
                .map(it -> it.map(BoardImage::new, BoardImage::new));
        final Observable<Game> moves = boards.scan(initGame, (game, images) -> {
            final Position position = positionRecogniser.next(game.position(), images._1, images._2).get();
            logger.debug("current position:\n{}", Positions.toText(position));
            final Move move = moveRecogniser.detect(game.position(), position);
            logger.info("detected move {}", move);
            return game.move(move);
        });
        moves.subscribe();

        // waiting forever
        while (true) {
        }
    }
}