package io.chesslave.model

import org.junit.Assert.assertEquals

import io.chesslave.model.Piece.Type
import org.junit.Test

class ColorTest {

    @Test
    fun opponentTest() {
        assertEquals(Color.BLACK, Color.WHITE.opponent())
        assertEquals(Color.WHITE, Color.BLACK.opponent())
    }

    @Test
    fun pawnTest() {
        assertEquals(Piece(Type.PAWN, Color.WHITE), Color.WHITE.pawn())
        assertEquals(Piece(Type.PAWN, Color.BLACK), Color.BLACK.pawn())
    }

    @Test
    fun knightTest() {
        assertEquals(Piece(Type.KNIGHT, Color.WHITE), Color.WHITE.knight())
        assertEquals(Piece(Type.KNIGHT, Color.BLACK), Color.BLACK.knight())
    }

    @Test
    fun bishopTest() {
        assertEquals(Piece(Type.BISHOP, Color.WHITE), Color.WHITE.bishop())
        assertEquals(Piece(Type.BISHOP, Color.BLACK), Color.BLACK.bishop())
    }

    @Test
    fun rookTest() {
        assertEquals(Piece(Type.ROOK, Color.WHITE), Color.WHITE.rook())
        assertEquals(Piece(Type.ROOK, Color.BLACK), Color.BLACK.rook())
    }

    @Test
    fun queenTest() {
        assertEquals(Piece(Type.QUEEN, Color.WHITE), Color.WHITE.queen())
        assertEquals(Piece(Type.QUEEN, Color.BLACK), Color.BLACK.queen())
    }

    @Test
    fun kingTest() {
        assertEquals(Piece(Type.KING, Color.WHITE), Color.WHITE.king())
        assertEquals(Piece(Type.KING, Color.BLACK), Color.BLACK.king())
    }
}
