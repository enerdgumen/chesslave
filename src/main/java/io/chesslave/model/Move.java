package io.chesslave.model;

import java.util.Optional;

public class Move {

    public final Board.Square from;
    public final Board.Square to;
    public final Optional<Piece.Type> promotion;

    public Move(Board.Square from, Board.Square to, Optional<Piece.Type> promotion) {
        this.from = from;
        this.to = to;
        this.promotion = promotion;
    }
}
