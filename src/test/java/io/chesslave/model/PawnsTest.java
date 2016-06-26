package io.chesslave.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PawnsTest {

    @Test
    public void directionTest() {
        assertEquals(1, Pawns.direction(Color.WHITE));
        assertEquals(-1, Pawns.direction(Color.BLACK));
    }
}
