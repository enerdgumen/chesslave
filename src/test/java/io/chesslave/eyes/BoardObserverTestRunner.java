package io.chesslave.eyes;

import io.chesslave.model.Game;
import org.junit.Ignore;
import org.junit.Test;
import rx.Observable;
import java.awt.Desktop;
import java.net.URI;
import static org.junit.Assume.assumeTrue;

@Ignore
public class BoardObserverTestRunner {
    private static final String DIR_IMAGES = "/images/";
    private static final String DIR_PIECE_SET = DIR_IMAGES + "set1/";
    private static final String IMAGE_INITIAL_BOARD = DIR_PIECE_SET + "initial-board.png";
    private static final String CHESS_BOARD_WEB_PAGE = "https://www.chess.com/analysis-board-editor";

    @Test
    public void example() throws Exception {
        assumeTrue(Desktop.isDesktopSupported());
        Desktop.getDesktop().browse(new URI(CHESS_BOARD_WEB_PAGE));
        // 5 seconds to open the browser
        Thread.sleep(5000);

        final BoardConfiguration config = new BoardAnalyzer().analyze(Images.read(IMAGE_INITIAL_BOARD));
        final Observable<Game> moves = new BoardObserver(config).start();
        moves.toBlocking().last(); // waiting forever
    }
}