package io.chesslave.model

import io.chesslave.model.Move.Regular.Variation.EnPassant
import io.chesslave.model.Move.Regular.Variation.Promotion
import io.chesslave.model.Piece.Type
import org.junit.Assert.*
import org.junit.Test

class PawnsTest {

    @Test
    fun directionTest() {
        assertEquals(1, Pawns.direction(Color.WHITE).toLong())
        assertEquals(-1, Pawns.direction(Color.BLACK).toLong())
    }

    @Test
    fun inPromotionTest() {
        assertTrue(Pawns.inPromotion(Color.WHITE, Board.e8))
        assertTrue(Pawns.inPromotion(Color.BLACK, Board.a1))

        assertFalse(Pawns.inPromotion(Color.WHITE, Board.c7))
        assertFalse(Pawns.inPromotion(Color.BLACK, Board.h3))
    }

    @Test
    fun isCaptureTest() {
        assertTrue(Pawns.isCapture(Move.Regular(Board.e5, Board.d6, EnPassant())))
        assertTrue(Pawns.isCapture(Move.Regular(Board.b2, Board.c3)))

        assertFalse(Pawns.isCapture(Move.Regular(Board.b2, Board.b3)))
        assertFalse(Pawns.isCapture(Move.Regular(Board.g7, Board.g8, Promotion(Type.QUEEN))))
    }

    @Test
    fun isEnPassantAvailableWhiteTest() {
        val position = positionFromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | |p| | | | | ",
            "P|r| |P| |P|p| ",
            " | | | | | |k| ",
            " |P| | | | | | ",
            " | | | | | |K| ",
            " | | | | | | | ")
        assertTrue(Pawns.isEnPassantAvailable(Board.f5, position))
        assertFalse(Pawns.isEnPassantAvailable(Board.d5, position))
        assertFalse(Pawns.isEnPassantAvailable(Board.a5, position))
        assertFalse(Pawns.isEnPassantAvailable(Board.b3, position))
        assertFalse(Pawns.isEnPassantAvailable(Board.g2, position))
        assertFalse(Pawns.isEnPassantAvailable(Board.a8, position))
    }

    @Test
    fun isEnPassantAvailableBlackTest() {
        val position = positionFromText(
            " | | | | | | | ",
            " | | | |k| | | ",
            " | |p| | | | | ",
            " | | | | | | | ",
            "p|R| |p|P|P|p| ",
            " |P| | | |Q| | ",
            " | | | | | |K| ",
            " | | | | | | | ")
        assertTrue(Pawns.isEnPassantAvailable(Board.d4, position))
        assertFalse(Pawns.isEnPassantAvailable(Board.a4, position))
        assertFalse(Pawns.isEnPassantAvailable(Board.g4, position))
        assertFalse(Pawns.isEnPassantAvailable(Board.c6, position))
        assertFalse(Pawns.isEnPassantAvailable(Board.e7, position))
        assertFalse(Pawns.isEnPassantAvailable(Board.a8, position))
    }

    @Test
    fun isEnPassantAvailableTest() {
        val position = positionFromText(
            " | | | | | | |k",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | |p|P|P| | | ",
            " | | | |p| |K| ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertTrue(Pawns.isEnPassantAvailable(Board.c4, position))
        assertFalse(Pawns.isEnPassantAvailable(Board.d4, position))
        assertFalse(Pawns.isEnPassantAvailable(Board.e4, position))
    }
}
