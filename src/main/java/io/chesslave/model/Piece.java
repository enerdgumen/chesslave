package io.chesslave.model;

public class Piece {

    public static enum Type {

        PAWN,
        KNIGHT,
        BISHOP,
        ROOK,
        QUEEN,
        KING
    }

    public final Type type;
    public final Color color;

    public Piece(Type type, Color color) {
        this.type = type;
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("%s %s", type, color);
    }
}
