package io.chesslave.model;

public enum Color {

    WHITE,
    BLACK;

    public Color opponent() {
        return this == WHITE ? BLACK : WHITE;
    }

    public Piece pawn() {
        return Piece.of(Piece.Type.PAWN, this);
    }

    public Piece bishop() {
        return Piece.of(Piece.Type.BISHOP, this);
    }

    public Piece knight() {
        return Piece.of(Piece.Type.KNIGHT, this);
    }

    public Piece rook() {
        return Piece.of(Piece.Type.ROOK, this);
    }

    public Piece queen() {
        return Piece.of(Piece.Type.QUEEN, this);
    }

    public Piece king() {
        return Piece.of(Piece.Type.KING, this);
    }
}
