package io.chesslave.eyes.sikuli;

import io.chesslave.eyes.Vision;
import javaslang.Lazy;
import javaslang.collection.Stream;
import javaslang.control.Option;
import org.sikuli.basics.Settings;
import org.sikuli.script.Finder;
import org.sikuli.script.Image;
import org.sikuli.script.Pattern;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SikuliVision implements Vision {

    private static class SikuliRecogniser implements Recogniser {

        private final BufferedImage source;

        public SikuliRecogniser(BufferedImage source) {
            this.source = source;
        }

        @Override
        public Option<Match> match(BufferedImage target) {
            final Finder matches = new Finder(source);
            matches.find(new Image(target));
            return Stream.ofAll(() -> matches).<Match>map(m -> new SikuliMatch(source, m)).headOption();
        }

        @Override
        public Stream<Match> matches(BufferedImage target) {
            final Finder matches = new Finder(source);
            matches.findAll(new Image(target));
            return Stream.ofAll(() -> matches).map(match -> new SikuliMatch(source, match));
        }
    }

    public static class SikuliMatch implements Match {
        private final BufferedImage source;
        private final Lazy<BufferedImage> image;
        private final org.sikuli.script.Match match;

        public SikuliMatch(BufferedImage source, org.sikuli.script.Match match) {
            this.source = source;
            this.match = match;
            this.image = Lazy.of(() -> {
                final Rectangle rect = match.getRect();
                return source.getSubimage(rect.x, rect.y, rect.width, rect.height);
            });
        }

        @Override
        public double similarity() {
            return match.getScore();
        }

        @Override
        public Rectangle region() {
            return match.getRect();
        }

        @Override
        public BufferedImage source() {
            return source;
        }

        @Override
        public BufferedImage image() {
            return image.get();
        }
    }

    @Override
    public Recogniser recognise(BufferedImage image) {
        return new SikuliRecogniser(image);
    }
}
