package io.chesslave.model;

import io.chesslave.Ensure;
import javaslang.collection.List;
import javaslang.collection.Set;

public class Board {

    public static Board standard = new Board(8);
    public final int size;
    private final Set<Square> squares;

    public Board(int size) {
        Ensure.isTrue(size > 0, "cannot create a board with size %d", size);
        this.size = size;
        this.squares = List.range(0, size)
                .crossProduct(List.range(0, size))
                .map(t -> new Square(t._1, t._2))
                .toSet();
    }

    public Square square(int col, int row) {
        return new Square(col, row);
    }

    public Square square(String coordinates) {
        Ensure.isTrue(coordinates.length() == 2, "bad coordinate %s", coordinates);
        final String coo = coordinates.toLowerCase();
        return new Square(coo.charAt(0) - 'a', coo.charAt(1) - '1');
    }

    public Set<Square> allSquares() {
        return squares;
    }

    public class Square {

        public final int col;
        public final int row;

        private Square(int col, int row) {
            Ensure.isTrue(0 <= col && col < size, "illegal column %d", col);
            Ensure.isTrue(0 <= row && row < size, "illegal row %d", row);
            this.col = col;
            this.row = row;
        }

        public String name() {
            return new StringBuilder()
                    .append((char) ('a' + col))
                    .append((char) ('1' + row))
                    .toString();
        }

        @Override
        public String toString() {
            return name();
        }

        @Override
        public int hashCode() {
            return size * row + col;
        }

        @Override
        public boolean equals(Object rhs) {
            if (rhs instanceof Square == false) {
                return false;
            }
            final Square other = (Square) rhs;
            return this.col == other.col && this.row == other.row;
        }
    }
}
