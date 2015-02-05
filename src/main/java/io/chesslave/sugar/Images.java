package io.chesslave.sugar;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Images {

    public static BufferedImage read(Class<?> clazz, String path) {
        try {
            return ImageIO.read(clazz.getResource(path));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
