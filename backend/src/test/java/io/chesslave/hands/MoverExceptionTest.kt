package io.chesslave.hands

import org.junit.Assert.assertEquals
import org.junit.Test

class MoverExceptionTest {

    @Test
    fun testCauseConstructor() {
        val throwable = Exception()
        val moverException = MoverException(throwable)
        assertEquals(throwable, moverException.cause)
    }

    @Test
    fun testCauseAndMessageConstructor() {
        val throwable = Exception()
        val moverException = MoverException("useless message", throwable)
        assertEquals(throwable, moverException.cause)
        assertEquals("useless message", moverException.message)
    }
}
