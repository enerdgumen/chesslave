package io.chesslave.model

import javaslang.control.Option
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
        assertTrue(Pawns.inPromotion(Color.WHITE, Square.of("e8")))
        assertTrue(Pawns.inPromotion(Color.BLACK, Square.of("a1")))

        assertFalse(Pawns.inPromotion(Color.WHITE, Square.of("c7")))
        assertFalse(Pawns.inPromotion(Color.BLACK, Square.of("h3")))
    }

    @Test
    fun isCaptureTest() {
        assertTrue(Pawns.isCapture(Movements.Regular(Square.of("e5"), Square.of("d6"), enPassant = true)))
        assertTrue(Pawns.isCapture(Movements.Regular(Square.of("b2"), Square.of("c3"))))

        assertFalse(Pawns.isCapture(Movements.Regular(Square.of("b2"), Square.of("b3"))))
        assertFalse(Pawns.isCapture(Movements.Regular(Square.of("g7"), Square.of("g8"), promotion = Option.some(Piece.Type.QUEEN))))
    }

    @Test
    fun isEnPassantAvailableWhiteTest() {
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | |p| | | | | ",
            "P|r| |P| |P|p| ",
            " | | | | | |k| ",
            " |P| | | | | | ",
            " | | | | | |K| ",
            " | | | | | | | ")
        assertTrue(Pawns.isEnPassantAvailable(Square.of("f5"), position))
        assertFalse(Pawns.isEnPassantAvailable(Square.of("d5"), position))
        assertFalse(Pawns.isEnPassantAvailable(Square.of("a5"), position))
        assertFalse(Pawns.isEnPassantAvailable(Square.of("b3"), position))
        assertFalse(Pawns.isEnPassantAvailable(Square.of("g2"), position))
        assertFalse(Pawns.isEnPassantAvailable(Square.of("a8"), position))
    }

    @Test
    fun isEnPassantAvailableBlackTest() {
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | |k| | | ",
            " | |p| | | | | ",
            " | | | | | | | ",
            "p|R| |p|P|P|p| ",
            " |P| | | |Q| | ",
            " | | | | | |K| ",
            " | | | | | | | ")
        assertTrue(Pawns.isEnPassantAvailable(Square.of("d4"), position))
        assertFalse(Pawns.isEnPassantAvailable(Square.of("a4"), position))
        assertFalse(Pawns.isEnPassantAvailable(Square.of("g4"), position))
        assertFalse(Pawns.isEnPassantAvailable(Square.of("c6"), position))
        assertFalse(Pawns.isEnPassantAvailable(Square.of("e7"), position))
        assertFalse(Pawns.isEnPassantAvailable(Square.of("a8"), position))
    }

    @Test
    fun isEnPassantAvailableTest() {
        val position = Positions.fromText(
            " | | | | | | |k",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | |p|P|P| | | ",
            " | | | |p| |K| ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertTrue(Pawns.isEnPassantAvailable(Square.of("c4"), position))
        assertFalse(Pawns.isEnPassantAvailable(Square.of("d4"), position))
        assertFalse(Pawns.isEnPassantAvailable(Square.of("e4"), position))
    }
}
