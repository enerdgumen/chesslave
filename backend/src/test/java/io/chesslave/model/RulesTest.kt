package io.chesslave.model

import io.vavr.collection.HashSet
import org.junit.Assert.assertEquals
import org.junit.Test

class movesTest {

    /*
     * Pawn behaviour
     */

    @Test
    fun whitePawnMoves() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | |*| | | | | ",
            " |r|*|N| | | | ",
            " | |P| | | | | ",
            " | | | |K| | | ")
        val got = position.moves(Board.c2).map { it.to }
        val expected = HashSet.of(Board.c3, Board.c4, Board.b3)
        assertEquals(expected, got)
    }

    @Test
    fun blackPawnMoves() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | |p| | | | | ",
            " |R|*|n| | | | ",
            " | |*| | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val got = position.moves(Board.c7).map { it.to }
        val expected = HashSet.of(Board.c6, Board.c5, Board.b6)
        assertEquals(expected, got)
    }

    @Test
    fun lockedPawn() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | |p| | | | | ",
            " | |P| | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val got = position.moves(Board.c4).map { it.to }
        val expected = HashSet.empty<Square>()
        assertEquals(expected, got)
    }

    @Test
    fun whiteEnPassant() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | |*|*| | | | ",
            " | |p|P|b| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val got = position.moves(Board.d5).map { it.to }
        val expected = HashSet.of(Board.d6, Board.c6)
        assertEquals(expected, got)
    }

    @Test
    fun blackEnPassant() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |b| | | ",
            " | |p|P| | | | ",
            " | |*|*| | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val got = position.moves(Board.c4).map { it.to }
        val expected = HashSet.of(Board.c3, Board.d3)
        assertEquals(expected, got)
    }

    @Test
    fun pawnMustDefendKingIfCheck() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | |r| | |*|K| ",
            " | | | | |P| | ",
            " | | | | | | | ")
        val got = position.moves(Board.f2).map { it.to }
        val expected = HashSet.of(Board.f3)
        assertEquals(expected, got)
    }

    /*
     * King behaviour
     */

    @Test
    fun kingMoves() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | |*|*|*| | ",
            " | | |*|K|*| | ",
            " | | |*|*|*| | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = position.moves(Board.e4).map { it.to }
        val expected = HashSet.of(
            Board.e5, Board.f5, Board.f4, Board.f3,
            Board.e3, Board.d3, Board.d4, Board.d5)
        assertEquals(expected, got)
    }

    @Test
    fun kingCannotMoveOutOfTheBoard() {
        val position = positionFromText(
            " | | |*|k|*| | ",
            " | | |*|*|*| | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = position.moves(Board.e8).map { it.to }
        val expected = HashSet.of(Board.f8, Board.f7, Board.e7, Board.d7, Board.d8)
        assertEquals(expected, got)
    }

    @Test
    fun kingCannotMoveInOpponentPieceSquares() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | |*| |N| | ",
            " | | |B| | | | ",
            " | | | | | | | ",
            " | | | |K| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = position.moves(Board.e8).map { it.to }
        val expected = HashSet.of(Board.f7, Board.d7)
        assertEquals(expected, got)
    }

    @Test
    fun kingCannotMoveInOpponentPawnSquares() {
        val position = positionFromText(
            " | | | | |k| | ",
            " | | | |*|P| | ",
            " | | | | | |K| ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = position.moves(Board.f8).map { it.to }
        val expected = HashSet.of(Board.e7)
        assertEquals(expected, got)
    }

    @Test
    fun kingCannotMoveInSquaresFilledWithFriends() {
        val position = positionFromText(
            " | | |*|k| | | ",
            " | | |*| |p| | ",
            " | | |B| | | | ",
            " | | | | | | | ",
            " | | | |K| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = position.moves(Board.e8).map { it.to }
        val expected = HashSet.of(Board.d7, Board.d8)
        assertEquals(expected, got)
    }

    @Test
    fun kingsOpposition() {
        val position = positionFromText(
            " | | | |*|k|*| ",
            " | | | | | | | ",
            " | | | | |K| | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | |P",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = position.moves(Board.f8).map { it.to }
        val expected = HashSet.of(Board.e8, Board.g8)
        assertEquals(expected, got)
    }

    @Test
    fun checkmate() {
        val position = positionFromText(
            " |R| | |k| | | ",
            "R| | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = position.moves(Board.e8).map { it.to }
        val expected = HashSet.empty<Square>()
        assertEquals(expected, got)
    }

    @Test
    fun stalemate() {
        val position = positionFromText(
            " | | | | |k| | ",
            " | | | | |P| | ",
            " | | | | |K| | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = position.moves(Board.f8).map { it.to }
        val expected = HashSet.empty<Square>()
        assertEquals(expected, got)
    }

    /*
     * Knight behaviour
     */

    @Test
    fun knightMoves() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | |*| |*| | ",
            " | |*| | | |*| ",
            " | | | |N| | | ",
            " | |*| | | |*| ",
            " | | |*| |*| | ",
            " | | | |K| | | ")
        val got = position.moves(Board.e4).map { it.to }
        val expected = HashSet.of(
            Board.f6, Board.g5, Board.g3, Board.f2,
            Board.d2, Board.c3, Board.c5, Board.d6)
        assertEquals(expected, got)
    }

    @Test
    fun lockedKnightHasNoMoves() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | |r| | | ",
            " | | | | | | | ",
            " | | | |N| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val got = position.moves(Board.e4).map { it.to }
        val expected = HashSet.empty<Square>()
        assertEquals(expected, got)
    }

    @Test
    fun knightMustDefendKingIfCheck() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | |r| | | ",
            " | | | |*| | | ",
            " | |N| | | | | ",
            " | | | |*| | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val got = position.moves(Board.c4).map { it.to }
        val expected = HashSet.of(Board.e5, Board.e3)
        assertEquals(expected, got)
    }

    @Test
    fun knightCannotMoveOutOfTheBoard() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | |N")
        val got = position.moves(Board.h1).map { it.to }
        val expected = HashSet.of(Board.f2, Board.g3)
        assertEquals(expected, got)
    }

    /*
     * Bishop behaviour
     */

    @Test
    fun bishopMoves() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | |N",
            " | |n| | | |*| ",
            " | | |*| |*| | ",
            " | | | |B| | | ",
            " | | |*| |*| | ",
            " | |*| | | |P| ",
            " |*| | |K| | | ")
        val got = position.moves(Board.e4).map { it.to }
        val expected = HashSet.of(
            Board.f5, Board.g6, Board.f3, Board.d3,
            Board.c2, Board.b1, Board.d5, Board.c6)
        assertEquals(expected, got)
    }

    /*
     * Rook behaviour
     */

    @Test
    fun rookMoves() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | |p| | |N",
            " | | | |*| | | ",
            " | | | |*| | | ",
            " | |N|*|R|*|*|*",
            " | | | |*| | | ",
            " | | | |*| | | ",
            " | | | |K| | | ")
        val got = position.moves(Board.e4).map { it.to }
        val expected = HashSet.of(
            Board.e5, Board.e6, Board.e7, Board.f4,
            Board.g4, Board.h4, Board.e3, Board.e2,
            Board.d4)
        assertEquals(expected, got)
    }

    /*
     * Queen behaviour
     */

    @Test
    fun queenMoves() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | |p| | |N",
            " | |n| |*| |*| ",
            " | | |*|*|*| | ",
            " | |N|*|Q|*|*|*",
            " | | |*|*|*| | ",
            " | |*| |*| |P| ",
            " |*| | |K| | | ")
        val got = position.moves(Board.e4).map { it.to }
        val expected = HashSet.of(
            Board.e5, Board.e6, Board.e7, Board.f5,
            Board.g6, Board.f4, Board.g4, Board.h4,
            Board.f3, Board.e3, Board.e2, Board.d3,
            Board.c2, Board.b1, Board.d4, Board.d5,
            Board.c6)
        assertEquals(expected, got)
    }

    /*
     * Corner cases
     */

    @Test
    fun noMovesIfPieceCannotSolveCheck() {
        val position = positionFromText(
            " | |r| | | | | ",
            " | | |k| | | | ",
            " | | | | | | | ",
            " | |K| | | | | ",
            " | | | |N| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val moves = position.moves(Board.e4)
        assertEquals(HashSet.empty<Any>(), moves)
    }

    @Test
    fun noMovesOnEmptySquare() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val moves = position.moves(Board.a2)
        assertEquals(HashSet.empty<Any>(), moves)
    }
}

