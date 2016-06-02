package io.chesslave.model;

import javaslang.collection.Set;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

public class PieceTest {

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
        assertEquals(12, allPieces.length());
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
}
