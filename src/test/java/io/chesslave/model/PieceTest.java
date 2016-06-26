package io.chesslave.model;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javaslang.collection.Set;
import org.junit.Test;

public class PieceTest {

    @Test
    public void pieceTypeValues() {
        assertEquals(1, Piece.Type.PAWN.value());
        assertEquals(3, Piece.Type.KNIGHT.value());
        assertEquals(3, Piece.Type.BISHOP.value());
        assertEquals(5, Piece.Type.ROOK.value());
        assertEquals(9, Piece.Type.QUEEN.value());
        assertEquals(0, Piece.Type.KING.value());
    }

    @Test
    public void isFriendTest() {
        final Piece whiteKing = Piece.of(Piece.Type.KING, Color.WHITE);
        final Piece whiteQueen = Piece.of(Piece.Type.QUEEN, Color.WHITE);
        final Piece blackKing = Piece.of(Piece.Type.KING, Color.BLACK);
        assertTrue(whiteKing.isFriend(whiteQueen));
        assertFalse(whiteKing.isFriend(blackKing));
    }

    @Test
    public void isOpponentTest() {
        final Piece whiteKing = Piece.of(Piece.Type.KING, Color.WHITE);
        final Piece whiteQueen = Piece.of(Piece.Type.QUEEN, Color.WHITE);
        final Piece blackKing = Piece.of(Piece.Type.KING, Color.BLACK);
        assertTrue(whiteKing.isOpponent(blackKing));
        assertFalse(whiteKing.isOpponent(whiteQueen));
    }

    @Test
    public void allTest() {
        final Set<Piece> allPieces = Piece.all();
        assertEquals(12, allPieces.size());
        assertThat(allPieces, hasItem(Piece.of(Piece.Type.KING, Color.WHITE)));
        assertThat(allPieces, hasItem(Piece.of(Piece.Type.QUEEN, Color.WHITE)));
        assertThat(allPieces, hasItem(Piece.of(Piece.Type.ROOK, Color.WHITE)));
        assertThat(allPieces, hasItem(Piece.of(Piece.Type.BISHOP, Color.WHITE)));
        assertThat(allPieces, hasItem(Piece.of(Piece.Type.KNIGHT, Color.WHITE)));
        assertThat(allPieces, hasItem(Piece.of(Piece.Type.PAWN, Color.WHITE)));
        assertThat(allPieces, hasItem(Piece.of(Piece.Type.KING, Color.BLACK)));
        assertThat(allPieces, hasItem(Piece.of(Piece.Type.QUEEN, Color.BLACK)));
        assertThat(allPieces, hasItem(Piece.of(Piece.Type.ROOK, Color.BLACK)));
        assertThat(allPieces, hasItem(Piece.of(Piece.Type.BISHOP, Color.BLACK)));
        assertThat(allPieces, hasItem(Piece.of(Piece.Type.KNIGHT, Color.BLACK)));
        assertThat(allPieces, hasItem(Piece.of(Piece.Type.PAWN, Color.BLACK)));
    }

    @Test
    public void equalsTest() {
        final Piece whiteKing = Piece.of(Piece.Type.KING, Color.WHITE);
        assertTrue(whiteKing.equals(whiteKing));
        assertFalse(whiteKing.equals(new Object()));
        assertTrue(whiteKing.equals(Piece.of(Piece.Type.KING, Color.WHITE)));
    }

    @Test
    public void hashCodeTest() {
        final Piece whiteKing = Piece.of(Piece.Type.KING, Color.WHITE);
        final int whiteKingHash = whiteKing.hashCode();
        assertEquals(whiteKingHash, whiteKing.hashCode());
        assertNotEquals(whiteKingHash, new Object().hashCode());
        assertEquals(whiteKingHash, Piece.of(Piece.Type.KING, Color.WHITE).hashCode());
    }
}