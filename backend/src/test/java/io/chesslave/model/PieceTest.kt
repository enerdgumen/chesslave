package io.chesslave.model

import io.chesslave.model.Piece.Type
import org.hamcrest.CoreMatchers.hasItem
import org.junit.Assert.*
import org.junit.Test

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
        val whiteKing = Piece(Type.KING, Color.WHITE)
        val whiteQueen = Piece(Type.QUEEN, Color.WHITE)
        val blackKing = Piece(Type.KING, Color.BLACK)
        assertTrue(whiteKing.isFriend(whiteQueen))
        assertFalse(whiteKing.isFriend(blackKing))
    }

    @Test
    fun isOpponentTest() {
        val whiteKing = Piece(Type.KING, Color.WHITE)
        val whiteQueen = Piece(Type.QUEEN, Color.WHITE)
        val blackKing = Piece(Type.KING, Color.BLACK)
        assertTrue(whiteKing.isOpponent(blackKing))
        assertFalse(whiteKing.isOpponent(whiteQueen))
    }

    @Test
    fun allTest() {
        val allPieces = Piece.all()
        assertEquals(12, allPieces.size())
        assertThat(allPieces, hasItem(Piece(Type.KING, Color.WHITE)))
        assertThat(allPieces, hasItem(Piece(Type.QUEEN, Color.WHITE)))
        assertThat(allPieces, hasItem(Piece(Type.ROOK, Color.WHITE)))
        assertThat(allPieces, hasItem(Piece(Type.BISHOP, Color.WHITE)))
        assertThat(allPieces, hasItem(Piece(Type.KNIGHT, Color.WHITE)))
        assertThat(allPieces, hasItem(Piece(Type.PAWN, Color.WHITE)))
        assertThat(allPieces, hasItem(Piece(Type.KING, Color.BLACK)))
        assertThat(allPieces, hasItem(Piece(Type.QUEEN, Color.BLACK)))
        assertThat(allPieces, hasItem(Piece(Type.ROOK, Color.BLACK)))
        assertThat(allPieces, hasItem(Piece(Type.BISHOP, Color.BLACK)))
        assertThat(allPieces, hasItem(Piece(Type.KNIGHT, Color.BLACK)))
        assertThat(allPieces, hasItem(Piece(Type.PAWN, Color.BLACK)))
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
        val whiteKingHash = whiteKing.hashCode()
        assertEquals(whiteKingHash, whiteKing.hashCode())
        assertNotEquals(whiteKingHash, Any().hashCode())
        assertEquals(whiteKingHash, Piece(Type.KING, Color.WHITE).hashCode())
    }
}