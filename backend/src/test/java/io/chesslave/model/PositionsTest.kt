package io.chesslave.model

import io.chesslave.model.Piece.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class PositionsTest {

    val position = Position.Builder()
        .withPiece(Board.a1, Piece(Type.ROOK, Color.WHITE))
        .withPiece(Board.b1, Piece(Type.KNIGHT, Color.WHITE))
        .withPiece(Board.c1, Piece(Type.BISHOP, Color.WHITE))
        .withPiece(Board.d1, Piece(Type.QUEEN, Color.WHITE))
        .withPiece(Board.e1, Piece(Type.KING, Color.WHITE))
        .withPiece(Board.f1, Piece(Type.BISHOP, Color.WHITE))
        .withPiece(Board.g1, Piece(Type.KNIGHT, Color.WHITE))
        .withPiece(Board.h1, Piece(Type.ROOK, Color.WHITE))
        .withPiece(Board.a2, Piece(Type.PAWN, Color.WHITE))
        .withPiece(Board.b2, Piece(Type.PAWN, Color.WHITE))
        .withPiece(Board.c2, Piece(Type.PAWN, Color.WHITE))
        .withPiece(Board.d2, Piece(Type.PAWN, Color.WHITE))
        .withPiece(Board.e2, Piece(Type.PAWN, Color.WHITE))
        .withPiece(Board.f2, Piece(Type.PAWN, Color.WHITE))
        .withPiece(Board.g2, Piece(Type.PAWN, Color.WHITE))
        .withPiece(Board.h2, Piece(Type.PAWN, Color.WHITE))
        .withPiece(Board.a8, Piece(Type.ROOK, Color.BLACK))
        .withPiece(Board.b8, Piece(Type.KNIGHT, Color.BLACK))
        .withPiece(Board.c8, Piece(Type.BISHOP, Color.BLACK))
        .withPiece(Board.d8, Piece(Type.QUEEN, Color.BLACK))
        .withPiece(Board.e8, Piece(Type.KING, Color.BLACK))
        .withPiece(Board.f8, Piece(Type.BISHOP, Color.BLACK))
        .withPiece(Board.g8, Piece(Type.KNIGHT, Color.BLACK))
        .withPiece(Board.h8, Piece(Type.ROOK, Color.BLACK))
        .withPiece(Board.a7, Piece(Type.PAWN, Color.BLACK))
        .withPiece(Board.b7, Piece(Type.PAWN, Color.BLACK))
        .withPiece(Board.c7, Piece(Type.PAWN, Color.BLACK))
        .withPiece(Board.d7, Piece(Type.PAWN, Color.BLACK))
        .withPiece(Board.e7, Piece(Type.PAWN, Color.BLACK))
        .withPiece(Board.f7, Piece(Type.PAWN, Color.BLACK))
        .withPiece(Board.g7, Piece(Type.PAWN, Color.BLACK))
        .withPiece(Board.h7, Piece(Type.PAWN, Color.BLACK))
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