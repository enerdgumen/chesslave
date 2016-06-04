package io.chesslave;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EnsureTest {

    @Test
    public void notNullSuccessTest() {
        final Object obj = new Object();
        assertEquals(obj, Ensure.notNull(obj, null));
    }

    @Test
    public void notNullFailTest() {
        String errorMessage = "you are a naughty programmer";
        try {
            Ensure.notNull(null, errorMessage);
            fail("Should throw a NPE when reference is null");
        } catch (NullPointerException npe) {
            assertEquals(errorMessage, npe.getMessage());
        }
    }

    @Test
    public void isTrueSuccessTest() {
        Ensure.isTrue(true, null);
    }

    @Test
    public void isTrueFailTest() {
        String errorMessage = "your conscience is dirty";
        try {
            Ensure.isTrue(false, errorMessage);
            fail("Should throw an exception when condition is false");
        } catch (IllegalArgumentException iae) {
            assertEquals(errorMessage, iae.getMessage());
        }
    }
}
