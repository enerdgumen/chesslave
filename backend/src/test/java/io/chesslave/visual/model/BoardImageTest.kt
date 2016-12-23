package io.chesslave.visual.model

import io.chesslave.model.Board
import io.chesslave.model.Square
import io.chesslave.visual.Images
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test
import java.awt.Point
import java.awt.image.BufferedImage

class BoardImageTest {

    companion object {
        val DIR_IMAGES = "/images/"
        val DIR_RESOURCES = DIR_IMAGES + "visual/"
        val IMAGE_UNFLIPPED_BOARD = DIR_RESOURCES + "board.png"
        val IMAGE_FLIPPED_BOARD = DIR_RESOURCES + "flipped-board.png"
        val IMAGE_KNIGHT = DIR_RESOURCES + "knight-dark-square.png"
        val IMAGE_PAWN = DIR_RESOURCES + "pawn-light-square.png"
        val IMAGE_DARK_SQUARE = DIR_RESOURCES + "dark-square.png"
        val IMAGE_LIGHT_SQUARE = DIR_RESOURCES + "light-square.png"

        lateinit var unflippedBoard: BufferedImage
        lateinit var flippedBoard: BufferedImage
        lateinit var knight: BufferedImage
        lateinit var pawn: BufferedImage
        lateinit var darkSquare: BufferedImage
        lateinit var lightSquare: BufferedImage

        @BeforeClass
        @JvmStatic fun setUpBeforeClass() {
            unflippedBoard = Images.read(IMAGE_UNFLIPPED_BOARD)
            flippedBoard = Images.read(IMAGE_FLIPPED_BOARD)

            knight = Images.read(IMAGE_KNIGHT)
            pawn = Images.read(IMAGE_PAWN)
            darkSquare = Images.read(IMAGE_DARK_SQUARE)
            lightSquare = Images.read(IMAGE_LIGHT_SQUARE)
        }
    }

    @Test
    fun constructorImageTest() {
        val boardImage = BoardImage(unflippedBoard)
        assertEquals(Point(0, 0), boardImage.offset())
        assertFalse(boardImage.flipped())
        validateBoardImage(boardImage)
    }

    @Test
    fun constructorImageFlippedTest() {
        val flipped = true
        val boardImage = BoardImage(flippedBoard, flipped)
        assertEquals(Point(0, 0), boardImage.offset())
        assertTrue(boardImage.flipped())
        validateBoardImage(boardImage)
    }

    @Test
    fun constructorImageOffsetTest() {
        val offset = Point(1, 1)
        val boardImage = BoardImage(unflippedBoard, offset)
        assertEquals(offset, boardImage.offset())
        assertFalse(boardImage.flipped())
        validateBoardImage(boardImage)
    }

    @Test
    fun constructorImageOffsetFlippedTest() {
        val offset = Point(1, 1)
        val flipped = true
        val boardImage = BoardImage(flippedBoard, offset, flipped)
        assertEquals(offset, boardImage.offset())
        assertTrue(boardImage.flipped())
        validateBoardImage(boardImage)
    }

    private fun validateBoardImage(boardImage: BoardImage) {
        assertTrue(Images.areEquals(boardImage.image(), if (boardImage.flipped()) flippedBoard else unflippedBoard))
        assertTrue(Images.areEquals(boardImage.squareImage(Board.g1).image(), knight))
        assertTrue(Images.areEquals(boardImage.squareImage(Board.d7).image(), pawn))
        assertTrue(Images.areEquals(boardImage.squareImage(Board.e4).image(), lightSquare))
        assertTrue(Images.areEquals(boardImage.squareImage(Board.e5).image(), darkSquare))
    }
}
