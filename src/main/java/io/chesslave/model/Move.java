package io.chesslave.model;

import javaslang.control.Option;

public class Move {

    public final Board.Square from;
    public final Board.Square to;
    public final Option<Piece.Type> promotion;

    public Move(Board.Square from, Board.Square to, Option<Piece.Type> promotion) {
        this.from = from;
        this.to = to;
        this.promotion = promotion;
    }
}
