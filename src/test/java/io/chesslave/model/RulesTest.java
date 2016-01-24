package io.chesslave.model;

import io.chesslave.model.Moviment.Regular;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import org.junit.Assert;
import org.junit.Test;

public class RulesTest {

    Square target(Regular move) {
        return move.to;
    }

     /*
     * Pawn behaviour
     */

    @Test
    public void whitePawnMoves() {
        final Position position = Position.of(
                " | | | |k| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | |*| | | | | ",
                " |r|*|N| | | | ",
                " | |P| | | | | ",
                " | | | |K| | | ");
        final Set<Square> got = Rules.moves(position, Square.of("c2")).map(this::target);
        final Set<Square> expected = HashSet.of(Square.of("c3"), Square.of("c4"), Square.of("b3"));
        Assert.assertEquals(expected, got);
    }

    @Test
    public void blackPawnMoves() {
        final Position position = Position.of(
                " | | | |k| | | ",
                " | |p| | | | | ",
                " |R|*|n| | | | ",
                " | |*| | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | |K| | | ");
        final Set<Square> got = Rules.moves(position, Square.of("c7")).map(this::target);
        final Set<Square> expected = HashSet.of(Square.of("c6"), Square.of("c5"), Square.of("b6"));
        Assert.assertEquals(expected, got);
    }

    @Test
    public void lockedPawn() {
        final Position position = Position.of(
                " | | | |k| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | |p| | | | | ",
                " | |P| | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | |K| | | ");
        final Set<Square> got = Rules.moves(position, Square.of("c4")).map(this::target);
        final Set<Square> expected = HashSet.empty();
        Assert.assertEquals(expected, got);
    }

    @Test
    public void whiteEnPassant() {
        final Position position = Position.of(
                " | | | |k| | | ",
                " | | | | | | | ",
                " | |*|*| | | | ",
                " | |p|P|b| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | |K| | | ");
        final Set<Square> got = Rules.moves(position, Square.of("d5")).map(this::target);
        final Set<Square> expected = HashSet.of(Square.of("d6"), Square.of("c6"));
        Assert.assertEquals(expected, got);
    }

    /*
     * King behaviour
     */

