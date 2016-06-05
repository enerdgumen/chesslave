package io.chesslave.model;

public enum Color {

    WHITE,
    BLACK;

    public Color opponent() {
        return this == WHITE ? BLACK : WHITE;
    }
}
