package io.chesslave.visual;

import javaslang.collection.Iterator;
import javaslang.collection.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

/**
 * Support methods for images.
 */
public abstract class Images {

    private static final Logger logger = LoggerFactory.getLogger(Images.class);

    /**
     * Reads the image from the classpath.
     */
    public static BufferedImage read(String path) {
        try {
            logger.debug("Reading image {}", path);
            return ImageIO.read(Images.class.getResource(path));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Writes the image of the given file as PNG.
     */
    public static void write(BufferedImage image, File file) {
        try {
            logger.debug("Writing image {}", file);
            ImageIO.write(image, "PNG", file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Removes the image border while the RGB pixel colors hold the given predicate.
     */
    public static BufferedImage crop(BufferedImage image, Predicate<Integer> predicate) {
        int top = 0;
        {
            boolean accept = true;
            while (accept) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    if (!predicate.test(image.getRGB(x, top))) {
                        accept = false;
                        break;
                    }
                }
                if (accept) {
                    ++top;
                }
            }
        }
        int left = 0;
        {
            boolean accept = true;
            while (accept) {
                for (int y = top; y < image.getHeight(); ++y) {
                    if (!predicate.test(image.getRGB(left, y))) {
                        accept = false;
                        break;
                    }
                }
                if (accept) {
                    ++left;
                }
            }
        }
        int right = image.getWidth() - 1;
        {
            boolean accept = true;
            while (accept) {
                for (int y = top; y < image.getHeight(); ++y) {
                    if (!predicate.test(image.getRGB(right, y))) {
                        accept = false;
                        break;
                    }
                }
                if (accept) {
                    --right;
                }
            }
        }
        int bottom = image.getHeight() - 1;
        {
            boolean accept = true;
            while (accept) {
                for (int x = left; x <= right; ++x) {
                    if (!predicate.test(image.getRGB(x, bottom))) {
                        accept = false;
                        break;
                    }
                }
                if (accept) {
                    --bottom;
                }
            }
        }
        final int width = right - left + 1;
        final int height = bottom - top + 1;
        return image.getSubimage(left, top, width, height);
    }

    /**
     * Fills the outer background with the specific RGB color.
     */
    public static BufferedImage fillOuterBackground(BufferedImage image, int newColor) {
        final int oldColor = image.getRGB(0, 0);
        // top -> bottom
        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                final int color = image.getRGB(x, y);
                if (color == oldColor) {
                    image.setRGB(x, y, newColor);
                } else if (color != newColor) {
                    break;
                }
            }
        }
        // bottom -> top
        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = image.getHeight() - 1; y >= 0; --y) {
                final int color = image.getRGB(x, y);
                if (color == oldColor) {
                    image.setRGB(x, y, newColor);
                } else if (color != newColor) {
                    break;
                }
            }
        }
        // left -> right
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                final int color = image.getRGB(x, y);
                if (color == oldColor) {
                    image.setRGB(x, y, newColor);
                } else if (color != newColor) {
                    break;
                }
            }
        }
        // right -> left
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = image.getWidth() - 1; x >= 0; --x) {
                final int color = image.getRGB(x, y);
                if (color == oldColor) {
                    image.setRGB(x, y, newColor);
                } else if (color != newColor) {
                    break;
                }
            }
        }
        return image;
    }

    /**
     * @param rowPoints    The number of pixel colors to read for each image row.
     * @param columnPoints The number of pixel colors to read for each image column.
     * @return The stream of all pixel colors read from the upper-left corner to the down-right corner.
     * For each row only {@code rowPoints} pixels are read at regular intervals at the center of the image.
     * As the same way, for each column only {@code columnPoints} pixels are read at regular intervals at
     * the center of the image.
     */
    public static Iterator<Color> sample(BufferedImage image, int rowPoints, int columnPoints) {
        final int widthStep = image.getWidth() / (rowPoints + 1);
        final int heightStep = image.getHeight() / (columnPoints + 1);
        return Stream.rangeClosed(1, rowPoints)
                .crossProduct(Stream.rangeClosed(1, columnPoints))
                .map(point -> new Color(image.getRGB(point._1 * widthStep, point._2 * heightStep)));
    }

    /**
     * @return True if the two images are identical.
     */
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
