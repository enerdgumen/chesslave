package io.chesslave.model;

import io.chesslave.Ensure;
import io.chesslave.model.Piece.Type;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.HashMap;
import javaslang.collection.List;
import javaslang.collection.Map;
import javaslang.collection.Set;
import javaslang.control.Option;
import java.util.function.Function;

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

    Position(Map<Square, Piece> position) {
        this.position = position;
    }

    public Option<Piece> at(Square square) {
        return position.get(square);
    }

    public Map<Square, Piece> get() {
        return position;
    }

    public Position put(Square square, Piece piece) {
        return new Position(position.put(square, piece));
    }

    public Position remove(Square square) {
        return new Position(position.remove(square));
    }

    public Position move(Square from, Square to) {
        final Piece piece = position.apply(from);
        return new Position(position.remove(from).put(to, piece));
    }

    public Set<Tuple2<Square, Piece>> toSet() {
        return position.toSet();
    }

    @Override
    public String toString() {
        return "Position{" + "position=" + position + '}';
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof Position == false) {
            return false;
        }
        final Position other = (Position) rhs;
        return this.position.equals(other.position);
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }
}
