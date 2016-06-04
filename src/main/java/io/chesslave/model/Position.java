package io.chesslave.model;

import io.chesslave.Ensure;
import javaslang.Tuple2;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.collection.Set;
import javaslang.control.Option;

/**
 * An immutable chess position.
 */
public class Position {

    public static class Builder {

        private Map<Square, Piece> position = HashMap.empty();

        /**
         * Puts the piece at the specified square.
         */
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

    /**
     * @return The piece placed at the given square or nothing if the square is empty.
     */
    public Option<Piece> at(Square square) {
        return position.get(square);
    }

    /**
     * @return A new position having the given piece placed to the given square.
     */
    public Position put(Square square, Piece piece) {
        return new Position(position.put(square, piece));
    }

    /**
     * @return A new position gets removing the piece at the given square.
     */
    public Position remove(Square square) {
        return new Position(position.remove(square));
    }

    /**
     * @return A new position gets moving the piece at the {@code from} square to the {@code to} square.
     */
    public Position move(Square from, Square to) {
        final Piece piece = position.apply(from);
        return new Position(position.remove(from).put(to, piece));
    }

    /**
     * @return A map having as keys the filled squares and as values the relative pieces.
     */
    public Map<Square, Piece> toMap() {
        return position;
    }

    /**
     * @return A set with all filled squares and the relative pieces.
     */
    public Set<Tuple2<Square, Piece>> toSet() {
        return position.toSet();
    }

    @Override
    public String toString() {
        return "Position{" + "position=" + position + '}';
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        }
        if (!(rhs instanceof Position)) {
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
