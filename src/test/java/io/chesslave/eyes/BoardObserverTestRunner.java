package io.chesslave.eyes;

import io.chesslave.eyes.BoardAnalyzer;
import io.chesslave.eyes.BoardConfiguration;
import io.chesslave.eyes.BoardObserver;
import io.chesslave.eyes.Images;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class BoardObserverTestRunner {

    @Test
    public void example() throws Exception {
        // open http://www.chess.com/analysis-board-editor
        final BoardConfiguration config = new BoardAnalyzer().analyze(Images.read("/images/set1/initial-board.png"));
        new BoardObserver().start(config);
    }
}