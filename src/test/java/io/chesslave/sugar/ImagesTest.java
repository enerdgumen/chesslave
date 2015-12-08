package io.chesslave.sugar;

import org.junit.Assert;
import org.junit.Test;
import java.awt.image.BufferedImage;

public class ImagesTest {

    @Test
    public void croppingSquareImage() {
        final BufferedImage input = Images.read("/images/crop/board.png");
        final BufferedImage got = Images.crop(input, color -> color == input.getRGB(0, 0));
        final BufferedImage expected = Images.read("/images/crop/cropped-board.png");
        Assert.assertEquals(true, Images.areEquals(expected, got));
    }

    @Test
    public void croppingComplexImage() {
        final BufferedImage input = Images.read("/images/crop/knight.png");
        final BufferedImage got = Images.crop(input, color -> color == input.getRGB(0, 0));
        final BufferedImage expected = Images.read("/images/crop/cropped-knight.png");
        Assert.assertEquals(true, Images.areEquals(expected, got));
    }
}