class allMovesTest {

    @Test
    fun allMoves() {
        val position = positionFromText(
            " | |r| | | | | ",
            " |P| |k| | | | ",
            " | | | | | | | ",
            " | |K| |N| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = position.allMoves(Color.WHITE).toSet()
        val expected = HashSet.of(
            Move.Regular(Board.c5, Board.b4),
            Move.Regular(Board.c5, Board.b5),
            Move.Regular(Board.c5, Board.b6),
            Move.Regular(Board.c5, Board.d4),
            Move.Regular(Board.c5, Board.d5),
            Move.Regular(Board.b7, Board.c8),
            Move.Regular(Board.e5, Board.c6))
        assertEquals(expected, got)
    }
}

class isTargetForColorTest {

    @Test
    fun itShouldDetectBishops() {
        val position = positionFromText(
            " | | | |k| | | ",
            " | | | | | | |*",
            " | |p| | | |*| ",
            " | | |*| |*| | ",
            " | | | |B| | | ",
            " | | |*| |*| | ",
            " | |*| | | |P| ",
            " |*| | |K| | | ")
        assertEquals(false, Board.a1.isTargetForColor(position, Color.WHITE))
        assertEquals(true, Board.b1.isTargetForColor(position, Color.WHITE))
        assertEquals(true, Board.g6.isTargetForColor(position, Color.WHITE))
        assertEquals(true, Board.c6.isTargetForColor(position, Color.WHITE))
        assertEquals(true, Board.g2.isTargetForColor(position, Color.WHITE))
        assertEquals(false, Board.h1.isTargetForColor(position, Color.WHITE))
    }
}