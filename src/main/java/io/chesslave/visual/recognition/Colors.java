package io.chesslave.visual.recognition;

import java.awt.Color;

public class Colors {
    private static final double DEFAULT_TOLERANCE = .02d;

    public static Color of(int rgb) {
        return new Color(rgb);
    }

    public static boolean areSimilar(Color a, Color b) {
        return Colors.areSimilar(a, b, DEFAULT_TOLERANCE);
    }

    public static boolean areSimilar(Color a, Color b, double tolerance) {
        return Math.abs(hue(a) - hue(b)) < tolerance;
    }

    public static float hue(Color color) {
        return Colors.getHSB(color)[0];
    }

    public static float brightness(Color color) {
        return Colors.getHSB(color)[2];
    }

    private static float[] getHSB(Color color) {
        final float hsb[] = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        return hsb;
    }
}