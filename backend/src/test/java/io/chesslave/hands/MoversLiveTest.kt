package io.chesslave.hands

import io.chesslave.eyes.Vision
import io.chesslave.eyes.sikuli.SikuliScreen
import io.chesslave.eyes.sikuli.SikuliVision
import io.chesslave.hands.sikuli.SikuliPointer
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
        subject(Square.of("e2"), Square.of("e4"))
        subject(Square.of("e7"), Square.of("e5"))
        subject(Square.of("g1"), Square.of("f3"))
        subject(Square.of("b8"), Square.of("c6"))
        subject(Square.of("f1"), Square.of("b5"))
        subject(Square.of("g8"), Square.of("f6"))
        subject(Square.of("e1"), Square.of("g1"))
        subject(Square.of("f8"), Square.of("e7"))
        subject(Square.of("f1"), Square.of("e1"))
        subject(Square.of("a7"), Square.of("a6"))
        // awkward move, but it tests captures ;)
        subject(Square.of("b5"), Square.of("c6"))
    }
}
