package io.chesslave.hands

import io.chesslave.eyes.Vision
import io.chesslave.eyes.sikuli.SikuliScreen
import io.chesslave.eyes.sikuli.SikuliVision
import io.chesslave.hands.sikuli.SikuliPointer
import io.chesslave.model.Board
import io.chesslave.model.Square
import io.chesslave.visual.Images
import io.chesslave.visual.model.BoardImage
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assume.assumeThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.sikuli.script.Screen
import java.awt.Desktop
import java.awt.Point
import java.net.URI

/**
 * This test is ignored since it is based on unstable www.chess.com.
 * It doesn't work anymore, and should be revisited.
 */
@RunWith(Parameterized::class)
@Ignore
class MoversLiveTest(val subject: Mover, val resetButtonPoint: Point, val flipButtonPoint: Point?) {

    companion object {

        @Parameterized.Parameters
        @JvmStatic fun data(): Collection<Array<Any?>> {
            try {
                Desktop.getDesktop().browse(URI("https://www.chess.com/analysis-board-editor"))
                // 5 seconds to open the browser
                Thread.sleep(5000L)

                val screen = SikuliScreen()
                val vision = SikuliVision()
                val pointer = SikuliPointer(Screen.getPrimaryScreen())
                val recogniser: Vision.Recogniser = vision.recognise(screen.captureAll())

                val unflippedBoard = recogniser.bestMatch(Images.read("/images/visual/board.png"))
                    .map { BoardImage(it.image(), offset = it.region().location, flipped = false) }
                    .get()
                val resetPoint = recogniser.bestMatch(Images.read("/images/hands/chesscom-reset-button.png"))
                    .map { with(it.region()) { Point(centerX.toInt(), centerY.toInt()) } }
                    .get()

                // flip board
                val flipPoint = recogniser.bestMatch(Images.read("/images/hands/chesscom-flip-board-button.png"))
                    .map { with(it.region()) { Point(centerX.toInt(), centerY.toInt()) } }
                    .get()
                if (flipPoint != null) {
                    pointer.click(flipPoint)
                }
                // wait webapp's response
                Thread.sleep(500)
                // recapture screen after flip
                val flippedBoard = vision.recognise(screen.captureAll())
                    .bestMatch(Images.read("/images/visual/flipped-board.png"))
                    .map { BoardImage(it.image(), offset = it.region().location, flipped = true) }
                    .get()

                return listOf(
                    arrayOf(moveByClick(squarePoints(unflippedBoard)), resetPoint, flipPoint),
                    arrayOf(moveByDrag(squarePoints(unflippedBoard)), resetPoint, null),
                    arrayOf(moveByClick(squarePoints(flippedBoard)), resetPoint, flipPoint),
                    arrayOf(moveByDrag(squarePoints(flippedBoard)), resetPoint, null))
            } catch (ex: Exception) {
                return listOf()
            }
        }
    }

    @Before
    fun setUp() {
        assumeThat(subject, notNullValue())
        assumeThat(resetButtonPoint, notNullValue())

        val pointer = SikuliPointer(Screen.getPrimaryScreen())

        // flip board when necessary
        if (flipButtonPoint != null) {
            pointer.click(flipButtonPoint)
        }

        // reset board position after each sequence
        pointer.click(resetButtonPoint)
    }

    @Test
    fun spanishOpeningTest() {
        subject(Board.e2, Board.e4)
        subject(Board.e7, Board.e5)
        subject(Board.g1, Board.f3)
        subject(Board.b8, Board.c6)
        subject(Board.f1, Board.b5)
        subject(Board.g8, Board.f6)
        subject(Board.e1, Board.g1)
        subject(Board.f8, Board.e7)
        subject(Board.f1, Board.e1)
        subject(Board.a7, Board.a6)
        // awkward move, but it tests captures ;)
        subject(Board.b5, Board.c6)
    }
}

class Asd {
    @Test
    fun asd() {
        for (col in 0..7) {
            for (row in 0..7) {
                println("val ${'a'+col}${'1'+row} = Square($col, $row)")
            }
        }
    }
}