package io.chesslave.eyes;

import io.chesslave.eyes.sikuli.SikuliScreen;
import io.chesslave.eyes.sikuli.SikuliVision;
import io.chesslave.model.Color;
import io.chesslave.model.Game;
import io.chesslave.model.Move;
import io.chesslave.visual.BoardImage;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.control.Option;
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
    private final GameRecogniser recogniser;

    public BoardObserver(BoardConfiguration config) {
        this.config = config;
        this.screen = new SikuliScreen();
        this.vision = new SikuliVision();
        this.recogniser = new GameRecogniser(
                new PositionRecogniser(vision, config),
                new MoveRecogniserByImageDiff(new PieceRecogniser(vision, config)),
                new MoveRecogniserByPositionDiff());
    }

    public Observable<Game> start(Color color) throws Exception {
        // detecting initial position
        final Vision.Match match = findBoardInDesktop(config.board);
        final BoardImage initImage = new BoardImage(match.image(), match.region().getLocation());
        final Game initGame = recogniser.begin(initImage, color);
        logger.debug("initial position:\n{}", initGame.position());
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
            // TODO: restore from eventual exception
            final Option<Move> move = recogniser.next(game, images._1, images._2);
            if (move.isEmpty()) {
                logger.debug("nothing is changed");
                return game;
            }
            // TODO: validate move
            logger.debug("detective move:\n{}", move);
            return game.move(move.get());
        });
    }
}