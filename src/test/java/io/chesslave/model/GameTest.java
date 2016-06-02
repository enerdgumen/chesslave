package io.chesslave.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {

    @Test
    public void sicilianOpening() {
        final Position expected = Position.of(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | | | | | | | ",
                " | | |p|P| | | ",
                " | | | | |N| | ",
                "P|P|P| | |P|P|P",
                "R|N|B|Q|K|B| |R");
        Position actual = Game.empty()
                .move(Moviment.regular(Square.of("e2"), Square.of("e4")))
                .move(Moviment.regular(Square.of("c7"), Square.of("c5")))
                .move(Moviment.regular(Square.of("g1"), Square.of("f3")))
                .move(Moviment.regular(Square.of("d7"), Square.of("d6")))
                .move(Moviment.regular(Square.of("d2"), Square.of("d4")))
                .move(Moviment.regular(Square.of("c5"), Square.of("d4")))
                .position();
        assertEquals(expected, actual);
    }
}
