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
        val whiteKing = position.at(Square.of("e1"))
        assertTrue(whiteKing.isDefined)
        assertEquals(Piece(Type.KING, Color.WHITE), whiteKing.get())

        val blackQueen = position.at(Square.of("d8"))
        assertTrue(blackQueen.isDefined)
        assertEquals(Piece(Type.QUEEN, Color.BLACK), blackQueen.get())

        val noPiece = position.at(Square.of("b4"))
        assertTrue(noPiece.isEmpty)
    }

    @Test
    fun putTest() {
        val newPosition = position.put(Square.of("e4"), Piece(Type.PAWN, Color.BLACK))
        val whiteKing = newPosition.at(Square.of("e1"))
        assertTrue(whiteKing.isDefined)
        assertEquals(Piece(Type.KING, Color.WHITE), whiteKing.get())

        val blackPawn = newPosition.at(Square.of("e4"))
        assertTrue(blackPawn.isDefined)
        assertEquals(Piece(Type.PAWN, Color.BLACK), blackPawn.get())

        val noPiece = newPosition.at(Square.of("g3"))
        assertTrue(noPiece.isEmpty)
    }

    @Test
    fun removeTest() {
        val newPosition = position.remove(Square.of("a2"))
        val whiteKing = newPosition.at(Square.of("e1"))
        assertTrue(whiteKing.isDefined)
        assertEquals(Piece(Type.KING, Color.WHITE), whiteKing.get())

        val noPiece = newPosition.at(Square.of("a2"))
        assertTrue(noPiece.isEmpty)
    }

    @Test
    fun moveTest() {
        val newPosition = position.move(Square.of("e2"), Square.of("e4"))
        val whiteKing = newPosition.at(Square.of("e1"))
        assertTrue(whiteKing.isDefined)
        assertEquals(Piece(Type.KING, Color.WHITE), whiteKing.get())

        val whitePawn = newPosition.at(Square.of("e4"))
        assertTrue(whitePawn.isDefined)
        assertEquals(Piece(Type.PAWN, Color.WHITE), whitePawn.get())

        val noPiece = newPosition.at(Square.of("e2"))
        assertTrue(noPiece.isEmpty)
    }

    @Test
    fun moveToCaptureTest() {
        val newPosition = position.move(Square.of("e2"), Square.of("e7"))
        val whitePawn = newPosition.at(Square.of("e7"))
        assertTrue(whitePawn.isDefined)
        assertEquals(Piece(Type.PAWN, Color.WHITE), whitePawn.get())

        val noPiece = newPosition.at(Square.of("e2"))
        assertTrue(noPiece.isEmpty)
    }

    @Test(expected = NoSuchElementException::class)
    fun moveEmptySquareTest() {
        position.move(Square.of("e4"), Square.of("e2"))
    }

    @Test
    fun toSetTest() {
        val positionSet = position.toSet()
        assertEquals(32, positionSet.size().toLong())
        assertThat(positionSet, hasItem(Tuple.of(Square.of("a1"), Piece(Type.ROOK, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("b1"), Piece(Type.KNIGHT, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("c1"), Piece(Type.BISHOP, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("d1"), Piece(Type.QUEEN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("e1"), Piece(Type.KING, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("f1"), Piece(Type.BISHOP, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("g1"), Piece(Type.KNIGHT, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("h1"), Piece(Type.ROOK, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("a2"), Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("b2"), Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("c2"), Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("d2"), Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("e2"), Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("f2"), Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("g2"), Piece(Type.PAWN, Color.WHITE))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("h2"), Piece(Type.PAWN, Color.WHITE))))

        assertThat(positionSet, hasItem(Tuple.of(Square.of("a8"), Piece(Type.ROOK, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("b8"), Piece(Type.KNIGHT, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("c8"), Piece(Type.BISHOP, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("d8"), Piece(Type.QUEEN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("e8"), Piece(Type.KING, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("f8"), Piece(Type.BISHOP, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("g8"), Piece(Type.KNIGHT, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("h8"), Piece(Type.ROOK, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("a7"), Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("b7"), Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("c7"), Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("d7"), Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("e7"), Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("f7"), Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("g7"), Piece(Type.PAWN, Color.BLACK))))
        assertThat(positionSet, hasItem(Tuple.of(Square.of("h7"), Piece(Type.PAWN, Color.BLACK))))
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