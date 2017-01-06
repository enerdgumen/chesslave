package io.chesslave.model

import io.chesslave.model.Move.Regular.Variation.EnPassant
import io.chesslave.model.Move.Regular.Variation.Promotion
import io.chesslave.model.Piece.Type
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MoveTest {

    @Test
    fun shortCastleWhiteTest() {
        val position = positionFromText(
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
        assertTrue(noPieceOnE1 == null)
        val whiteRook = newPosition.at(Board.f1)
        assertTrue(whiteRook != null)
        assertEquals(Piece.whiteRook, whiteRook)
        val whiteKing = newPosition.at(Board.g1)
        assertTrue(whiteKing != null)
        assertEquals(Piece.whiteKing, whiteKing)
        val noPieceOnH1 = newPosition.at(Board.h1)
        assertTrue(noPieceOnH1 == null)
    }

    @Test
    fun longCastleWhiteTest() {
        val position = positionFromText(
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
        assertTrue(noPieceOnE1 == null)
        val whiteRook = newPosition.at(Board.d1)
        assertTrue(whiteRook != null)
        assertEquals(Piece.whiteRook, whiteRook)
        val whiteKing = newPosition.at(Board.c1)
        assertTrue(whiteKing != null)
        assertEquals(Piece.whiteKing, whiteKing)
        val noPieceOnB1 = newPosition.at(Board.b1)
        assertTrue(noPieceOnB1 == null)
        val noPieceOnA1 = newPosition.at(Board.a1)
        assertTrue(noPieceOnA1 == null)
    }

    @Test
    fun shortCastleBlackTest() {
        val position = positionFromText(
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
        assertTrue(noPieceOnE8 == null)
        val blackRook = newPosition.at(Board.f8)
        assertTrue(blackRook != null)
        assertEquals(Piece.blackRook, blackRook)
        val blackKing = newPosition.at(Board.g8)
        assertTrue(blackKing != null)
        assertEquals(Piece.blackKing, blackKing)
        val noPieceOnH8 = newPosition.at(Board.h8)
        assertTrue(noPieceOnH8 == null)
    }

    @Test
    fun longCastleBlackTest() {
        val position = positionFromText(
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
        assertTrue(noPieceOnE8 == null)
        val blackRook = newPosition.at(Board.d8)
        assertTrue(blackRook != null)
        assertEquals(Piece.blackRook, blackRook)
        val blackKing = newPosition.at(Board.c8)
        assertTrue(blackKing != null)
        assertEquals(Piece.blackKing, blackKing)
        val noPieceOnB8 = newPosition.at(Board.b8)
        assertTrue(noPieceOnB8 == null)
        val noPieceOnA8 = newPosition.at(Board.a8)
        assertTrue(noPieceOnA8 == null)
    }

    @Test
    fun enPassantWhiteTest() {
        val position = positionFromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | |p|P| | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val enPassant = Move.Regular(Board.d5, Board.c6, EnPassant())
        val newPosition = enPassant.apply(position)

        val noPieceOnD5 = newPosition.at(Board.d5)
        assertTrue(noPieceOnD5 == null)
        val noPieceOnD6 = newPosition.at(Board.d6)
        assertTrue(noPieceOnD6 == null)
        val noPieceOnC5 = newPosition.at(Board.c5)
        assertTrue(noPieceOnC5 == null)
        val whitePawn = newPosition.at(Board.c6)
        assertTrue(whitePawn != null)
        assertEquals(Piece.whitePawn, whitePawn)
    }

    @Test
    fun enPassantBlackTest() {
        val position = positionFromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | |P|p| ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val enPassant = Move.Regular(Board.g4, Board.f3, EnPassant())
        val newPosition = enPassant.apply(position)

        val noPieceOnG4 = newPosition.at(Board.g4)
        assertTrue(noPieceOnG4 == null)
        val noPieceOnG3 = newPosition.at(Board.g3)
        assertTrue(noPieceOnG3 == null)
        val noPieceOnF4 = newPosition.at(Board.f4)
        assertTrue(noPieceOnF4 == null)
        val blackPawn = newPosition.at(Board.f3)
        assertTrue(blackPawn != null)
        assertEquals(Piece.blackPawn, blackPawn)
    }

    @Test
    fun promotionTest() {
        val position = positionFromText(
            " | | | | | | | ",
            " |P| | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val promotion = Move.Regular(Board.b7, Board.b8, Promotion(Type.QUEEN))
        val newPosition = promotion.apply(position)

        val noPieceOnB7 = newPosition.at(Board.b7)
        assertTrue(noPieceOnB7 == null)
        val whiteQueen = newPosition.at(Board.b8)
        assertTrue(whiteQueen != null)
        assertEquals(Piece.whiteQueen, whiteQueen)
    }

    @Test
    fun regularTest() {
        val position = positionFromText(
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
        assertTrue(noPieceOnE2 == null)
        val whitePawn = newPosition.at(Board.e4)
        assertTrue(whitePawn != null)
        assertEquals(Piece.whitePawn, whitePawn)
    }

    @Test
    fun captureTest() {
        val position = positionFromText(
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
        assertTrue(noPieceOnB5 == null)
        val whiteBishop = newPosition.at(Board.c6)
        assertTrue(whiteBishop != null)
        assertEquals(Piece.whiteBishop, whiteBishop)
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
