package io.chesslave.model;

import static org.junit.Assert.assertEquals;

import javaslang.collection.List;
import org.junit.Test;

public class GameTest {

    @Test
    public void positionTest() {
        final Game game = Game.initialPosition()
                .move(Movements.regular(Square.of("e2"), Square.of("e4")))
                .move(Movements.regular(Square.of("c7"), Square.of("c5")))
                .move(Movements.regular(Square.of("g1"), Square.of("f3")))
                .move(Movements.regular(Square.of("d7"), Square.of("d6")))
                .move(Movements.regular(Square.of("d2"), Square.of("d4")))
                .move(Movements.regular(Square.of("c5"), Square.of("d4")));
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
    }

    @Test
    public void turnTest() {
        Game game = Game.initialPosition();
        assertEquals(Color.WHITE, game.turn());

        game = game.move(Movements.regular(Square.of("e2"), Square.of("e4")));
        assertEquals(Color.BLACK, game.turn());
    }

    @Test
    public void movesTest() {
        Game game = Game.initialPosition();
        final List<Move> initMoves = game.moves();
        assertEquals(List.empty(), initMoves);

        final Move firstMove = Movements.regular(Square.of("e2"), Square.of("e4"));
        game = game.move(firstMove);
        final List<Move> oneMove = game.moves();
        assertEquals(List.of(firstMove), oneMove);

        final Move secondMove = Movements.regular(Square.of("e7"), Square.of("e5"));
        game = game.move(secondMove);
        final List<Move> twoMoves = game.moves();
        assertEquals(List.of(firstMove, secondMove), twoMoves);
    }
}
