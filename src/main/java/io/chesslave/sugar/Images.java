package io.chesslave.sugar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

public abstract class Images {

    public static BufferedImage read(Class<?> clazz, String path) {
        try {
            return ImageIO.read(clazz.getResource(path));
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

    public static BufferedImage cropWhile(BufferedImage image, Predicate<Integer> predicate) {
        final int middleY = image.getHeight() / 2;
        final int middleX = image.getWidth() / 2;

        int left = 0;
        while (predicate.test(image.getRGB(left, middleY))) {
            left++;
        }

        int right = image.getWidth() - 1;
        while (predicate.test(image.getRGB(right, middleY))) {
            right--;
        }

        int top = 0;
        while (predicate.test(image.getRGB(middleX, top))) {
            top++;
        }

        int bottom = image.getHeight() - 1;
        while (predicate.test(image.getRGB(middleX, bottom))) {
            bottom--;
        }

        final int width = right - left + 1;
        final int height = bottom - top + 1;
        return image.getSubimage(left, top, width, height);
    }
}
