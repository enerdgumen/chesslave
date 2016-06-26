package io.chesslave.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameTest {

    @Test
    public void sicilianOpeningTest() {
        final Game game = Game.initialPosition()
                .move(Movement.regular(Square.of("e2"), Square.of("e4")))
                .move(Movement.regular(Square.of("c7"), Square.of("c5")))
                .move(Movement.regular(Square.of("g1"), Square.of("f3")))
                .move(Movement.regular(Square.of("d7"), Square.of("d6")))
                .move(Movement.regular(Square.of("d2"), Square.of("d4")))
                .move(Movement.regular(Square.of("c5"), Square.of("d4")));
        final Position expectedPosition = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | | | | | | | ",
                " | | |p|P| | | ",
                " | | | | |N| | ",
                "P|P|P| | |P|P|P",
                "R|N|B|Q|K|B| |R");
        assertEquals(expectedPosition, game.position());
        assertEquals(Color.WHITE, game.turn());
    }

    @Test
    public void initialPositionTest() {
        final Game game = Game.initialPosition();
        final Position expectedPosition = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p|p|p|p|p|p|p",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                "P|P|P|P|P|P|P|P",
                "R|N|B|Q|K|B|N|R");
        assertEquals(expectedPosition, game.position());
        assertEquals(Color.WHITE, game.turn());
    }
}
