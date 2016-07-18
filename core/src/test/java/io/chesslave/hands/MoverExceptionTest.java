package io.chesslave.hands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MoverExceptionTest {
    private Throwable throwable;

    @Before
    public void setUp() {
        throwable = new Exception("boooom!");
    }

    @Test
    public void testCauseConstructor() {
        final MoverException moverException = new MoverException(throwable);
        assertEquals(throwable, moverException.getCause());
    }

    @Test
    public void testCauseAndMessageConstructor() {
        final MoverException moverException = new MoverException("useless message", throwable);
        assertEquals(throwable, moverException.getCause());
        assertEquals("useless message", moverException.getMessage());
    }
}
