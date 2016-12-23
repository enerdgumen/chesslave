package io.chesslave.model

import io.chesslave.model.Piece.Type
import javaslang.control.Option
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MoveTest {

    @Test
    fun shortCastleWhiteTest() {
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | |R")
        val shortCastling = Move.ShortCastling(Color.WHITE)
        val newPosition = shortCastling.apply(position)

        val noPieceOnE1 = newPosition.at(Board.e1)
        assertTrue(noPieceOnE1.isEmpty)
        val whiteRook = newPosition.at(Board.f1)
        assertTrue(whiteRook.isDefined)
        assertEquals(Piece(Type.ROOK, Color.WHITE), whiteRook.get())
        val whiteKing = newPosition.at(Board.g1)
        assertTrue(whiteKing.isDefined)
        assertEquals(Piece(Type.KING, Color.WHITE), whiteKing.get())
        val noPieceOnH1 = newPosition.at(Board.h1)
        assertTrue(noPieceOnH1.isEmpty)
    }

    @Test
    fun longCastleWhiteTest() {
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "R| | | |K| | | ")
        val longCastling = Move.LongCastling(Color.WHITE)
        val newPosition = longCastling.apply(position)

        val noPieceOnE1 = newPosition.at(Board.e1)
        assertTrue(noPieceOnE1.isEmpty)
        val whiteRook = newPosition.at(Board.d1)
        assertTrue(whiteRook.isDefined)
        assertEquals(Piece(Type.ROOK, Color.WHITE), whiteRook.get())
        val whiteKing = newPosition.at(Board.c1)
        assertTrue(whiteKing.isDefined)
        assertEquals(Piece(Type.KING, Color.WHITE), whiteKing.get())
        val noPieceOnB1 = newPosition.at(Board.b1)
        assertTrue(noPieceOnB1.isEmpty)
        val noPieceOnA1 = newPosition.at(Board.a1)
        assertTrue(noPieceOnA1.isEmpty)
    }

    @Test
    fun shortCastleBlackTest() {
        val position = Positions.fromText(
            " | | | |k| | |r",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val shortCastling = Move.ShortCastling(Color.BLACK)
        val newPosition = shortCastling.apply(position)

        val noPieceOnE8 = newPosition.at(Board.e8)
        assertTrue(noPieceOnE8.isEmpty)
        val blackRook = newPosition.at(Board.f8)
        assertTrue(blackRook.isDefined)
        assertEquals(Piece(Type.ROOK, Color.BLACK), blackRook.get())
        val blackKing = newPosition.at(Board.g8)
        assertTrue(blackKing.isDefined)
        assertEquals(Piece(Type.KING, Color.BLACK), blackKing.get())
        val noPieceOnH8 = newPosition.at(Board.h8)
        assertTrue(noPieceOnH8.isEmpty)
    }

    @Test
    fun longCastleBlackTest() {
        val position = Positions.fromText(
            "r| | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val longCastling = Move.LongCastling(Color.BLACK)
        val newPosition = longCastling.apply(position)

        val noPieceOnE8 = newPosition.at(Board.e8)
        assertTrue(noPieceOnE8.isEmpty)
        val blackRook = newPosition.at(Board.d8)
        assertTrue(blackRook.isDefined)
        assertEquals(Piece(Type.ROOK, Color.BLACK), blackRook.get())
        val blackKing = newPosition.at(Board.c8)
        assertTrue(blackKing.isDefined)
        assertEquals(Piece(Type.KING, Color.BLACK), blackKing.get())
        val noPieceOnB8 = newPosition.at(Board.b8)
        assertTrue(noPieceOnB8.isEmpty)
        val noPieceOnA8 = newPosition.at(Board.a8)
        assertTrue(noPieceOnA8.isEmpty)
    }

    @Test
    fun enPassantWhiteTest() {
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | |p|P| | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val enPassant = Move.Regular(Board.d5, Board.c6, enPassant = true)
        val newPosition = enPassant.apply(position)

        val noPieceOnD5 = newPosition.at(Board.d5)
        assertTrue(noPieceOnD5.isEmpty)
        val noPieceOnD6 = newPosition.at(Board.d6)
        assertTrue(noPieceOnD6.isEmpty)
        val noPieceOnC5 = newPosition.at(Board.c5)
        assertTrue(noPieceOnC5.isEmpty)
        val whitePawn = newPosition.at(Board.c6)
        assertTrue(whitePawn.isDefined)
        assertEquals(Piece(Type.PAWN, Color.WHITE), whitePawn.get())
    }

    @Test
    fun enPassantBlackTest() {
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | |P|p| ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val enPassant = Move.Regular(Board.g4, Board.f3, enPassant = true)
        val newPosition = enPassant.apply(position)

        val noPieceOnG4 = newPosition.at(Board.g4)
        assertTrue(noPieceOnG4.isEmpty)
        val noPieceOnG3 = newPosition.at(Board.g3)
        assertTrue(noPieceOnG3.isEmpty)
        val noPieceOnF4 = newPosition.at(Board.f4)
        assertTrue(noPieceOnF4.isEmpty)
        val blackPawn = newPosition.at(Board.f3)
        assertTrue(blackPawn.isDefined)
        assertEquals(Piece(Type.PAWN, Color.BLACK), blackPawn.get())
    }

    @Test
    fun promotionTest() {
        val position = Positions.fromText(
            " | | | | | | | ",
            " |P| | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val promotion = Move.Regular(Board.b7, Board.b8, promotion = Option.some(Type.QUEEN))
        val newPosition = promotion.apply(position)

        val noPieceOnB7 = newPosition.at(Board.b7)
        assertTrue(noPieceOnB7.isEmpty)
        val whiteQueen = newPosition.at(Board.b8)
        assertTrue(whiteQueen.isDefined)
        assertEquals(Piece(Type.QUEEN, Color.WHITE), whiteQueen.get())
    }

    @Test
    fun regularTest() {
        val position = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p|p|p|p|p|p|p",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "P|P|P|P|P|P|P|P",
            "R|N|B|Q|K|B|N|R")
        val firstMove = Move.Regular(Board.e2, Board.e4)
        val newPosition = firstMove.apply(position)

        val noPieceOnE2 = newPosition.at(Board.e2)
        assertTrue(noPieceOnE2.isEmpty)
        val whitePawn = newPosition.at(Board.e4)
        assertTrue(whitePawn.isDefined)
        assertEquals(Piece(Type.PAWN, Color.WHITE), whitePawn.get())
    }

    @Test
    fun captureTest() {
        val position = Positions.fromText(
            "r| |b|q|k|b|n|r",
            " |p|p|p| |p|p|p",
            "p| |n| | | | | ",
            " |B| | |p| | | ",
            " | | | |P| | | ",
            " | | | | |N| | ",
            "P|P|P|P| |P|P|P",
            "R|N|B|Q|K| | |R")
        val bishopTakesKnight = Move.Regular(Board.b5, Board.c6)
        val newPosition = bishopTakesKnight.apply(position)

        val noPieceOnB5 = newPosition.at(Board.b5)
        assertTrue(noPieceOnB5.isEmpty)
        val whiteBishop = newPosition.at(Board.c6)
        assertTrue(whiteBishop.isDefined)
        assertEquals(Piece(Type.BISHOP, Color.WHITE), whiteBishop.get())
    }

    @Test
    fun toStringTest() {
        val regular = Move.Regular(Board.b5, Board.c6)
        assertTrue(regular.toString().startsWith("Regular"))
        val shortCastling = Move.ShortCastling(Color.WHITE)
        assertTrue(shortCastling.toString().startsWith("ShortCastling"))
        val longCastling = Move.LongCastling(Color.BLACK)
        assertTrue(longCastling.toString().startsWith("LongCastling"))
    }
}
