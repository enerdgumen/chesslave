package io.chesslave.model;

import javaslang.control.Option;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PositionTest {

    private Position startPosition;

    @Before
    public void setUp() {
        startPosition = Position.of(
                "r|n|b|q|k|b|n|r",
                "p|p|p|p|p|p|p|p",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                "P|P|P|P|P|P|P|P",
                "R|N|B|Q|K|B|N|R");
    }

    @Test
    public void canCreatePositionFromText() {
        final Position expected = new Position.Builder()
                .withPiece(Square.of("a1"), Piece.of(Piece.Type.ROOK, Color.WHITE))
                .withPiece(Square.of("b1"), Piece.of(Piece.Type.KNIGHT, Color.WHITE))
                .withPiece(Square.of("c1"), Piece.of(Piece.Type.BISHOP, Color.WHITE))
                .withPiece(Square.of("d1"), Piece.of(Piece.Type.QUEEN, Color.WHITE))
                .withPiece(Square.of("e1"), Piece.of(Piece.Type.KING, Color.WHITE))
                .withPiece(Square.of("f1"), Piece.of(Piece.Type.BISHOP, Color.WHITE))
                .withPiece(Square.of("g1"), Piece.of(Piece.Type.KNIGHT, Color.WHITE))
                .withPiece(Square.of("h1"), Piece.of(Piece.Type.ROOK, Color.WHITE))
                .withPiece(Square.of("a2"), Piece.of(Piece.Type.PAWN, Color.WHITE))
                .withPiece(Square.of("b2"), Piece.of(Piece.Type.PAWN, Color.WHITE))
                .withPiece(Square.of("c2"), Piece.of(Piece.Type.PAWN, Color.WHITE))
                .withPiece(Square.of("d2"), Piece.of(Piece.Type.PAWN, Color.WHITE))
                .withPiece(Square.of("e2"), Piece.of(Piece.Type.PAWN, Color.WHITE))
                .withPiece(Square.of("f2"), Piece.of(Piece.Type.PAWN, Color.WHITE))
                .withPiece(Square.of("g2"), Piece.of(Piece.Type.PAWN, Color.WHITE))
                .withPiece(Square.of("h2"), Piece.of(Piece.Type.PAWN, Color.WHITE))
                .withPiece(Square.of("a8"), Piece.of(Piece.Type.ROOK, Color.BLACK))
                .withPiece(Square.of("b8"), Piece.of(Piece.Type.KNIGHT, Color.BLACK))
                .withPiece(Square.of("c8"), Piece.of(Piece.Type.BISHOP, Color.BLACK))
                .withPiece(Square.of("d8"), Piece.of(Piece.Type.QUEEN, Color.BLACK))
                .withPiece(Square.of("e8"), Piece.of(Piece.Type.KING, Color.BLACK))
                .withPiece(Square.of("f8"), Piece.of(Piece.Type.BISHOP, Color.BLACK))
                .withPiece(Square.of("g8"), Piece.of(Piece.Type.KNIGHT, Color.BLACK))
                .withPiece(Square.of("h8"), Piece.of(Piece.Type.ROOK, Color.BLACK))
                .withPiece(Square.of("a7"), Piece.of(Piece.Type.PAWN, Color.BLACK))
                .withPiece(Square.of("b7"), Piece.of(Piece.Type.PAWN, Color.BLACK))
                .withPiece(Square.of("c7"), Piece.of(Piece.Type.PAWN, Color.BLACK))
                .withPiece(Square.of("d7"), Piece.of(Piece.Type.PAWN, Color.BLACK))
                .withPiece(Square.of("e7"), Piece.of(Piece.Type.PAWN, Color.BLACK))
                .withPiece(Square.of("f7"), Piece.of(Piece.Type.PAWN, Color.BLACK))
                .withPiece(Square.of("g7"), Piece.of(Piece.Type.PAWN, Color.BLACK))
                .withPiece(Square.of("h7"), Piece.of(Piece.Type.PAWN, Color.BLACK))
                .build();
        assertEquals(expected, startPosition);
    }

    @Test
    public void atTest() {
        final Option<Piece> whiteKing = startPosition.at(Square.of("e1"));
        assertTrue(whiteKing.isDefined());
        assertEquals(Piece.of(Piece.Type.KING, Color.WHITE), whiteKing.get());

        final Option<Piece> blackQueen = startPosition.at(Square.of("d8"));
        assertTrue(blackQueen.isDefined());
        assertEquals(Piece.of(Piece.Type.QUEEN, Color.BLACK), blackQueen.get());

        final Option<Piece> noPiece = startPosition.at(Square.of("b4"));
        assertTrue(noPiece.isEmpty());
    }

    @Test
    public void putTest() {
        final Position newPosition = startPosition.put(Square.of("e4"), Piece.of(Piece.Type.PAWN, Color.BLACK));
        final Option<Piece> whiteKing = newPosition.at(Square.of("e1"));
        assertTrue(whiteKing.isDefined());
        assertEquals(Piece.of(Piece.Type.KING, Color.WHITE), whiteKing.get());

        final Option<Piece> blackPawn = newPosition.at(Square.of("e4"));
        assertTrue(blackPawn.isDefined());
        assertEquals(Piece.of(Piece.Type.PAWN, Color.BLACK), blackPawn.get());

        final Option<Piece> noPiece = newPosition.at(Square.of("g3"));
        assertTrue(noPiece.isEmpty());
    }

    @Test
    public void removeTest() {
        final Position newPosition = startPosition.remove(Square.of("a2"));
        final Option<Piece> whiteKing = newPosition.at(Square.of("e1"));
        assertTrue(whiteKing.isDefined());
        assertEquals(Piece.of(Piece.Type.KING, Color.WHITE), whiteKing.get());

        final Option<Piece> noPiece = newPosition.at(Square.of("a2"));
        assertTrue(noPiece.isEmpty());
    }

    @Test
    public void moveTest() {
        final Position newPosition = startPosition.move(Square.of("e2"), Square.of("e4"));
        final Option<Piece> whiteKing = newPosition.at(Square.of("e1"));
        assertTrue(whiteKing.isDefined());
        assertEquals(Piece.of(Piece.Type.KING, Color.WHITE), whiteKing.get());

        final Option<Piece> whitePawn = newPosition.at(Square.of("e4"));
        assertTrue(whitePawn.isDefined());
        assertEquals(Piece.of(Piece.Type.PAWN, Color.WHITE), whitePawn.get());

        final Option<Piece> noPiece = newPosition.at(Square.of("e2"));
        assertTrue(noPiece.isEmpty());
    }

    @Test
    public void moveToCaptureTest() {
        final Position newPosition = startPosition.move(Square.of("e2"), Square.of("e7"));
        final Option<Piece> whitePawn = newPosition.at(Square.of("e7"));
        assertTrue(whitePawn.isDefined());
        assertEquals(Piece.of(Piece.Type.PAWN, Color.WHITE), whitePawn.get());

        final Option<Piece> noPiece = newPosition.at(Square.of("e2"));
        assertTrue(noPiece.isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void moveEmptySquareTest() {
        startPosition.move(Square.of("e4"), Square.of("e2"));
    }
}