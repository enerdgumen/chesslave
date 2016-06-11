package io.chesslave.eyes;

import javaslang.collection.Stream;
import javaslang.control.Option;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public interface Vision {

    interface Recognizer {

        Stream<Match> matches(BufferedImage target);

        default Option<Match> bestMatch(BufferedImage target) {
            final double bestSimilarity = 0.75;
            return matches(target)
                    .takeUntil(it -> it.similarity() < bestSimilarity)
                    .toList()
                    .sortBy(it -> it.similarity())
                    .lastOption();
        }
    }

    interface Match {

        double similarity();

        Rectangle region();

        BufferedImage source();

        BufferedImage image();
    }

    Recognizer recognise(BufferedImage image);
}
