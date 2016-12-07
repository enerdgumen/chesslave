package io.chesslave.model;

import io.chesslave.support.Ensure;
import org.immutables.value.Value;
import java.util.Optional;

@Value.Enclosing
public interface MoveDescription {

    @Value.Immutable
    interface Square {

        Optional<Piece.Type> piece();

        Optional<Integer> col();

        Optional<Integer> row();

        @Value.Check
        default void check() {
            Ensure.isTrue(piece().isPresent() || col().isPresent() || row().isPresent(), "Wrong square description!");
        }
    }

    enum Status {
        RELAX,
        CHECK,
        CHECKMATE
    }

    @Value.Immutable
    interface Regular extends MoveDescription {

        Square fromSquare();

        Square toSquare();

        boolean capture();

        boolean enPassant();

        Optional<Piece.Type> promotion();

        Status status();
    }

    @Value.Immutable
    interface Castling extends MoveDescription {

        boolean isShort();

        Status status();
    }
}
