package io.chesslave.eyes

import io.chesslave.model.Color
import io.chesslave.visual.Images
import org.junit.Assume.assumeTrue
import org.junit.Ignore
import org.junit.Test
import java.awt.Desktop
import java.net.URI

@Ignore
class BoardObserverTestRunner {

    @Test
    fun example() {
        assumeTrue(Desktop.isDesktopSupported())
        Desktop.getDesktop().browse(URI("https://www.chess.com/analysis-board-editor"))
        // 5 seconds to open the browser
        Thread.sleep(5000)

        val config = BoardAnalyzer().analyze(Images.read("/images/set1/initial-board.png"))
        val moves = BoardObserver(config).start(Color.WHITE)
        moves.blockingLast() // waiting forever
    }
}