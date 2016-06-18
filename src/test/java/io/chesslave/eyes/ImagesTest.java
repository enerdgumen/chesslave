package io.chesslave.eyes;

import org.junit.Test;
import java.awt.Color;
import java.awt.image.BufferedImage;
import static org.junit.Assert.assertTrue;

public class ImagesTest {
    private static final String DIR_IMAGES = "/images/";
    private static final String DIR_CROP = DIR_IMAGES + "crop/";
    private static final String DIR_FILL_BACKGROUND = DIR_IMAGES + "fillOuterBackground/";
    private static final String DIR_COPY = DIR_IMAGES + "copy/";

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
    public void fillingOuterBackground() {
        final BufferedImage image = Images.read(DIR_FILL_BACKGROUND + "queen.png");
        final BufferedImage got = Images.fillOuterBackground(image, Color.green.getRGB());
        final BufferedImage expected = Images.read(DIR_FILL_BACKGROUND + "queen-filled.png");
        assertTrue(Images.areEquals(expected, got));
    }

    @Test
    public void copyingSubImage() {
        final BufferedImage image = Images.read(DIR_COPY + "image.png");
        final BufferedImage got = Images.copy(image.getSubimage(100, 50, 100, 100));
        final BufferedImage expected = Images.read(DIR_COPY + "subimage.png");
        assertTrue(Images.areEquals(expected, got));
    }
}