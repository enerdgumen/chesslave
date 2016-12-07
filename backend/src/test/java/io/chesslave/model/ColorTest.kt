package io.chesslave.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ColorTest {

    @Test
    public void opponentTest() {
        assertEquals(Color.BLACK, Color.WHITE.opponent());
        assertEquals(Color.WHITE, Color.BLACK.opponent());
    }

    @Test
    public void pawnTest() {
        assertEquals(Piece.of(Piece.Type.PAWN, Color.WHITE), Color.WHITE.pawn());
        assertEquals(Piece.of(Piece.Type.PAWN, Color.BLACK), Color.BLACK.pawn());
    }

    @Test
    public void knightTest() {
        assertEquals(Piece.of(Piece.Type.KNIGHT, Color.WHITE), Color.WHITE.knight());
        assertEquals(Piece.of(Piece.Type.KNIGHT, Color.BLACK), Color.BLACK.knight());
    }

    @Test
    public void bishopTest() {
        assertEquals(Piece.of(Piece.Type.BISHOP, Color.WHITE), Color.WHITE.bishop());
        assertEquals(Piece.of(Piece.Type.BISHOP, Color.BLACK), Color.BLACK.bishop());
    }

    @Test
    public void rookTest() {
        assertEquals(Piece.of(Piece.Type.ROOK, Color.WHITE), Color.WHITE.rook());
        assertEquals(Piece.of(Piece.Type.ROOK, Color.BLACK), Color.BLACK.rook());
    }

    @Test
    public void queenTest() {
        assertEquals(Piece.of(Piece.Type.QUEEN, Color.WHITE), Color.WHITE.queen());
        assertEquals(Piece.of(Piece.Type.QUEEN, Color.BLACK), Color.BLACK.queen());
    }

    @Test
    public void kingTest() {
        assertEquals(Piece.of(Piece.Type.KING, Color.WHITE), Color.WHITE.king());
        assertEquals(Piece.of(Piece.Type.KING, Color.BLACK), Color.BLACK.king());
    }
}
