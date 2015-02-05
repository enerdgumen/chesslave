package io.chesslave.model;

public class Castling {

    public static enum Side {

        SHORT,
        LONG
    }

    public final Color color;
    public final Side side;

    public Castling(Color color, Side side) {
        this.color = color;
        this.side = side;
    }
}
