package io.chesslave.model;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class PositionBuilderTest {

    @Test
    public void withPieceIllegalArgumentTest() {
        final Square e2 = Square.of("e2");
        final Piece whitePawn = Piece.of(Piece.Type.PAWN, Color.WHITE);
        final Piece whiteKnight = Piece.of(Piece.Type.KNIGHT, Color.WHITE);

        Position.Builder positionBuilder = new Position.Builder().withPiece(e2, whitePawn);
        try {
            positionBuilder.withPiece(e2, whiteKnight);
            fail("Should fail when the square is already occupied");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), containsString(e2.toString()));
            assertThat(iae.getMessage(), containsString(whiteKnight.toString()));
            assertThat(iae.getMessage(), containsString("square already used"));
        }
    }
}
