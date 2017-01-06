package io.chesslave.visual

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.awt.Color

class ImagesTest {

    // TODO: move images into this package
    val DIR_BASE = "/images/visual/"
    val DIR_CROP = DIR_BASE + "crop/"
    val DIR_FILL_BACKGROUND = DIR_BASE + "fill/"
    val DIR_COPY = DIR_BASE + "copy/"

    @Test
    fun croppingSquareImage() {
        val input = Images.read(DIR_CROP + "board.png")
        val got = Images.crop(input) { it === input.getRGB(0, 0) }
        val expected = Images.read(DIR_CROP + "cropped-board.png")
        assertTrue(Images.areEquals(expected, got))
    }

    @Test
    fun croppingComplexImage() {
        val input = Images.read(DIR_CROP + "knight.png")
        val got = Images.crop(input) { it === input.getRGB(0, 0) }
        val expected = Images.read(DIR_CROP + "cropped-knight.png")
        assertTrue(Images.areEquals(expected, got))
    }

    @Test
    fun fillingOuterBackground1() {
        val image = Images.read(DIR_FILL_BACKGROUND + "queen.png")
        val got = Images.fillOuterBackground(image, Color.GREEN.rgb)
        val expected = Images.read(DIR_FILL_BACKGROUND + "queen-filled.png")
        assertTrue(Images.areEquals(expected, got))
    }

    @Test
    fun fillingOuterBackground2() {
        val image = Images.read(DIR_FILL_BACKGROUND + "pawn.png")
        val got = Images.fillOuterBackground(image, Color.BLUE.rgb)
        val expected = Images.read(DIR_FILL_BACKGROUND + "pawn-filled.png")
        assertTrue(Images.areEquals(expected, got))
    }

    @Test
    fun copyingSubImage() {
        val image = Images.read(DIR_COPY + "image.png")
        val got = Images.copy(image.getSubimage(100, 50, 100, 100))
        val expected = Images.read(DIR_COPY + "subimage.png")
        assertTrue(Images.areEquals(expected, got))
    }

    @Test
    fun areDifferent() {
        val baseImage = Images.read(DIR_BASE + "dark-square.png")
        assertFalse(Images.areDifferent(baseImage, baseImage))

        val differentImage = Images.read(DIR_BASE + "light-square.png")
        assertTrue(Images.areDifferent(baseImage, differentImage))

        val tighterImage = Images.read(DIR_BASE + "dark-square-tighter.png")
        assertTrue(Images.areDifferent(baseImage, tighterImage))

        val shorterImage = Images.read(DIR_BASE + "dark-square-shorter.png")
        assertTrue(Images.areDifferent(baseImage, shorterImage))
    }

    @Test
    fun areEquals() {
        val baseImage = Images.read(DIR_BASE + "dark-square.png")
        assertTrue(Images.areEquals(baseImage, baseImage))

        val tighterImage = Images.read(DIR_BASE + "dark-square-tighter.png")
        assertFalse(Images.areEquals(baseImage, tighterImage))

        val shorterImage = Images.read(DIR_BASE + "dark-square-shorter.png")
        assertFalse(Images.areEquals(baseImage, shorterImage))

        val differentImage = Images.read(DIR_BASE + "light-square.png")
        assertFalse(Images.areEquals(baseImage, differentImage))
    }
}