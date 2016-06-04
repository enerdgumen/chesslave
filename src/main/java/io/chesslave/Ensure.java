package io.chesslave;

import java.util.function.Predicate;

/**
 * Utilities to perform assertions.
 */
public abstract class Ensure {

    public static <T> T notNull(T t, String message, Object... args) {
        if (t == null) {
            throw new NullPointerException(String.format(message, args));
        }
        return t;
    }

    public static void isTrue(boolean cond, String message, Object... args) {
        if (!cond) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }
}
