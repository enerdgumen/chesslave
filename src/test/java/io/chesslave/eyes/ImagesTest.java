package io.chesslave.eyes;

import org.junit.Test;
import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertTrue;

public class ImagesTest {
    private static final String DIR_IMAGES = "/images/";
    private static final String DIR_CROP = DIR_IMAGES + "crop/";
    private static final String DIR_FILL_BACKGROUND = DIR_IMAGES + "fillOuterBackground/";

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
        Images.fillOuterBackground(image, Color.green.getRGB());
        final BufferedImage expected = Images.read(DIR_FILL_BACKGROUND + "queen-filled.png");
        assertTrue(Images.areEquals(expected, image));
    }
}