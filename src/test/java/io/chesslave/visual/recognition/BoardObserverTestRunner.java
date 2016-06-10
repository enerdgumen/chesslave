package io.chesslave.visual.recognition;

import io.chesslave.visual.Images;
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