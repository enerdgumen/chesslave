package io.chesslave.model

import io.chesslave.model.Piece.Type
import org.hamcrest.CoreMatchers.hasItem
import org.junit.Assert.*
import org.junit.Test

class ColorTest {

    @Test
    fun opponentTest() {
        assertEquals(Color.BLACK, Color.WHITE.opponent())
        assertEquals(Color.WHITE, Color.BLACK.opponent())
    }
}

class PieceTest {

    @Test
    fun pieceTypeValues() {
        assertEquals(1, Type.PAWN.value)
        assertEquals(3, Type.KNIGHT.value)
        assertEquals(3, Type.BISHOP.value)
        assertEquals(5, Type.ROOK.value)
        assertEquals(9, Type.QUEEN.value)
        assertEquals(0, Type.KING.value)
    }

    @Test
    fun isFriendTest() {
        assertTrue(Piece.whiteKing.isFriend(Piece.whiteQueen))
        assertFalse(Piece.whiteKing.isFriend(Piece.blackKing))
    }

    @Test
    fun isOpponentTest() {
        assertTrue(Piece.whiteKing.isOpponent(Piece.blackKing))
        assertFalse(Piece.whiteKing.isOpponent(Piece.whiteQueen))
    }

    @Test
    fun allTest() {
        val allPieces = Piece.all
        assertEquals(12, allPieces.size())
        assertThat(allPieces, hasItem(Piece.whiteKing))
        assertThat(allPieces, hasItem(Piece.whiteQueen))
        assertThat(allPieces, hasItem(Piece.whiteRook))
        assertThat(allPieces, hasItem(Piece.whiteBishop))
        assertThat(allPieces, hasItem(Piece.whiteKnight))
        assertThat(allPieces, hasItem(Piece.whitePawn))
        assertThat(allPieces, hasItem(Piece.blackKing))
        assertThat(allPieces, hasItem(Piece.blackQueen))
        assertThat(allPieces, hasItem(Piece.blackRook))
        assertThat(allPieces, hasItem(Piece.blackBishop))
        assertThat(allPieces, hasItem(Piece.blackKnight))
        assertThat(allPieces, hasItem(Piece.blackPawn))
    }

    @Test
    fun equalsTest() {
        val whiteKing = Piece(Type.KING, Color.WHITE)
        assertTrue(whiteKing == whiteKing)
        assertFalse(whiteKing == Any())
        assertTrue(whiteKing == Piece(Type.KING, Color.WHITE))
    }

    @Test
    fun hashCodeTest() {
        val whiteKing = Piece(Type.KING, Color.WHITE)
        assertNotEquals(whiteKing.hashCode(), Any().hashCode())
        assertEquals(whiteKing.hashCode(), Piece(Type.KING, Color.WHITE).hashCode())
    }
}