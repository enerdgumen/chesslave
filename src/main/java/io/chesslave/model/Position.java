package io.chesslave.model;

import io.chesslave.Ensure;
import io.chesslave.model.Piece.Type;
import javaslang.Tuple;
import javaslang.collection.HashMap;
import javaslang.collection.List;
import javaslang.collection.Map;
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

    public static Position of(
            String row8,
            String row7,
            String row6,
            String row5,
            String row4,
            String row3,
            String row2,
            String row1) {
        final Map<String, Piece> pieceFromCode = HashMap.ofAll(
                Tuple.of("P", Piece.of(Type.PAWN, Color.WHITE)),
                Tuple.of("N", Piece.of(Type.KNIGHT, Color.WHITE)),
                Tuple.of("B", Piece.of(Type.BISHOP, Color.WHITE)),
                Tuple.of("R", Piece.of(Type.ROOK, Color.WHITE)),
                Tuple.of("Q", Piece.of(Type.QUEEN, Color.WHITE)),
                Tuple.of("K", Piece.of(Type.KING, Color.WHITE)),
                Tuple.of("p", Piece.of(Type.PAWN, Color.BLACK)),
                Tuple.of("n", Piece.of(Type.KNIGHT, Color.BLACK)),
                Tuple.of("b", Piece.of(Type.BISHOP, Color.BLACK)),
                Tuple.of("r", Piece.of(Type.ROOK, Color.BLACK)),
                Tuple.of("q", Piece.of(Type.QUEEN, Color.BLACK)),
                Tuple.of("k", Piece.of(Type.KING, Color.BLACK)));
        final Map<Square, Piece> position = List.of(row1, row2, row3, row4, row5, row6, row7, row8)
                .zipWithIndex()
                .flatMap(row -> List.of(row._1.split("\\|"))
                        .map(pieceFromCode::get)
                        .zipWithIndex()
                        .flatMap(col -> col._1.map(piece -> Tuple.of(new Square(col._2, row._2), piece))))
                .toMap(Function.identity());
        return new Position(position);
    }

    private final Map<Square, Piece> position;

    private Position(Map<Square, Piece> position) {
        this.position = position;
    }

    public Option<Piece> at(Square square) {
        return position.get(square);
    }

    public Map<Square, Piece> get() {
        return position;
    }

    public Position move(Move move) {
        final Piece movedPiece = position.apply(move.from);
        return new Position(position.remove(move.from).put(move.to, movedPiece));
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
