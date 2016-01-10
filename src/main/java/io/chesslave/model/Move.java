package io.chesslave.model;

import javaslang.control.Option;

public class Move {

    public final Square from;
    public final Square to;
    public final Option<Piece.Type> promotion;

    private Move(Square from, Square to, Option<Piece.Type> promotion) {
        this.from = from;
        this.to = to;
        this.promotion = promotion;
    }

    public static Move of(Square from, Square to) {
        return new Move(from, to, Option.none());
    }
}
