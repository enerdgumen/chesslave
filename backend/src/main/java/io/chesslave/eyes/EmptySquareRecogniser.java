package io.chesslave.eyes;

import io.chesslave.visual.Images;
import io.chesslave.visual.model.SquareImage;
import java.awt.image.BufferedImage;

public class EmptySquareRecogniser {

    public boolean isEmpty(SquareImage square) {
        final BufferedImage image = square.image();
        final java.awt.Color example = Colors.of(image.getRGB(image.getWidth() / 2, image.getHeight() / 2));
        return Images.sample(image, 1, 16).forAll(it -> Colors.areSimilar(example, Colors.of(it)))
                && Images.sample(image, 16, 1).forAll(it -> Colors.areSimilar(example, Colors.of(it)));
    }
}
