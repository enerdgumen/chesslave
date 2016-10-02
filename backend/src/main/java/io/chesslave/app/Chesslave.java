package io.chesslave.app;

import io.chesslave.eyes.BoardAnalyzer;
import io.chesslave.eyes.BoardConfiguration;
import io.chesslave.eyes.Screen;
import io.chesslave.eyes.sikuli.SikuliScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

public class Chesslave {

    private static final Logger logger = LoggerFactory.getLogger(Chesslave.class);

    public static void configure(EventBus events) {
        final Screen screen = new SikuliScreen();
        final Observable<BoardConfiguration> config = events
                .on("select-board")
                .flatMap(e -> screen.select("Select board...").map(new BoardAnalyzer()::analyze));
        config.subscribe(
                value -> events.fire(Event.of("board-selected")),
                ex -> events.fire(Event.of("board-selection-failed")));
    }
}
