package io.chesslave.visual.recognition;

import io.chesslave.model.*;
import io.chesslave.visual.BoardImage;
import io.chesslave.visual.Images;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.List;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Image;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class BoardObserver {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void start(BoardConfiguration config) throws Exception {
        final Screen screen = Screen.getScreen(1);
        final PositionRecogniser positionRecogniser = new FullPositionRecogniser(config);
        final MoveRecognizer moveRecognizer = new MoveRecognizer();

        // detecting initial position
        final BoardImage initImage = findBoardRegion(screen, config.board);
        final Position initPosition = positionRecogniser.begin(initImage).get();
        final Game initGame = new Game(initPosition, List.empty(), Color.WHITE);
        logger.debug("initial position:\n{}", Positions.toText(initPosition));

        // following game
        final Observable<BufferedImage> captures = Observable.interval(1, TimeUnit.SECONDS)
                .map(count -> screen.capture().getImage());
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
            final Move move = moveRecognizer.detect(game.position(), position);
            logger.info("detected move {}", move);
            return game.move(move);
        });
        moves.subscribe();

        // waiting forever
        while (true) {
        }
    }

    private BoardImage findBoardRegion(Screen screen, BoardImage board) throws FindFailed {
        final Match match = screen.find(new Image(board.image()));
        screen.setRect(match.getRect());
        screen.highlight(1);
        return new BoardImage(screen.capture().getImage());
    }
}