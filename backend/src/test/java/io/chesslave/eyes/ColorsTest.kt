package io.chesslave.eyes

import io.chesslave.eyes.Colors
import org.junit.Test

import java.awt.Color

import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

class ColorsTest {

    @Test
    fun areSimilarTest() {
        assertTrue(Colors.areSimilar(Color.BLUE, Color.BLUE))
        assertTrue(Colors.areSimilar(Color.BLACK, Color.DARK_GRAY))
        assertFalse(Colors.areSimilar(Color.RED, Color.BLUE))
        assertFalse(Colors.areSimilar(Color.WHITE, Color.YELLOW))
    }
}
