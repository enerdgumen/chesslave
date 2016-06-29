package io.chesslave.eyes;

import javaslang.collection.Stream;
import javaslang.control.Option;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public interface Vision {

    interface Recogniser {

        Option<Match> match(BufferedImage target);

        Stream<Match> matches(BufferedImage target);

        default Option<Match> bestMatch(BufferedImage target) {
            return matches(target)
                    .toList()
                    .sortBy(Match::similarity)
                    .lastOption();
        }
    }

    interface Match {

        double similarity();

        Rectangle region();

        BufferedImage source();

        BufferedImage image();
    }

    Recogniser recognise(BufferedImage image);
}
