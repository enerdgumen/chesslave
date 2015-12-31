package io.chesslave.recognition;

import org.junit.Test;

public class BoardObserverTestRunner {

    @Test
    public void sample() throws Exception {
        // open http://www.chess.com/analysis-board-editor
        final BoardConfiguration config = new BoardAnalyzer().analyze(Images.read("/images/set1/initial-board.png"));
        new BoardObserver().start(config);
    }
}