package io.chesslave.model;

public enum Color {

    WHITE("w"),
    BLACK("b");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public Color opponent() {
        return this == WHITE ? BLACK : WHITE;
    }
}
