package io.chesslave.model;

import io.chesslave.model.Board.Square;
import io.chesslave.Ensure;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;

public class Position {

    public static class Builder {

        private Map<Square, Piece> position = HashMap.empty();

        public Builder withPiece(Square square, Piece piece) {
            Ensure.isTrue(!position.containsKey(square), "cannot place %s into %s: square already used", piece, square);
            position = position.put(square, piece);
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

    public Option<Piece> at(Square square) {
        return position.get(square);
    }

    @Override
    public String toString() {
        return "Position{" + "position=" + position + '}';
    }
}
