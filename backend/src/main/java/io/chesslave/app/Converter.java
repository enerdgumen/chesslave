package io.chesslave.app;

import java.io.IOException;

public interface Converter<T> {

    String asString(T value) throws IOException;

    T fromString(String text) throws IOException;
}
