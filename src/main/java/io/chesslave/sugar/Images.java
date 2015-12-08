package io.chesslave.sugar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

public abstract class Images {

    public static BufferedImage read(String path) {
        try {
            return ImageIO.read(Images.class.getResource(path));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void write(BufferedImage image, File file) {
        try {
            ImageIO.write(image, "PNG", file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static BufferedImage crop(BufferedImage image, Predicate<Integer> predicate) {
        boolean acceptLine;

        int top = 0;
        acceptLine = true;
        while (acceptLine) {
            for (int x = 0; x < image.getWidth(); ++x) {
                if (!predicate.test(image.getRGB(x, top))) {
                    acceptLine = false;
                    break;
                }
            }
            if (acceptLine) {
                ++top;
            }
        }

        int left = 0;
        acceptLine = true;
        while (acceptLine) {
            for (int y = top; y < image.getHeight(); ++y) {
                if (!predicate.test(image.getRGB(left, y))) {
                    acceptLine = false;
                    break;
                }
            }
            if (acceptLine) {
                ++left;
            }
        }

        int right = image.getWidth() - 1;
        acceptLine = true;
        while (acceptLine) {
            for (int y = top; y < image.getHeight(); ++y) {
                if (!predicate.test(image.getRGB(right, y))) {
                    acceptLine = false;
                    break;
                }
            }
            if (acceptLine) {
                --right;
            }
        }

        int bottom = image.getHeight() - 1;
        acceptLine = true;
        while (acceptLine) {
            for (int x = left; x <= right; ++x) {
                if (!predicate.test(image.getRGB(x, bottom))) {
                    acceptLine = false;
                    break;
                }
            }
            if (acceptLine) {
                --bottom;
            }
        }

        final int width = right - left + 1;
        final int height = bottom - top + 1;
        return image.getSubimage(left, top, width, height);
    }

    public static boolean areEquals(BufferedImage fst, BufferedImage snd) {
        if (fst.getWidth() != snd.getWidth() || fst.getHeight() != snd.getHeight()) {
            return false;
        }
        for (int x = 0; x < fst.getWidth(); ++x) {
            for (int y = 0; y < fst.getHeight(); ++y) {
                if (fst.getRGB(x, y) != snd.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
}
