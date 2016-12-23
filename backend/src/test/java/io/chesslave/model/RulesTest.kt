package io.chesslave.model

import javaslang.collection.HashSet
import org.junit.Assert.assertEquals
import org.junit.Test

class RulesTest {

    /*
     * Pawn behaviour
     */

    @Test
    fun whitePawnMoves() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | |*| | | | | ",
            " |r|*|N| | | | ",
            " | |P| | | | | ",
            " | | | |K| | | ")
        val got = Rules.moves(position, Board.c2).map { it.to }
        val expected = HashSet.of(Board.c3, Board.c4, Board.b3)
        assertEquals(expected, got)
    }

    @Test
    fun blackPawnMoves() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | |p| | | | | ",
            " |R|*|n| | | | ",
            " | |*| | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val got = Rules.moves(position, Board.c7).map { it.to }
        val expected = HashSet.of(Board.c6, Board.c5, Board.b6)
        assertEquals(expected, got)
    }

    @Test
    fun lockedPawn() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | |p| | | | | ",
            " | |P| | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val got = Rules.moves(position, Board.c4).map { it.to }
        val expected = HashSet.empty<Square>()
        assertEquals(expected, got)
    }

    @Test
    fun whiteEnPassant() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | |*|*| | | | ",
            " | |p|P|b| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val got = Rules.moves(position, Board.d5).map { it.to }
        val expected = HashSet.of(Board.d6, Board.c6)
        assertEquals(expected, got)
    }

    @Test
    fun blackEnPassant() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |b| | | ",
            " | |p|P| | | | ",
            " | |*|*| | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val got = Rules.moves(position, Board.c4).map { it.to }
        val expected = HashSet.of(Board.c3, Board.d3)
        assertEquals(expected, got)
    }

    @Test
    fun pawnMustDefendKingIfCheck() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | |r| | |*|K| ",
            " | | | | |P| | ",
            " | | | | | | | ")
        val got = Rules.moves(position, Board.f2).map { it.to }
        val expected = HashSet.of(Board.f3)
        assertEquals(expected, got)
    }

    @Test
    fun attackingPawnSquares() {
        val position = Positions.fromText(
            " | | | | | | |k",
            " | | | | | | | ",
            " | | |P|p| | | ",
            " | | |*|N| | | ",
            " | |p| |P| | | ",
            " | | | | | |K| ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = Rules.attackingPawnSquares(Board.d5, Color.BLACK, position)
        val expected = HashSet.of(Board.e6)
        assertEquals(expected, got)
    }

    @Test
    fun attackingPawnEnPassantSquares() {
        val position = Positions.fromText(
            " | | | | | | |k",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | |p|P|P| | | ",
            " | | | |p| |K| ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = Rules.attackingPawnSquares(Board.d4, Color.BLACK, position)
        val expected = HashSet.of(Board.c4)
        assertEquals(expected, got)
    }

    /*
     * King behaviour
     */

    @Test
    fun kingMoves() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | |*|*|*| | ",
            " | | |*|K|*| | ",
            " | | |*|*|*| | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = Rules.moves(position, Board.e4).map { it.to }
        val expected = HashSet.of(
            Board.e5, Board.f5, Board.f4, Board.f3,
            Board.e3, Board.d3, Board.d4, Board.d5)
        assertEquals(expected, got)
    }

    @Test
    fun kingCannotMoveOutOfTheBoard() {
        val position = Positions.fromText(
            " | | |*|k|*| | ",
            " | | |*|*|*| | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = Rules.moves(position, Board.e8).map { it.to }
        val expected = HashSet.of(Board.f8, Board.f7, Board.e7, Board.d7, Board.d8)
        assertEquals(expected, got)
    }

    @Test
    fun kingCannotMoveInOpponentPieceSquares() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | |*| |N| | ",
            " | | |B| | | | ",
            " | | | | | | | ",
            " | | | |K| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = Rules.moves(position, Board.e8).map { it.to }
        val expected = HashSet.of(Board.f7, Board.d7)
        assertEquals(expected, got)
    }

    @Test
    fun kingCannotMoveInOpponentPawnSquares() {
        val position = Positions.fromText(
            " | | | | |k| | ",
            " | | | |*|P| | ",
            " | | | | | |K| ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = Rules.moves(position, Board.f8).map { it.to }
        val expected = HashSet.of(Board.e7)
        assertEquals(expected, got)
    }

    @Test
    fun kingCannotMoveInSquaresFilledWithFriends() {
        val position = Positions.fromText(
            " | | |*|k| | | ",
            " | | |*| |p| | ",
            " | | |B| | | | ",
            " | | | | | | | ",
            " | | | |K| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = Rules.moves(position, Board.e8).map { it.to }
        val expected = HashSet.of(Board.d7, Board.d8)
        assertEquals(expected, got)
    }

    @Test
    fun kingsOpposition() {
        val position = Positions.fromText(
            " | | | |*|k|*| ",
            " | | | | | | | ",
            " | | | | |K| | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | |P",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = Rules.moves(position, Board.f8).map { it.to }
        val expected = HashSet.of(Board.e8, Board.g8)
        assertEquals(expected, got)
    }

    @Test
    fun checkmate() {
        val position = Positions.fromText(
            " |R| | |k| | | ",
            "R| | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = Rules.moves(position, Board.e8).map { it.to }
        val expected = HashSet.empty<Square>()
        assertEquals(expected, got)
    }

    @Test
    fun stalemate() {
        val position = Positions.fromText(
            " | | | | |k| | ",
            " | | | | |P| | ",
            " | | | | |K| | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = Rules.moves(position, Board.f8).map { it.to }
        val expected = HashSet.empty<Square>()
        assertEquals(expected, got)
    }

    /*
     * Knight behaviour
     */

    @Test
    fun knightMoves() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | |*| |*| | ",
            " | |*| | | |*| ",
            " | | | |N| | | ",
            " | |*| | | |*| ",
            " | | |*| |*| | ",
            " | | | |K| | | ")
        val got = Rules.moves(position, Board.e4).map { it.to }
        val expected = HashSet.of(
            Board.f6, Board.g5, Board.g3, Board.f2,
            Board.d2, Board.c3, Board.c5, Board.d6)
        assertEquals(expected, got)
    }

    @Test
    fun lockedKnightHasNoMoves() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | |r| | | ",
            " | | | | | | | ",
            " | | | |N| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val got = Rules.moves(position, Board.e4).map { it.to }
        val expected = HashSet.empty<Square>()
        assertEquals(expected, got)
    }

    @Test
    fun knightMustDefendKingIfCheck() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | |r| | | ",
            " | | | |*| | | ",
            " | |N| | | | | ",
            " | | | |*| | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val got = Rules.moves(position, Board.c4).map { it.to }
        val expected = HashSet.of(Board.e5, Board.e3)
        assertEquals(expected, got)
    }

    @Test
    fun knightCannotMoveOutOfTheBoard() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | |N")
        val got = Rules.moves(position, Board.h1).map { it.to }
        val expected = HashSet.of(Board.f2, Board.g3)
        assertEquals(expected, got)
    }

    /*
     * Bishop behaviour
     */

    @Test
    fun bishopMoves() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | |N",
            " | |n| | | |*| ",
            " | | |*| |*| | ",
            " | | | |B| | | ",
            " | | |*| |*| | ",
            " | |*| | | |P| ",
            " |*| | |K| | | ")
        val got = Rules.moves(position, Board.e4).map { it.to }
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
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | |p| | |N",
            " | | | |*| | | ",
            " | | | |*| | | ",
            " | |N|*|R|*|*|*",
            " | | | |*| | | ",
            " | | | |*| | | ",
            " | | | |K| | | ")
        val got = Rules.moves(position, Board.e4).map { it.to }
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
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | |p| | |N",
            " | |n| |*| |*| ",
            " | | |*|*|*| | ",
            " | |N|*|Q|*|*|*",
            " | | |*|*|*| | ",
            " | |*| |*| |P| ",
            " |*| | |K| | | ")
        val got = Rules.moves(position, Board.e4).map { it.to }
        val expected = HashSet.of(
            Board.e5, Board.e6, Board.e7, Board.f5,
            Board.g6, Board.f4, Board.g4, Board.h4,
            Board.f3, Board.e3, Board.e2, Board.d3,
            Board.c2, Board.b1, Board.d4, Board.d5,
            Board.c6)
        assertEquals(expected, got)
    }

    /*
     * All moves
     */

    @Test
    fun allMoves() {
        val position = Positions.fromText(
            " | |r| | | | | ",
            " |P| |k| | | | ",
            " | | | | | | | ",
            " | |K| |N| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val got = Rules.allMoves(position, Color.WHITE).toSet()
        val expected = HashSet.of(
            Movements.Regular(Board.c5, Board.b4),
            Movements.Regular(Board.c5, Board.b5),
            Movements.Regular(Board.c5, Board.b6),
            Movements.Regular(Board.c5, Board.d4),
            Movements.Regular(Board.c5, Board.d5),
            Movements.Regular(Board.b7, Board.c8),
            Movements.Regular(Board.e5, Board.c6))
        assertEquals(expected, got)
    }

    /*
     * Corner cases
     */

    @Test
    fun noMovesIfPieceCannotSolveCheck() {
        val position = Positions.fromText(
            " | |r| | | | | ",
            " | | |k| | | | ",
            " | | | | | | | ",
            " | |K| | | | | ",
            " | | | |N| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        val moves = Rules.moves(position, Board.e4)
        assertEquals(HashSet.empty<Any>(), moves)
    }

    @Test
    fun noMovesOnEmptySquare() {
        val position = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val moves = Rules.moves(position, Board.a2)
        assertEquals(HashSet.empty<Any>(), moves)
    }
}