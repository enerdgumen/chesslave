package io.chesslave.visual.recognition;

import javaslang.collection.List;
import java.awt.Color;

public class Colors {

    public static boolean areSimilar(Color a, Color b) {
        final double tolerance = .02;
        return Math.abs(hue(a) - hue(b)) < tolerance;
    }

    public static float hue(Color color) {
        final float hsb[] = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        return hsb[0];
    }

    public static float brightness(Color color) {
        final float hsb[] = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        return hsb[2];
    }
}