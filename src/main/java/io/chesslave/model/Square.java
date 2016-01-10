package io.chesslave.model;

import io.chesslave.Ensure;
import io.chesslave.Functions;
import javaslang.Tuple2;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Set;
import javaslang.collection.Stream;
import javaslang.control.Option;
import java.util.function.Function;

public class Square {

    public final int col;
    public final int row;

    public Square(int col, int row) {
        Ensure.isTrue(0 <= col && col < Board.SIZE, "illegal column %d", col);
        Ensure.isTrue(0 <= row && row < Board.SIZE, "illegal row %d", row);
        this.col = col;
        this.row = row;
    }

    public static Square of(String coordinates) {
        Ensure.isTrue(coordinates.length() == 2, "bad coordinate %s", coordinates);
        final String coo = coordinates.toLowerCase();
        return new Square(coo.charAt(0) - 'a', coo.charAt(1) - '1');
    }

    public static Set<Square> all() {
        return List.range(0, Board.SIZE)
                .crossProduct(List.range(0, Board.SIZE))
                .map(Functions.of(Square::new).tupled())
                .toSet();
    }

    public String name() {
        return new StringBuilder()
                .append((char) ('a' + col))
                .append((char) ('1' + row))
                .toString();
    }

    public Option<Square> translate(int col, int row) {
        final int newCol = this.col + col;
        final int newRow = this.row + row;
        return (0 <= newCol && newCol < Board.SIZE) && (0 <= newRow && newRow < Board.SIZE)
                ? Option.some(new Square(newCol, newRow))
                : Option.none();
    }

    public Set<Square> translateAll(Tuple2<Integer, Integer>... translations) {
        return HashSet.of(translations).map(Functions.of(this::translate).tupled()).flatMap(Function.identity());
    }

    public Stream<Square> walk(int col, int row) {
        return Stream.gen(translate(col, row), previous -> previous.flatMap(sq -> sq.translate(col, row)))
                .takeWhile(Option::isDefined)
                .flatMap(Function.identity());
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public int hashCode() {
        return Board.SIZE * row + col;
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
