package io.chesslave.model;

import io.chesslave.model.Board.Square;
import io.chesslave.sugar.Ensure;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Position {

    public static class Builder {

        private final Map<Square, Piece> position = new HashMap<>();

        public Builder withPiece(Square square, Piece piece) {
            Ensure.isTrue(!position.containsKey(square), "cannot place %s into %s: square already used", piece, square);
            position.put(square, piece);
            return this;
        }

        public Position build() {
            return new Position(position);
        }
    }

    private final Map<Square, Piece> position;

    private Position(Map<Square, Piece> position) {
        this.position = position;
    }

    public Optional<Piece> at(Square square) {
        return Optional.ofNullable(position.get(square));
    }

    @Override
    public String toString() {
        return "Position{" + "position=" + position + '}';
    }
}
