package io.chesslave.eyes;

import io.chesslave.model.Color;
import io.chesslave.model.Square;
import io.chesslave.visual.model.BoardImage;
import javaslang.collection.HashSet;
import javaslang.collection.Iterator;
import javaslang.collection.Set;
import org.immutables.value.Value;
import java.awt.image.BufferedImage;

@Deprecated
public class Recognition {

    @Value.Immutable
    public interface SquareGlance {

        Square square();

        Color side();
    }

    public static Set<SquareGlance> filledSquares(BoardImage board) {
        return Square.all()
                .filter(square -> isSquareFilled(board.squareImage(square).image()))
                .map(square -> ImmutableSquareGlance.builder()
                        .square(square)
                        .side(guessPieceSide(board.squareImage(square).image()))
                        .build());
    }

    public static boolean isSquareFilled(BufferedImage square) {
        final java.awt.Color example = Colors.of(square.getRGB(square.getWidth() / 2, square.getHeight() / 2));
        return !Images.sample(square, 1, 16).forAll(it -> Colors.areSimilar(example, Colors.of(it)));
    }

    public static Color guessPieceSide(BufferedImage square) {
        final Iterator<Float> values = Images.sample(square, 1, 16).map(it -> Colors.brightness(Colors.of(it)));
        final Set<FloatSet> components = values.foldLeft(HashSet.<FloatSet>empty(), (sets, value) -> {
            final FloatSet set = sets.find(it -> it.contains(value))
                    .peek(it -> it.add(value))
                    .getOrElse(() -> new FloatSet(value));
            return sets.add(set);
        });
        final float brightness = components.maxBy(it -> it.weight).get().average;
        return brightness < .5 ? Color.BLACK : Color.WHITE;
    }

    private static class FloatSet {

        float sum, average;
        int weight;

        FloatSet(float hue) {
            sum = average = hue;
            weight = 1;
        }

        boolean contains(float value) {
            final double tolerance = 0.2;
            return Math.abs(average - value) < tolerance;
        }

        void add(float hue) {
            sum += hue;
            weight += 1;
            average = sum / weight;
        }

        @Override
        public String toString() {
            return "FloatSet{" +
                    "average=" + average +
                    ", weight=" + weight +
                    '}';
        }
    }
}
