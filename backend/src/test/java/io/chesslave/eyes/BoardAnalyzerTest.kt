package io.chesslave.eyes

import io.chesslave.model.Game
import io.chesslave.visual.Images
import io.chesslave.visual.rendering.BoardRenderer
import io.chesslave.visual.rendering.ChessSet
import org.junit.Assert
import org.junit.Test
import java.awt.Point
import java.io.File

class analyzeBoardImageTest {

    @Test
    fun analyzeSet1() {
        val userImage = Images.read("/images/set1/initial-board-with-background.png")
        val conf = analyzeBoardImage(userImage)
        conf.save(File("target"))
    }

    @Test
    fun analyzeSet2() {
        val userImage = Images.read("/images/set2/initial-board.png")
        val conf = analyzeBoardImage(userImage)
        conf.save(File("target"))
    }
}

class detectSquareTest {

    @Test
    fun checkingIfThereIsSquareAtGivenPoint() {
        val chessSet = ChessSet.read("/images/set1/")
        val image = BoardRenderer(chessSet).withPosition(Game.initialPosition().position()).render().image
        val got = image.detectSquare(Point(image.width / 2 - 10, image.height / 2 - 10))!!
        val gotImage = image.getSubimage(got.x, got.y, got.width, got.height)
        Assert.assertNotNull(got)
    }
}

class detectBoardTest {

    @Test
    fun detectingBoardAtGivenPoint() {
        val image = Images.read("/io/chesslave/eyes/lichess_brown.png")
        val got = image.detectBoard(Point(image.width / 2 - 10, image.height / 2 - 10))!!
        val gotImage = image.getSubimage(got.x, got.y, got.width, got.height)
        Assert.assertNotNull(got)
    }
}