    @Test
    public void kingMoves() {
        final Position position = Position.of(
                " | | | |k| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | |*|*|*| | ",
                " | | |*|K|*| | ",
                " | | |*|*|*| | ",
                " | | | | | | | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.moves(position, Square.of("e4")).map(this::target);
        final Set<Square> expected = HashSet.of(
                Square.of("e5"), Square.of("f5"), Square.of("f4"), Square.of("f3"),
                Square.of("e3"), Square.of("d3"), Square.of("d4"), Square.of("d5"));
        Assert.assertEquals(expected, got);
    }

    @Test
    public void kingCannotMoveOutOfTheBoard() {
        final Position position = Position.of(
                " | | |*|k|*| | ",
                " | | |*|*|*| | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | |K| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.moves(position, Square.of("e8")).map(this::target);
        final Set<Square> expected = HashSet.of(Square.of("f8"), Square.of("f7"), Square.of("e7"), Square.of("d7"), Square.of("d8"));
        Assert.assertEquals(expected, got);
    }

    @Test
    public void kingCannotMoveInOpponentSquares() {
        final Position position = Position.of(
                " | | | |k| | | ",
                " | | |*| |N| | ",
                " | | |B| | | | ",
                " | | | | | | | ",
                " | | | |K| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.moves(position, Square.of("e8")).map(this::target);
        final Set<Square> expected = HashSet.of(Square.of("f7"), Square.of("d7"));
        Assert.assertEquals(expected, got);
    }

    @Test
    public void kingCannotMoveInSquaresFilledWithFriends() {
        final Position position = Position.of(
                " | | |*|k| | | ",
                " | | |*| |p| | ",
                " | | |B| | | | ",
                " | | | | | | | ",
                " | | | |K| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.moves(position, Square.of("e8")).map(this::target);
        final Set<Square> expected = HashSet.of(Square.of("d7"), Square.of("d8"));
        Assert.assertEquals(expected, got);
    }

    @Test
    public void checkmate() {
        final Position position = Position.of(
                " |R| | |k| | | ",
                "R| | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | |K| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.moves(position, Square.of("e8")).map(this::target);
        final Set<Square> expected = HashSet.empty();
        Assert.assertEquals(expected, got);
    }

    /*
     * Knight behaviour
     */

    @Test
    public void knightMoves() {
        final Position position = Position.of(
                " | | | |k| | | ",
                " | | | | | | | ",
                " | | |*| |*| | ",
                " | |*| | | |*| ",
                " | | | |N| | | ",
                " | |*| | | |*| ",
                " | | |*| |*| | ",
                " | | | |K| | | ");
        final Set<Square> got = Rules.moves(position, Square.of("e4")).map(this::target);
        final Set<Square> expected = HashSet.of(
                Square.of("f6"), Square.of("g5"), Square.of("g3"), Square.of("f2"),
                Square.of("d2"), Square.of("c3"), Square.of("c5"), Square.of("d6"));
        Assert.assertEquals(expected, got);
    }

    @Test
    public void lockedKnightHasNoMoves() {
        final Position position = Position.of(
                " | | | |k| | | ",
                " | | | | | | | ",
                " | | | |r| | | ",
                " | | | | | | | ",
                " | | | |N| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | |K| | | ");
        final Set<Square> got = Rules.moves(position, Square.of("e4")).map(this::target);
        final Set<Square> expected = HashSet.empty();
        Assert.assertEquals(expected, got);
    }

    @Test
    public void knightMustDefendKingIfCheck() {
        final Position position = Position.of(
                " | | | |k| | | ",
                " | | | | | | | ",
                " | | | |r| | | ",
                " | | | |*| | | ",
                " | |N| | | | | ",
                " | | | |*| | | ",
                " | | | | | | | ",
                " | | | |K| | | ");
        final Set<Square> got = Rules.moves(position, Square.of("c4")).map(this::target);
        final Set<Square> expected = HashSet.of(Square.of("e5"), Square.of("e3"));
        Assert.assertEquals(expected, got);
    }

    /*
     * Bishop behaviour
     */

    @Test
    public void bishopMoves() {
        final Position position = Position.of(
                " | | | |k| | | ",
                " | | | | | | |N",
                " | |n| | | |*| ",
                " | | |*| |*| | ",
                " | | | |B| | | ",
                " | | |*| |*| | ",
                " | |*| | | |P| ",
                " |*| | |K| | | ");
        final Set<Square> got = Rules.moves(position, Square.of("e4")).map(this::target);
        final Set<Square> expected = HashSet.of(
                Square.of("f5"), Square.of("g6"), Square.of("f3"), Square.of("d3"),
                Square.of("c2"), Square.of("b1"), Square.of("d5"), Square.of("c6"));
        Assert.assertEquals(expected, got);
    }

    /*
     * Rook behaviour
     */

    @Test
    public void rookMoves() {
        final Position position = Position.of(
                " | | | |k| | | ",
                " | | | |p| | |N",
                " | | | |*| | | ",
                " | | | |*| | | ",
                " | |N|*|R|*|*|*",
                " | | | |*| | | ",
                " | | | |*| | | ",
                " | | | |K| | | ");
        final Set<Square> got = Rules.moves(position, Square.of("e4")).map(this::target);
        final Set<Square> expected = HashSet.of(
                Square.of("e5"), Square.of("e6"), Square.of("e7"), Square.of("f4"),
                Square.of("g4"), Square.of("h4"), Square.of("e3"), Square.of("e2"),
                Square.of("d4"));
        Assert.assertEquals(expected, got);
    }

    /*
     * Queen behaviour
     */

    @Test
    public void queenMoves() {
        final Position position = Position.of(
                " | | | |k| | | ",
                " | | | |p| | |N",
                " | |n| |*| |*| ",
                " | | |*|*|*| | ",
                " | |N|*|Q|*|*|*",
                " | | |*|*|*| | ",
                " | |*| |*| |P| ",
                " |*| | |K| | | ");
        final Set<Square> got = Rules.moves(position, Square.of("e4")).map(this::target);
        final Set<Square> expected = HashSet.of(
                Square.of("e5"), Square.of("e6"), Square.of("e7"), Square.of("f5"),
                Square.of("g6"), Square.of("f4"), Square.of("g4"), Square.of("h4"),
                Square.of("f3"), Square.of("e3"), Square.of("e2"), Square.of("d3"),
                Square.of("c2"), Square.of("b1"), Square.of("d4"), Square.of("d5"),
                Square.of("c6"));
        Assert.assertEquals(expected, got);
    }
}