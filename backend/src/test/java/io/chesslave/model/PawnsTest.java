package io.chesslave.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PawnsTest {

    @Test
    public void directionTest() {
        assertEquals(1, Pawns.direction(Color.WHITE));
        assertEquals(-1, Pawns.direction(Color.BLACK));
    }

    @Test
    public void inPromotionTest() {
        assertTrue(Pawns.inPromotion(Color.WHITE, Square.of("e8")));
        assertTrue(Pawns.inPromotion(Color.BLACK, Square.of("a1")));

        assertFalse(Pawns.inPromotion(Color.WHITE, Square.of("c7")));
        assertFalse(Pawns.inPromotion(Color.BLACK, Square.of("h3")));
    }

    @Test
    public void isCaptureTest() {
        assertTrue(Pawns.isCapture(Movements.enPassant(Square.of("e5"), Square.of("d6"))));
        assertTrue(Pawns.isCapture(Movements.regular(Square.of("b2"), Square.of("c3"))));

        assertFalse(Pawns.isCapture(Movements.regular(Square.of("b2"), Square.of("b3"))));
        assertFalse(Pawns.isCapture(Movements.promotion(Square.of("g7"), Square.of("g8"), Piece.Type.QUEEN)));
    }

    @Test
    public void isEnPassantAvailableWhiteTest() {
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | | | | | | | ",
                " | |p| | | | | ",
                "P|r| |P| |P|p| ",
                " | | | | | |k| ",
                " |P| | | | | | ",
                " | | | | | |K| ",
                " | | | | | | | ");
        assertTrue(Pawns.isEnPassantAvailable(Square.of("f5"), position));
        assertFalse(Pawns.isEnPassantAvailable(Square.of("d5"), position));
        assertFalse(Pawns.isEnPassantAvailable(Square.of("a5"), position));
        assertFalse(Pawns.isEnPassantAvailable(Square.of("b3"), position));
        assertFalse(Pawns.isEnPassantAvailable(Square.of("g2"), position));
        assertFalse(Pawns.isEnPassantAvailable(Square.of("a8"), position));
    }

    @Test
    public void isEnPassantAvailableBlackTest() {
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | | | |k| | | ",
                " | |p| | | | | ",
                " | | | | | | | ",
                "p|R| |p|P|P|p| ",
                " |P| | | |Q| | ",
                " | | | | | |K| ",
                " | | | | | | | ");
        assertTrue(Pawns.isEnPassantAvailable(Square.of("d4"), position));
        assertFalse(Pawns.isEnPassantAvailable(Square.of("a4"), position));
        assertFalse(Pawns.isEnPassantAvailable(Square.of("g4"), position));
        assertFalse(Pawns.isEnPassantAvailable(Square.of("c6"), position));
        assertFalse(Pawns.isEnPassantAvailable(Square.of("e7"), position));
        assertFalse(Pawns.isEnPassantAvailable(Square.of("a8"), position));
    }

    @Test
    public void isEnPassantAvailableTest() {
        final Position position = Positions.fromText(
                " | | | | | | |k",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | |p|P|P| | | ",
                " | | | |p| |K| ",
                " | | | | | | | ",
                " | | | | | | | ");
        assertTrue(Pawns.isEnPassantAvailable(Square.of("c4"), position));
        assertFalse(Pawns.isEnPassantAvailable(Square.of("d4"), position));
        assertFalse(Pawns.isEnPassantAvailable(Square.of("e4"), position));
    }
}
