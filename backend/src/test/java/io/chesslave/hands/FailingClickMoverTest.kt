package io.chesslave.hands

import io.chesslave.model.Square
import io.chesslave.visual.model.BoardImage
import org.junit.Test
import java.awt.image.BufferedImage

class FailingClickMoverTest {

    @Test(expected = MoverException::class)
    fun moveTest() {
        val points = SquarePoints(BoardImage(BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB)))
        val subject = ClickMover(FailingPointer(), points)
        subject.move(Square.of("a1"), Square.of("b3"))
    }
}
