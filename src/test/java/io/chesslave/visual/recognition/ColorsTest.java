package io.chesslave.visual.recognition;

import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ColorsTest {

    @Test
    public void areSimilarTest() {
        assertTrue(Colors.areSimilar(Color.BLUE, Color.BLUE));
        assertTrue(Colors.areSimilar(Color.BLACK, Color.DARK_GRAY));
        assertFalse(Colors.areSimilar(Color.RED, Color.BLUE));
        assertFalse(Colors.areSimilar(Color.WHITE, Color.YELLOW));
    }
}
