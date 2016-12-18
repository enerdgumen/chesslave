package io.chesslave.eyes

import io.chesslave.visual.Images
import org.junit.Test
import java.io.File

class BoardAnalyzerTest {

    @Test
    fun analyzeSet1() {
        val userImage = Images.read("/images/set1/initial-board-with-background.png")
        val conf = BoardAnalyzer().analyze(userImage)
        conf.save(File("target"))
    }

    @Test
    fun analyzeSet2() {
        val userImage = Images.read("/images/set2/initial-board.png")
        val conf = BoardAnalyzer().analyze(userImage)
        conf.save(File("target"))
    }
}
