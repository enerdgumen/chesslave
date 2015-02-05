package io.chesslave.sugar;

public abstract class Ensure {

    public static <T> T notNull(T t, String message, Object... args) {
        if (t == null) {
            throw new NullPointerException(String.format(message, args));
        }
        return t;
    }

    public static void isTrue(boolean cond, String message, Object... args) {
        if (cond == false) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }
}
