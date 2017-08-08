package io.chesslave.model

import io.chesslave.model.Piece.Type
import io.vavr.Tuple
import org.hamcrest.CoreMatchers.hasItem
import org.junit.Assert.*
import org.junit.Test
import java.util.*

internal val initialPosition = Position.Builder()
    .withPiece(Board.a1, Piece.whiteRook)
    .withPiece(Board.b1, Piece.whiteKnight)
    .withPiece(Board.c1, Piece.whiteBishop)
    .withPiece(Board.d1, Piece.whiteQueen)
    .withPiece(Board.e1, Piece.whiteKing)
    .withPiece(Board.f1, Piece.whiteBishop)
    .withPiece(Board.g1, Piece.whiteKnight)
    .withPiece(Board.h1, Piece.whiteRook)
    .withPiece(Board.a2, Piece.whitePawn)
    .withPiece(Board.b2, Piece.whitePawn)
    .withPiece(Board.c2, Piece.whitePawn)
    .withPiece(Board.d2, Piece.whitePawn)
    .withPiece(Board.e2, Piece.whitePawn)
    .withPiece(Board.f2, Piece.whitePawn)
    .withPiece(Board.g2, Piece.whitePawn)
    .withPiece(Board.h2, Piece.whitePawn)
    .withPiece(Board.a8, Piece.blackRook)
    .withPiece(Board.b8, Piece.blackKnight)
    .withPiece(Board.c8, Piece.blackBishop)
    .withPiece(Board.d8, Piece.blackQueen)
    .withPiece(Board.e8, Piece.blackKing)
    .withPiece(Board.f8, Piece.blackBishop)
    .withPiece(Board.g8, Piece.blackKnight)
    .withPiece(Board.h8, Piece.blackRook)
    .withPiece(Board.a7, Piece.blackPawn)
    .withPiece(Board.b7, Piece.blackPawn)
    .withPiece(Board.c7, Piece.blackPawn)
    .withPiece(Board.d7, Piece.blackPawn)
    .withPiece(Board.e7, Piece.blackPawn)
    .withPiece(Board.f7, Piece.blackPawn)
    .withPiece(Board.g7, Piece.blackPawn)
    .withPiece(Board.h7, Piece.blackPawn)
    .build()

class PositionTest {

    @Test
    fun atTest() {
        val whiteKing = initialPosition.at(Board.e1)
        assertTrue(whiteKing != null)
        assertEquals(Piece.whiteKing, whiteKing)

        val blackQueen = initialPosition.at(Board.d8)
        assertTrue(blackQueen != null)
        assertEquals(Piece.blackQueen, blackQueen)

        val noPiece = initialPosition.at(Board.b4)
        assertTrue(noPiece == null)
    }

    @Test
    fun putTest() {
        val newPosition = initialPosition.put(Board.e4, Piece.blackPawn)
        val whiteKing = newPosition.at(Board.e1)
        assertTrue(whiteKing != null)
        assertEquals(Piece.whiteKing, whiteKing)

        val blackPawn = newPosition.at(Board.e4)
        assertTrue(blackPawn != null)
        assertEquals(Piece.blackPawn, blackPawn)

        val noPiece = newPosition.at(Board.g3)
        assertTrue(noPiece == null)
    }

    @Test
    fun removeTest() {
        val newPosition = initialPosition.remove(Board.a2)
        val whiteKing = newPosition.at(Board.e1)
        assertTrue(whiteKing != null)
        assertEquals(Piece.whiteKing, whiteKing)

        val noPiece = newPosition.at(Board.a2)
        assertTrue(noPiece == null)
    }

    @Test
    fun moveTest() {
        val newPosition = initialPosition.move(Board.e2, Board.e4)
        val whiteKing = newPosition.at(Board.e1)
        assertTrue(whiteKing != null)
        assertEquals(Piece.whiteKing, whiteKing)

        val whitePawn = newPosition.at(Board.e4)
        assertTrue(whitePawn != null)
        assertEquals(Piece.whitePawn, whitePawn)

        val noPiece = newPosition.at(Board.e2)
        assertTrue(noPiece == null)
    }

    @Test
    fun moveToCaptureTest() {
        val newPosition = initialPosition.move(Board.e2, Board.e7)
        val whitePawn = newPosition.at(Board.e7)
        assertTrue(whitePawn != null)
        assertEquals(Piece.whitePawn, whitePawn)

        val noPiece = newPosition.at(Board.e2)
        assertTrue(noPiece == null)
    }

