package io.chesslave.model

import javaslang.collection.List
import org.junit.Assert.assertEquals
import org.junit.Test

class GameTest {

    @Test
    fun positionTest() {
        val game = Game.initialPosition()
            .move(Movements.Regular(Square.of("e2"), Square.of("e4")))
            .move(Movements.Regular(Square.of("c7"), Square.of("c5")))
            .move(Movements.Regular(Square.of("g1"), Square.of("f3")))
            .move(Movements.Regular(Square.of("d7"), Square.of("d6")))
            .move(Movements.Regular(Square.of("d2"), Square.of("d4")))
            .move(Movements.Regular(Square.of("c5"), Square.of("d4")))
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

        game = game.move(Movements.Regular(Square.of("e2"), Square.of("e4")))
        assertEquals(Color.BLACK, game.turn())
    }

    @Test
    fun movesTest() {
        var game = Game.initialPosition()
        val initMoves = game.moves()
        assertEquals(List.empty<Any>(), initMoves)

        val firstMove = Movements.Regular(Square.of("e2"), Square.of("e4"))
        game = game.move(firstMove)
        val oneMove = game.moves()
        assertEquals(List.of(firstMove), oneMove)

        val secondMove = Movements.Regular(Square.of("e7"), Square.of("e5"))
        game = game.move(secondMove)
        val twoMoves = game.moves()
        assertEquals(List.of(firstMove, secondMove), twoMoves)
    }
}
