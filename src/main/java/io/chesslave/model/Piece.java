package io.chesslave.model;

import io.chesslave.Functions;
import javaslang.collection.List;
import javaslang.collection.Set;
import java.util.Objects;

public class Piece {

    public enum Type {

        PAWN,
        KNIGHT,
        BISHOP,
        ROOK,
        QUEEN,
        KING
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

    public static Set<Piece> all() {
        return List.of(Piece.Type.values()).crossProduct(List.of(Color.values()))
                .map(Functions.of(Piece::new).tupled())
                .toSet();
    }

    public boolean isFriend(Piece piece) {
        return this.color == piece.color;
    }

    public boolean isOpponent(Piece piece) {
        return this.color != piece.color;
    }

    @Override
    public String toString() {
        return String.format("%s %s", type, color);
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof Piece == false) {
            return false;
        }
        final Piece other = (Piece) rhs;
        return this.type == other.type &&
                this.color == other.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color);
    }
}
