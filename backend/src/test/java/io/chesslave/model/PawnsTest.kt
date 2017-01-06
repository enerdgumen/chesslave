package io.chesslave.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PawnsTest {

    @Test
    fun directionTest() {
        assertEquals(1, Pawns.direction(Color.WHITE).toLong())
        assertEquals(-1, Pawns.direction(Color.BLACK).toLong())
    }
}
