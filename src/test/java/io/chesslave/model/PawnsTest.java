package io.chesslave.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PawnsTest {

    @Test
    public void directionTest() {
        assertEquals(1, Pawns.direction(Color.WHITE));
        assertEquals(-1, Pawns.direction(Color.BLACK));
    }
}
