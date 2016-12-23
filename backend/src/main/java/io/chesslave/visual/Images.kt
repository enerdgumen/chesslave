package io.chesslave.visual

import javaslang.collection.Iterator
import javaslang.collection.Stream
import org.slf4j.LoggerFactory
import java.awt.image.BufferedImage
import java.io.File
import java.util.function.Predicate
import javax.imageio.ImageIO

/**
 * Support methods for images.
 */
object Images {

    private val logger = LoggerFactory.getLogger(Images::class.java)

    /**
     * Reads the image from the classpath.
     */
    fun read(path: String): BufferedImage {
        logger.trace("Reading image {}", path)
        return ImageIO.read(Images::class.java.getResource(path))
    }

    /**
     * Writes the image of the given file as PNG.
     */
   fun write(image: BufferedImage, file: File) {
        logger.debug("Writing image {}", file)
        ImageIO.write(image, "PNG", file)
    }

    /**
     * Clones the given image.
     */
    fun copy(image: BufferedImage): BufferedImage {
        val copy = BufferedImage(image.width, image.height, image.type)
        val g = copy.graphics
        g.drawImage(image, 0, 0, null)
        g.dispose()
        return copy
    }

    /**
     * Removes the image border while the RGB pixel colors hold the given predicate.
     */
    fun crop(image: BufferedImage, predicate: (Int) -> Boolean): BufferedImage {
        var top = 0
        run {
            var accept = true
            while (accept) {
                for (x in 0..image.width - 1) {
                    if (!predicate(image.getRGB(x, top))) {
                        accept = false
                        break
                    }
                }
                if (accept) {
                    ++top
                }
            }
        }
        var left = 0
        run {
            var accept = true
            while (accept) {
                for (y in top..image.height - 1) {
                    if (!predicate(image.getRGB(left, y))) {
                        accept = false
                        break
                    }
                }
                if (accept) {
                    ++left
                }
            }
        }
        var right = image.width - 1
        run {
            var accept = true
            while (accept) {
                for (y in top..image.height - 1) {
                    if (!predicate(image.getRGB(right, y))) {
                        accept = false
                        break
                    }
                }
                if (accept) {
                    --right
                }
            }
        }
        var bottom = image.height - 1
        run {
            var accept = true
            while (accept) {
                for (x in left..right) {
                    if (!predicate(image.getRGB(x, bottom))) {
                        accept = false
                        break
                    }
                }
                if (accept) {
                    --bottom
                }
            }
        }
        val width = right - left + 1
        val height = bottom - top + 1
        return image.getSubimage(left, top, width, height)
    }

    /**
     * Fills the outer background with the specific RGB color.
     */
    fun fillOuterBackground(source: BufferedImage, newColor: Int): BufferedImage {
        val image = Images.copy(source)
        val oldColor = image.getRGB(0, 0)
        // top -> bottom
        for (x in 0..image.width - 1) {
            for (y in 0..image.height - 1) {
                val color = image.getRGB(x, y)
                if (color == oldColor) {
                    image.setRGB(x, y, newColor)
                } else if (color != newColor) {
                    break
                }
            }
        }
        // bottom -> top
        for (x in 0..image.width - 1) {
            for (y in image.height - 1 downTo 0) {
                val color = image.getRGB(x, y)
                if (color == oldColor) {
                    image.setRGB(x, y, newColor)
                } else if (color != newColor) {
                    break
                }
            }
        }
        // left -> right
        for (y in 0..image.height - 1) {
            for (x in 0..image.width - 1) {
                val color = image.getRGB(x, y)
                if (color == oldColor) {
                    image.setRGB(x, y, newColor)
                } else if (color != newColor) {
                    break
                }
            }
        }
        // right -> left
        for (y in 0..image.height - 1) {
            for (x in image.width - 1 downTo 0) {
                val color = image.getRGB(x, y)
                if (color == oldColor) {
                    image.setRGB(x, y, newColor)
                } else if (color != newColor) {
                    break
                }
            }
        }
        return image
    }

    /**
     * @param rowPoints    The number of pixel colors to read for each image row.
     * *
     * @param columnPoints The number of pixel colors to read for each image column.
     * *
     * @return The stream of all pixel colors read from the upper-left corner to the down-right corner.
     * * For each row only `rowPoints` pixels are read at regular intervals at the center of the image.
     * * As the same way, for each column only `columnPoints` pixels are read at regular intervals at
     * * the center of the image.
     */
    fun sample(image: BufferedImage, rowPoints: Int, columnPoints: Int): Iterator<Int> {
        val widthStep = image.width / (rowPoints + 1)
        val heightStep = image.height / (columnPoints + 1)
        return Stream.rangeClosed(1, rowPoints)
            .crossProduct(Stream.rangeClosed(1, columnPoints))
            .map { image.getRGB(it._1 * widthStep, it._2 * heightStep) }
    }

    /**
     * @return True if the two images are *surely* different, false otherwise.
     */
    fun areDifferent(fst: BufferedImage, snd: BufferedImage): Boolean =
        fst.width != snd.width ||
            fst.height != snd.height ||
            Images.sample(fst, 10, 10).sum().toInt() != Images.sample(snd, 10, 10).sum().toInt()

    /**
     * @return True if the two images are identical.
     */
    fun areEquals(fst: BufferedImage, snd: BufferedImage): Boolean {
        if (fst.width != snd.width || fst.height != snd.height) {
            return false
        }
        for (x in 0..fst.width - 1) {
            for (y in 0..fst.height - 1) {
                if (fst.getRGB(x, y) != snd.getRGB(x, y)) {
                    return false
                }
            }
        }
        return true
    }
}
