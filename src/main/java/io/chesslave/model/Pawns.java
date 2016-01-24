package io.chesslave.model;

public class Pawns {

    public static int direction(Color color) {
        return color == Color.WHITE ? +1 : -1;
    }
}
