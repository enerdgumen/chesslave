package io.chesslave.sugar;

public abstract class Strings {

    public static String concat(String... strings) {
        final StringBuilder builder = new StringBuilder();
        for (String str : strings) {
            builder.append(str);
        }
        return builder.toString();
    }
}
