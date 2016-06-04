package io.chesslave.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PositionsTest {

    final Position position = new Position.Builder()
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

    @Test
    public void canCreatePositionFromText() {
        final Position got = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p|p|p|p|p|p|p",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                "P|P|P|P|P|P|P|P",
                "R|N|B|Q|K|B|N|R");
        assertEquals(position, got);
    }

    @Test
    public void canRenderPositionToText() {
        final String got = Positions.toText(position);
        final String expected
                = "r|n|b|q|k|b|n|r\n"
                + "p|p|p|p|p|p|p|p\n"
                + " | | | | | | | \n"
                + " | | | | | | | \n"
                + " | | | | | | | \n"
                + " | | | | | | | \n"
                + "P|P|P|P|P|P|P|P\n"
                + "R|N|B|Q|K|B|N|R";
        assertEquals(expected, got);
    }
}