    @Test(expected = NoSuchElementException::class)
    fun moveEmptySquareTest() {
        initialPosition.move(Board.e4, Board.e2)
    }

    @Test
    fun toSetTest() {
        val positionSet = initialPosition.toSet()
        assertEquals(32, positionSet.size().toLong())
        assertThat(positionSet, hasItem(Tuple.of(Board.a1, Piece.whiteRook)))
        assertThat(positionSet, hasItem(Tuple.of(Board.b1, Piece.whiteKnight)))
        assertThat(positionSet, hasItem(Tuple.of(Board.c1, Piece.whiteBishop)))
        assertThat(positionSet, hasItem(Tuple.of(Board.d1, Piece.whiteQueen)))
        assertThat(positionSet, hasItem(Tuple.of(Board.e1, Piece.whiteKing)))
        assertThat(positionSet, hasItem(Tuple.of(Board.f1, Piece.whiteBishop)))
        assertThat(positionSet, hasItem(Tuple.of(Board.g1, Piece.whiteKnight)))
        assertThat(positionSet, hasItem(Tuple.of(Board.h1, Piece.whiteRook)))
        assertThat(positionSet, hasItem(Tuple.of(Board.a2, Piece.whitePawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.b2, Piece.whitePawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.c2, Piece.whitePawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.d2, Piece.whitePawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.e2, Piece.whitePawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.f2, Piece.whitePawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.g2, Piece.whitePawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.h2, Piece.whitePawn)))

        assertThat(positionSet, hasItem(Tuple.of(Board.a8, Piece.blackRook)))
        assertThat(positionSet, hasItem(Tuple.of(Board.b8, Piece.blackKnight)))
        assertThat(positionSet, hasItem(Tuple.of(Board.c8, Piece.blackBishop)))
        assertThat(positionSet, hasItem(Tuple.of(Board.d8, Piece.blackQueen)))
        assertThat(positionSet, hasItem(Tuple.of(Board.e8, Piece.blackKing)))
        assertThat(positionSet, hasItem(Tuple.of(Board.f8, Piece.blackBishop)))
        assertThat(positionSet, hasItem(Tuple.of(Board.g8, Piece.blackKnight)))
        assertThat(positionSet, hasItem(Tuple.of(Board.h8, Piece.blackRook)))
        assertThat(positionSet, hasItem(Tuple.of(Board.a7, Piece.blackPawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.b7, Piece.blackPawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.c7, Piece.blackPawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.d7, Piece.blackPawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.e7, Piece.blackPawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.f7, Piece.blackPawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.g7, Piece.blackPawn)))
        assertThat(positionSet, hasItem(Tuple.of(Board.h7, Piece.blackPawn)))
    }

    @Test
    fun equalsTest() {
        assertEquals(initialPosition, initialPosition)
        assertNotEquals(initialPosition, Any())
        val samePosition = positionFromText(
            "r|n|b|q|k|b|n|r",
            "p|p|p|p|p|p|p|p",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "P|P|P|P|P|P|P|P",
            "R|N|B|Q|K|B|N|R")
        assertEquals(initialPosition, samePosition)
    }

    @Test
    fun hashCodeTest() {
        val startPositionHash = initialPosition.hashCode()
        assertEquals(startPositionHash, initialPosition.hashCode())
        assertNotEquals(startPositionHash, Any().hashCode())
        val samePosition = positionFromText(
            "r|n|b|q|k|b|n|r",
            "p|p|p|p|p|p|p|p",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "P|P|P|P|P|P|P|P",
            "R|N|B|Q|K|B|N|R")
        assertEquals(startPositionHash, samePosition.hashCode())
    }

    @Test
    fun toStringTest() {
        val expected = "" +
            "r|n|b|q|k|b|n|r\n" +
            "p|p|p|p|p|p|p|p\n" +
            " | | | | | | | \n" +
            " | | | | | | | \n" +
            " | | | | | | | \n" +
            " | | | | | | | \n" +
            "P|P|P|P|P|P|P|P\n" +
            "R|N|B|Q|K|B|N|R"
        assertEquals(expected, initialPosition.toString())
    }
}

class positionFromTextTest {

    @Test
    fun canCreatePositionFromText() {
        val got = positionFromText(
            "r|n|b|q|k|b|n|r",
            "p|p|p|p|p|p|p|p",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "P|P|P|P|P|P|P|P",
            "R|N|B|Q|K|B|N|R")
        assertEquals(initialPosition, got)
    }
}

class positionToTextTest {

    @Test
    fun canRenderPositionToText() {
        val got = positionToText(initialPosition)
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