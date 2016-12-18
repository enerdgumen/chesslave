package io.chesslave.model

import io.chesslave.model.Piece.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class PositionsTest {

    val position = Position.Builder()
        .withPiece(Square.of("a1"), Piece(Type.ROOK, Color.WHITE))
        .withPiece(Square.of("b1"), Piece(Type.KNIGHT, Color.WHITE))
        .withPiece(Square.of("c1"), Piece(Type.BISHOP, Color.WHITE))
        .withPiece(Square.of("d1"), Piece(Type.QUEEN, Color.WHITE))
        .withPiece(Square.of("e1"), Piece(Type.KING, Color.WHITE))
        .withPiece(Square.of("f1"), Piece(Type.BISHOP, Color.WHITE))
        .withPiece(Square.of("g1"), Piece(Type.KNIGHT, Color.WHITE))
        .withPiece(Square.of("h1"), Piece(Type.ROOK, Color.WHITE))
        .withPiece(Square.of("a2"), Piece(Type.PAWN, Color.WHITE))
        .withPiece(Square.of("b2"), Piece(Type.PAWN, Color.WHITE))
        .withPiece(Square.of("c2"), Piece(Type.PAWN, Color.WHITE))
        .withPiece(Square.of("d2"), Piece(Type.PAWN, Color.WHITE))
        .withPiece(Square.of("e2"), Piece(Type.PAWN, Color.WHITE))
        .withPiece(Square.of("f2"), Piece(Type.PAWN, Color.WHITE))
        .withPiece(Square.of("g2"), Piece(Type.PAWN, Color.WHITE))
        .withPiece(Square.of("h2"), Piece(Type.PAWN, Color.WHITE))
        .withPiece(Square.of("a8"), Piece(Type.ROOK, Color.BLACK))
        .withPiece(Square.of("b8"), Piece(Type.KNIGHT, Color.BLACK))
        .withPiece(Square.of("c8"), Piece(Type.BISHOP, Color.BLACK))
        .withPiece(Square.of("d8"), Piece(Type.QUEEN, Color.BLACK))
        .withPiece(Square.of("e8"), Piece(Type.KING, Color.BLACK))
        .withPiece(Square.of("f8"), Piece(Type.BISHOP, Color.BLACK))
        .withPiece(Square.of("g8"), Piece(Type.KNIGHT, Color.BLACK))
        .withPiece(Square.of("h8"), Piece(Type.ROOK, Color.BLACK))
        .withPiece(Square.of("a7"), Piece(Type.PAWN, Color.BLACK))
        .withPiece(Square.of("b7"), Piece(Type.PAWN, Color.BLACK))
        .withPiece(Square.of("c7"), Piece(Type.PAWN, Color.BLACK))
        .withPiece(Square.of("d7"), Piece(Type.PAWN, Color.BLACK))
        .withPiece(Square.of("e7"), Piece(Type.PAWN, Color.BLACK))
        .withPiece(Square.of("f7"), Piece(Type.PAWN, Color.BLACK))
        .withPiece(Square.of("g7"), Piece(Type.PAWN, Color.BLACK))
        .withPiece(Square.of("h7"), Piece(Type.PAWN, Color.BLACK))
        .build()

    @Test
    fun canCreatePositionFromText() {
        val got = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p|p|p|p|p|p|p",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "P|P|P|P|P|P|P|P",
            "R|N|B|Q|K|B|N|R")
        assertEquals(position, got)
    }

    @Test
    fun canRenderPositionToText() {
        val got = Positions.toText(position)
        val expected = "" +
            "r|n|b|q|k|b|n|r\n" +
            "p|p|p|p|p|p|p|p\n" +
            " | | | | | | | \n" +
            " | | | | | | | \n" +
            " | | | | | | | \n" +
            " | | | | | | | \n" +
            "P|P|P|P|P|P|P|P\n" +
            "R|N|B|Q|K|B|N|R"
        assertEquals(expected, got)
    }
}