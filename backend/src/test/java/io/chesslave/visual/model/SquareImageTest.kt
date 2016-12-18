package io.chesslave.visual.model

import io.chesslave.model.Board
import io.chesslave.model.Square
import io.chesslave.visual.Images
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.awt.image.BufferedImage

// TODO: these tests are too much logic, simplify!
@RunWith(Parameterized::class)
class SquareImageTest(val board: BufferedImage, val flipped: Boolean) {

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun data(): Collection<Array<Any>> =
            listOf(
                arrayOf(Images.read("/images/visual/board.png"), false),
                arrayOf(Images.read("/images/visual/flipped-board.png"), true))
    }

    val square = Square.of("g1")
    val squareImage = SquareImage(board, square, flipped)

    @Test
    fun imageTest() {
        val knight = Images.read("/images/visual/knight-dark-square.png")
        assertTrue(Images.areEquals(knight, squareImage.image()))
    }

    @Test
    fun sizeTest() {
        assertEquals((board.width / Board.SIZE).toLong(), squareImage.size().toLong())
    }

    @Test
    fun leftTest() {
        assertEquals((squareImage.size() * horizontalOffset(square.col)).toLong(), squareImage.left().toLong())
    }

    @Test
    fun rightTest() {
        assertEquals((squareImage.size() * (horizontalOffset(square.col) + 1)).toLong(), squareImage.right().toLong())
    }

    @Test
    fun topTest() {
        assertEquals((squareImage.size() * verticalOffset(square.row)).toLong(), squareImage.top().toLong())
    }

    @Test
    fun bottomTest() {
        assertEquals((squareImage.size() * (verticalOffset(square.row) + 1)).toLong(), squareImage.bottom().toLong())
    }

    @Test
    fun middleXTest() {
        assertEquals((squareImage.size() / 2 + squareImage.size() * horizontalOffset(square.col)).toLong(), squareImage.middleX().toLong())
    }

    @Test
    fun middleYTest() {
        assertEquals((squareImage.size() / 2 + squareImage.size() * verticalOffset(square.row)).toLong(), squareImage.middleY().toLong())
    }

    private fun horizontalOffset(col: Int): Int =
        if (flipped) Board.SIZE - 1 - col else col

    private fun verticalOffset(row: Int): Int =
        if (flipped) row else Board.SIZE - 1 - row
}
