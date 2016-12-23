package io.chesslave.model

import io.chesslave.model.Piece.Type
import javaslang.Tuple
import org.hamcrest.CoreMatchers.hasItem
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class PositionTest {

    val position = Positions.fromText(
        "r|n|b|q|k|b|n|r",
        "p|p|p|p|p|p|p|p",
        " | | | | | | | ",
        " | | | | | | | ",
        " | | | | | | | ",
        " | | | | | | | ",
        "P|P|P|P|P|P|P|P",
        "R|N|B|Q|K|B|N|R")

    @Test
    fun atTest() {
        val whiteKing = position.at(Board.e1)
        assertTrue(whiteKing.isDefined)
        assertEquals(Piece(Type.KING, Color.WHITE), whiteKing.get())

        val blackQueen = position.at(Board.d8)
        assertTrue(blackQueen.isDefined)
        assertEquals(Piece(Type.QUEEN, Color.BLACK), blackQueen.get())

        val noPiece = position.at(Board.b4)
        assertTrue(noPiece.isEmpty)
    }

    @Test
    fun putTest() {
        val newPosition = position.put(Board.e4, Piece(Type.PAWN, Color.BLACK))
        val whiteKing = newPosition.at(Board.e1)
        assertTrue(whiteKing.isDefined)
        assertEquals(Piece(Type.KING, Color.WHITE), whiteKing.get())

        val blackPawn = newPosition.at(Board.e4)
        assertTrue(blackPawn.isDefined)
        assertEquals(Piece(Type.PAWN, Color.BLACK), blackPawn.get())

        val noPiece = newPosition.at(Board.g3)
        assertTrue(noPiece.isEmpty)
    }

    @Test
    fun removeTest() {
        val newPosition = position.remove(Board.a2)
        val whiteKing = newPosition.at(Board.e1)
        assertTrue(whiteKing.isDefined)
        assertEquals(Piece(Type.KING, Color.WHITE), whiteKing.get())

        val noPiece = newPosition.at(Board.a2)
        assertTrue(noPiece.isEmpty)
    }

    @Test
    fun moveTest() {
        val newPosition = position.move(Board.e2, Board.e4)
        val whiteKing = newPosition.at(Board.e1)
        assertTrue(whiteKing.isDefined)
        assertEquals(Piece(Type.KING, Color.WHITE), whiteKing.get())

        val whitePawn = newPosition.at(Board.e4)
        assertTrue(whitePawn.isDefined)
        assertEquals(Piece(Type.PAWN, Color.WHITE), whitePawn.get())

        val noPiece = newPosition.at(Board.e2)
        assertTrue(noPiece.isEmpty)
    }

    @Test
    fun moveToCaptureTest() {
        val newPosition = position.move(Board.e2, Board.e7)
        val whitePawn = newPosition.at(Board.e7)
        assertTrue(whitePawn.isDefined)
        assertEquals(Piece(Type.PAWN, Color.WHITE), whitePawn.get())

        val noPiece = newPosition.at(Board.e2)
        assertTrue(noPiece.isEmpty)
    }

    @Test(expected = NoSuchElementException::class)
    fun moveEmptySquareTest() {
        position.move(Board.e4, Board.e2)
    }

    @Test
    fun toSetTest() {
        val positionSet = position.toSet()
        assertEquals(32, positionSet.size().toLong())
        assertThat(positionSet, hasItem(Tuple.of(Board.a1, Piece(Type.ROOK, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.b1, Piece(Type.KNIGHT, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.c1, Piece(Type.BISHOP, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.d1, Piece(Type.QUEEN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.e1, Piece(Type.KING, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.f1, Piece(Type.BISHOP, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.g1, Piece(Type.KNIGHT, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.h1, Piece(Type.ROOK, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.a2, Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.b2, Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.c2, Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.d2, Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.e2, Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.f2, Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.g2, Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Board.h2, Piece(Type.PAWN, Color.WHITE))))

        assertThat(positionSet, hasItem(Tuple.of(Board.a8, Piece(Type.ROOK, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.b8, Piece(Type.KNIGHT, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.c8, Piece(Type.BISHOP, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.d8, Piece(Type.QUEEN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.e8, Piece(Type.KING, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.f8, Piece(Type.BISHOP, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.g8, Piece(Type.KNIGHT, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.h8, Piece(Type.ROOK, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.a7, Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.b7, Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.c7, Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.d7, Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.e7, Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.f7, Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.g7, Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Board.h7, Piece(Type.PAWN, Color.BLACK))))
    }

    @Test
    fun equalsTest() {
        assertTrue(position == position)
        assertFalse(position == Any())
        val samePosition = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p|p|p|p|p|p|p",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "P|P|P|P|P|P|P|P",
            "R|N|B|Q|K|B|N|R")
        assertTrue(position == samePosition)
    }

    @Test
    fun hashCodeTest() {
        val startPositionHash = position.hashCode()
        assertEquals(startPositionHash.toLong(), position.hashCode().toLong())
        assertNotEquals(startPositionHash.toLong(), Any().hashCode().toLong())
        val samePosition = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p|p|p|p|p|p|p",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "P|P|P|P|P|P|P|P",
            "R|N|B|Q|K|B|N|R")
        assertEquals(startPositionHash.toLong(), samePosition.hashCode().toLong())
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
        assertEquals(expected, position.toString())
    }
}