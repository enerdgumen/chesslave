package io.chesslave.model;

public final class Pawns {

    private Pawns() {}

    public static int direction(Color color) {
        return color == Color.WHITE ? +1 : -1;
    }
}
