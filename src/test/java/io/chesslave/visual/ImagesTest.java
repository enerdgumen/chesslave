package io.chesslave.visual;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImagesTest {
    private static final String DIR_BASE = "/images/visual/";
    private static final String DIR_CROP = DIR_BASE + "crop/";
    private static final String DIR_FILL_BACKGROUND = DIR_BASE + "fill/";
    private static final String DIR_COPY = DIR_BASE + "copy/";

    @Test
    public void croppingSquareImage() {
        final BufferedImage input = Images.read(DIR_CROP + "board.png");
        final BufferedImage got = Images.crop(input, color -> color == input.getRGB(0, 0));
        final BufferedImage expected = Images.read(DIR_CROP + "cropped-board.png");
        assertTrue(Images.areEquals(expected, got));
    }

    @Test
    public void croppingComplexImage() {
        final BufferedImage input = Images.read(DIR_CROP + "knight.png");
        final BufferedImage got = Images.crop(input, color -> color == input.getRGB(0, 0));
        final BufferedImage expected = Images.read(DIR_CROP + "cropped-knight.png");
        assertTrue(Images.areEquals(expected, got));
    }

    @Test
    public void fillingOuterBackground1() {
        final BufferedImage image = Images.read(DIR_FILL_BACKGROUND + "queen.png");
        final BufferedImage got = Images.fillOuterBackground(image, Color.GREEN.getRGB());
        final BufferedImage expected = Images.read(DIR_FILL_BACKGROUND + "queen-filled.png");
        assertTrue(Images.areEquals(expected, got));
    }

    @Test
    public void fillingOuterBackground2() {
        final BufferedImage image = Images.read(DIR_FILL_BACKGROUND + "pawn.png");
        final BufferedImage got = Images.fillOuterBackground(image, Color.BLUE.getRGB());
        final BufferedImage expected = Images.read(DIR_FILL_BACKGROUND + "pawn-filled.png");
        assertTrue(Images.areEquals(expected, got));
    }

    @Test
    public void copyingSubImage() {
        final BufferedImage image = Images.read(DIR_COPY + "image.png");
        final BufferedImage got = Images.copy(image.getSubimage(100, 50, 100, 100));
        final BufferedImage expected = Images.read(DIR_COPY + "subimage.png");
        assertTrue(Images.areEquals(expected, got));
    }

    @Test
    public void areDifferent() {
        final BufferedImage baseImage = Images.read(DIR_BASE + "dark-square.png");
        assertFalse(Images.areDifferent(baseImage, baseImage));

        final BufferedImage differentImage = Images.read(DIR_BASE + "light-square.png");
        assertTrue(Images.areDifferent(baseImage, differentImage));

        final BufferedImage tighterImage = Images.read(DIR_BASE + "dark-square-tighter.png");
        assertTrue(Images.areDifferent(baseImage, tighterImage));

        final BufferedImage shorterImage = Images.read(DIR_BASE + "dark-square-shorter.png");
        assertTrue(Images.areDifferent(baseImage, shorterImage));
    }

    @Test
    public void areEquals() {
        final BufferedImage baseImage = Images.read(DIR_BASE + "dark-square.png");
        assertTrue(Images.areEquals(baseImage, baseImage));

        final BufferedImage tighterImage = Images.read(DIR_BASE + "dark-square-tighter.png");
        assertFalse(Images.areEquals(baseImage, tighterImage));

        final BufferedImage shorterImage = Images.read(DIR_BASE + "dark-square-shorter.png");
        assertFalse(Images.areEquals(baseImage, shorterImage));

        final BufferedImage differentImage = Images.read(DIR_BASE + "light-square.png");
        assertFalse(Images.areEquals(baseImage, differentImage));
    }
}