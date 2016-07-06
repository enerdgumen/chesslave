package io.chesslave.model;

public final class Pawns {

    private Pawns() {
    }

    public static int direction(Color color) {
        return color == Color.WHITE ? +1 : -1;
    }

    public static boolean inPromotion(Color color, Square square) {
        return color == Color.WHITE
                ? square.row == 7
                : square.row == 0;
    }
}
