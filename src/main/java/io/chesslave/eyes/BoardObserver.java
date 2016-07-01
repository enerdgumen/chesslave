package io.chesslave.eyes;

import io.chesslave.eyes.sikuli.SikuliScreen;
import io.chesslave.eyes.sikuli.SikuliVision;
import io.chesslave.model.*;
import io.chesslave.visual.Images;
import io.chesslave.visual.model.BoardImage;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class BoardObserver {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final BoardConfiguration config;
    private final Screen screen;
    private final Vision vision;
    private final PositionRecogniser positionRecogniser;
    private final MoveRecogniser moveRecogniser;

    public BoardObserver(BoardConfiguration config) {
        this.config = config;
        this.screen = new SikuliScreen();
        this.vision = new SikuliVision();
        this.positionRecogniser = new FullPositionRecogniser(vision, config);
        this.moveRecogniser = new MoveRecogniser();
    }

    public Observable<Game> start(Color color) throws Exception {
        // detecting initial position
        final Vision.Match match = findBoardInDesktop(config.board);
        final BoardImage initImage = new BoardImage(match.image(), match.region().getLocation());
        final Position initPosition = positionRecogniser.begin(initImage).get();
        final Game initGame = new Game(initPosition, List.empty(), color);
        logger.debug("initial position:\n{}", initPosition);
        // following game
        final Observable<Tuple2<BoardImage, BoardImage>> boards = captureBoards(match.region());
        return observeGame(initGame, boards);
    }

    private Vision.Match findBoardInDesktop(BoardImage board) {
        final BufferedImage desktop = screen.captureAll();
        final Vision.Match match = vision.recognise(desktop).bestMatch(board.image()).get();
        screen.highlight(match.region(), 1, TimeUnit.SECONDS);
        return match;
    }

    private Observable<Tuple2<BoardImage, BoardImage>> captureBoards(Rectangle region) {
        final Observable<BoardImage> boards = Observable.interval(1, TimeUnit.SECONDS)
                .map(count -> screen.capture(region))
                .map(capture -> new BoardImage(capture, region.getLocation()));
        return boards.zipWith(boards.skip(1), Tuple::of)
                .filter(it -> Images.areDifferent(it._1.image(), it._2.image()));
    }

    private Observable<Game> observeGame(Game initGame, Observable<Tuple2<BoardImage, BoardImage>> boards) {
        return boards.scan(initGame, (game, images) -> {
            final Position position = positionRecogniser.next(game.position(), images._1, images._2).get();
            logger.debug("current position:\n{}", Positions.toText(position));
            if (game.position().equals(position)) {
                logger.debug("nothing is changed");
                return game;
            }
            final Move move = moveRecogniser.detect(game.position(), position);
            logger.info("detected move {}", move);
            return game.move(move);
        });
    }
}