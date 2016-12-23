package io.chesslave.model

import javaslang.collection.List
import org.junit.Assert.assertEquals
import org.junit.Test

class GameTest {

    @Test
    fun positionTest() {
        val game = Game.initialPosition()
            .move(Move.Regular(Board.e2, Board.e4))
            .move(Move.Regular(Board.c7, Board.c5))
            .move(Move.Regular(Board.g1, Board.f3))
            .move(Move.Regular(Board.d7, Board.d6))
            .move(Move.Regular(Board.d2, Board.d4))
            .move(Move.Regular(Board.c5, Board.d4))
        val expectedPosition = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | | | |N| | ",
            "P|P|P| | |P|P|P",
            "R|N|B|Q|K|B| |R")
        assertEquals(expectedPosition, game.position())
    }

    @Test
    fun initialPositionTest() {
        val game = Game.initialPosition()
        val expectedPosition = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p|p|p|p|p|p|p",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "P|P|P|P|P|P|P|P",
            "R|N|B|Q|K|B|N|R")
        assertEquals(expectedPosition, game.position())
    }

    @Test
    fun turnTest() {
        var game = Game.initialPosition()
        assertEquals(Color.WHITE, game.turn())

        game = game.move(Move.Regular(Board.e2, Board.e4))
        assertEquals(Color.BLACK, game.turn())
    }

    @Test
    fun movesTest() {
        var game = Game.initialPosition()
        val initMoves = game.moves()
        assertEquals(List.empty<Any>(), initMoves)

        val firstMove = Move.Regular(Board.e2, Board.e4)
        game = game.move(firstMove)
        val oneMove = game.moves()
        assertEquals(List.of(firstMove), oneMove)

        val secondMove = Move.Regular(Board.e7, Board.e5)
        game = game.move(secondMove)
        val twoMoves = game.moves()
        assertEquals(List.of(firstMove, secondMove), twoMoves)
    }
}
