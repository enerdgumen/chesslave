package io.chesslave;

import javaslang.Function2;
import javaslang.Function3;

public class Functions {

    public static <T1, T2, R> Function2<T1, T2, R> of(Function2<T1, T2, R> g) {
        return g;
    }

    public static <T1, T2, T3, R> Function3<T1, T2, T3, R> of(Function3<T1, T2, T3, R> g) {
        return g;
    }
}
