package io.chesslave.recognition;

import java.awt.*;

public class Colors {

    public static boolean areSimilar(Color a, Color b) {
        return Math.abs(hue(a) - hue(b)) < .1;
    }

    public static float hue(Color color) {
        final float hsb[] = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        return hsb[0];
    }
}