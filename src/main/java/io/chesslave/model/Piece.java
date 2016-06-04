package io.chesslave.model;

import io.chesslave.Functions;
import javaslang.collection.List;
import javaslang.collection.Set;
import java.util.Objects;

/**
 * A chess piece.
 */
public class Piece {

    public enum Type {

        PAWN("p", 1),
        KNIGHT("n", 3),
        BISHOP("b", 3),
        ROOK("r", 5),
        QUEEN("q", 9),
        KING("k", 0);

        private final String code;
        private final int value;

        Type(String code, int value) {
            this.code = code;
            this.value = value;
        }

        public String code() {
            return code;
        }

        public int value() {
            return value;
        }
    }

    public final Type type;
    public final Color color;

    private Piece(Type type, Color color) {
        this.type = type;
        this.color = color;
    }

    public static Piece of(Type type, Color color) {
        return new Piece(type, color);
    }

    /**
     * @return All chess pieces available.
     */
    public static Set<Piece> all() {
        return List.of(Piece.Type.values()).crossProduct(List.of(Color.values()))
                .map(Functions.of(Piece::new).tupled())
                .toSet();
    }

    /**
     * @return True if the given piece has the same color of this piece.
     */
    public boolean isFriend(Piece piece) {
        return this.color == piece.color;
    }

    /**
     * @return True if the given piece has not the same color of this piece.
     */
    public boolean isOpponent(Piece piece) {
        return !isFriend(piece);
    }

    /**
     * @return the code of the piece in the visual representation of the board
     */
    public String getCode() {
        return color == Color.WHITE ? type.code.toUpperCase() : type.code;
    }

    @Override
    public String toString() {
        return String.format("%s %s", type, color);
    }

    @Override
    public boolean equals(Object rhs) {
        if (this == rhs) {
            return true;
        }
        if (!(rhs instanceof Piece)) {
            return false;
        }
        final Piece other = (Piece) rhs;
        return this.type == other.type && this.color == other.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color);
    }
}
