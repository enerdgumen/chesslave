package io.chesslave.app;

import io.chesslave.eyes.BoardAnalyzer;
import io.chesslave.eyes.BoardConfiguration;
import org.sikuli.script.Screen;
import org.sikuli.util.OverlayCapturePrompt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

public class Chesslave {

    private static final Logger logger = LoggerFactory.getLogger(Chesslave.class);

    public static void configure(EventBus events) {
        final Observable<BoardConfiguration> boardConf = events
                .on("select-board")
                .flatMap(e -> Observable.<BoardConfiguration>create(result -> {
                    final Screen screen = Screen.all();
                    final OverlayCapturePrompt overlay = new OverlayCapturePrompt(screen, subject -> {
                        final OverlayCapturePrompt me = (OverlayCapturePrompt) subject;
                        final BoardAnalyzer analyzer = new BoardAnalyzer();
                        final BoardConfiguration conf = analyzer.analyze(me.getSelection().getImage());
                        result.onNext(conf);
                    });
                    overlay.prompt("Select board...");
                }));
        boardConf.subscribe();
    }
}
