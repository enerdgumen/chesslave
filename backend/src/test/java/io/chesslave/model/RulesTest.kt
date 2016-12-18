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
        val got = Rules.moves(position, Square.of("c2")).map { it.to }
        val expected = HashSet.of(Square.of("c3"), Square.of("c4"), Square.of("b3"))
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
        val got = Rules.moves(position, Square.of("c7")).map { it.to }
        val expected = HashSet.of(Square.of("c6"), Square.of("c5"), Square.of("b6"))
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
        val got = Rules.moves(position, Square.of("c4")).map { it.to }
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
        val got = Rules.moves(position, Square.of("d5")).map { it.to }
        val expected = HashSet.of(Square.of("d6"), Square.of("c6"))
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
        val got = Rules.moves(position, Square.of("c4")).map { it.to }
        val expected = HashSet.of(Square.of("c3"), Square.of("d3"))
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
        val got = Rules.moves(position, Square.of("f2")).map { it.to }
        val expected = HashSet.of(Square.of("f3"))
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
        val got = Rules.attackingPawnSquares(Square.of("d5"), Color.BLACK, position)
        val expected = HashSet.of(Square.of("e6"))
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
        val got = Rules.attackingPawnSquares(Square.of("d4"), Color.BLACK, position)
        val expected = HashSet.of(Square.of("c4"))
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
        val got = Rules.moves(position, Square.of("e4")).map { it.to }
        val expected = HashSet.of(
            Square.of("e5"), Square.of("f5"), Square.of("f4"), Square.of("f3"),
            Square.of("e3"), Square.of("d3"), Square.of("d4"), Square.of("d5"))
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
        val got = Rules.moves(position, Square.of("e8")).map { it.to }
        val expected = HashSet.of(Square.of("f8"), Square.of("f7"), Square.of("e7"), Square.of("d7"), Square.of("d8"))
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
        val got = Rules.moves(position, Square.of("e8")).map { it.to }
        val expected = HashSet.of(Square.of("f7"), Square.of("d7"))
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
        val got = Rules.moves(position, Square.of("f8")).map { it.to }
        val expected = HashSet.of(Square.of("e7"))
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
        val got = Rules.moves(position, Square.of("e8")).map { it.to }
        val expected = HashSet.of(Square.of("d7"), Square.of("d8"))
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
        val got = Rules.moves(position, Square.of("f8")).map { it.to }
        val expected = HashSet.of(Square.of("e8"), Square.of("g8"))
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
        val got = Rules.moves(position, Square.of("e8")).map { it.to }
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
        val got = Rules.moves(position, Square.of("f8")).map { it.to }
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
        val got = Rules.moves(position, Square.of("e4")).map { it.to }
        val expected = HashSet.of(
            Square.of("f6"), Square.of("g5"), Square.of("g3"), Square.of("f2"),
            Square.of("d2"), Square.of("c3"), Square.of("c5"), Square.of("d6"))
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
        val got = Rules.moves(position, Square.of("e4")).map { it.to }
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
        val got = Rules.moves(position, Square.of("c4")).map { it.to }
        val expected = HashSet.of(Square.of("e5"), Square.of("e3"))
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
        val got = Rules.moves(position, Square.of("h1")).map { it.to }
        val expected = HashSet.of(Square.of("f2"), Square.of("g3"))
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
        val got = Rules.moves(position, Square.of("e4")).map { it.to }
        val expected = HashSet.of(
            Square.of("f5"), Square.of("g6"), Square.of("f3"), Square.of("d3"),
            Square.of("c2"), Square.of("b1"), Square.of("d5"), Square.of("c6"))
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
        val got = Rules.moves(position, Square.of("e4")).map { it.to }
        val expected = HashSet.of(
            Square.of("e5"), Square.of("e6"), Square.of("e7"), Square.of("f4"),
            Square.of("g4"), Square.of("h4"), Square.of("e3"), Square.of("e2"),
            Square.of("d4"))
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
        val got = Rules.moves(position, Square.of("e4")).map { it.to }
        val expected = HashSet.of(
            Square.of("e5"), Square.of("e6"), Square.of("e7"), Square.of("f5"),
            Square.of("g6"), Square.of("f4"), Square.of("g4"), Square.of("h4"),
            Square.of("f3"), Square.of("e3"), Square.of("e2"), Square.of("d3"),
            Square.of("c2"), Square.of("b1"), Square.of("d4"), Square.of("d5"),
            Square.of("c6"))
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
            Movements.Regular(Square.of("c5"), Square.of("b4")),
            Movements.Regular(Square.of("c5"), Square.of("b5")),
            Movements.Regular(Square.of("c5"), Square.of("b6")),
            Movements.Regular(Square.of("c5"), Square.of("d4")),
            Movements.Regular(Square.of("c5"), Square.of("d5")),
            Movements.Regular(Square.of("b7"), Square.of("c8")),
            Movements.Regular(Square.of("e5"), Square.of("c6")))
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
        val moves = Rules.moves(position, Square.of("e4"))
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
        val moves = Rules.moves(position, Square.of("a2"))
        assertEquals(HashSet.empty<Any>(), moves)
    }
}