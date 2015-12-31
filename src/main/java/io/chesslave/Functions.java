package io.chesslave;

import javaslang.Function2;

public class Functions {

    public static <T1, T2, R> Function2<T1, T2, R> of(Function2<T1, T2, R> g) {
        return g;
    }
}
