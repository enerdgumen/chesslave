package io.chesslave.model;

import static org.junit.Assert.assertEquals;

import io.chesslave.model.Movements.Regular;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import org.junit.Test;

public class RulesTest {

    private Square target(Regular move) {
        return move.to;
    }

     /*
     * Pawn behaviour
     */

    @Test
    public void whitePawnMoves() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void blackPawnMoves() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void lockedPawn() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void whiteEnPassant() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void blackEnPassant() {
        final Position position = Positions.fromText(
                " | | | |k| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | |b| | | ",
                " | |p|P| | | | ",
                " | |*|*| | | | ",
                " | | | | | | | ",
                " | | | |K| | | ");
        final Set<Square> got = Rules.moves(position, Square.of("c4")).map(this::target);
        final Set<Square> expected = HashSet.of(Square.of("c3"), Square.of("d3"));
        assertEquals(expected, got);
    }

    @Test
    public void pawnMustDefendKingIfCheck() {
        final Position position = Positions.fromText(
                " | | | |k| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | |r| | |*|K| ",
                " | | | | |P| | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.moves(position, Square.of("f2")).map(this::target);
        final Set<Square> expected = HashSet.of(Square.of("f3"));
        assertEquals(expected, got);
    }

    @Test
    public void attackingPawnSquares() {
        final Position position = Positions.fromText(
                " | | | | | | |k",
                " | | | | | | | ",
                " | | |P|p| | | ",
                " | | |*|N| | | ",
                " | |p| |P| | | ",
                " | | | | | |K| ",
                " | | | | | | | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.attackingPawnSquares(Square.of("d5"), Color.BLACK, position);
        final Set<Square> expected = HashSet.of(Square.of("e6"));
        assertEquals(expected, got);
    }

    @Test
    public void attackingPawnEnPassantSquares() {
        final Position position = Positions.fromText(
                " | | | | | | |k",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | |p|P|P| | | ",
                " | | | |p| |K| ",
                " | | | | | | | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.attackingPawnSquares(Square.of("d4"), Color.BLACK, position);
        final Set<Square> expected = HashSet.of(Square.of("c4"));
        assertEquals(expected, got);
    }

    /*
     * King behaviour
     */

    @Test
    public void kingMoves() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void kingCannotMoveOutOfTheBoard() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void kingCannotMoveInOpponentPieceSquares() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void kingCannotMoveInOpponentPawnSquares() {
        final Position position = Positions.fromText(
                " | | | | |k| | ",
                " | | | |*|P| | ",
                " | | | | | |K| ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.moves(position, Square.of("f8")).map(this::target);
        final Set<Square> expected = HashSet.of(Square.of("e7"));
        assertEquals(expected, got);
    }

    @Test
    public void kingCannotMoveInSquaresFilledWithFriends() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void kingsOpposition() {
        final Position position = Positions.fromText(
                " | | | |*|k|*| ",
                " | | | | | | | ",
                " | | | | |K| | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | |P",
                " | | | | | | | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.moves(position, Square.of("f8")).map(this::target);
        final Set<Square> expected = HashSet.of(Square.of("e8"), Square.of("g8"));
        assertEquals(expected, got);
    }

    @Test
    public void checkmate() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void stalemate() {
        final Position position = Positions.fromText(
                " | | | | |k| | ",
                " | | | | |P| | ",
                " | | | | |K| | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.moves(position, Square.of("f8")).map(this::target);
        final Set<Square> expected = HashSet.empty();
        assertEquals(expected, got);
    }

    @Test
    public void attackingKingSquares() {
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | | | | | | | ",
                " | | |P|k| | | ",
                " | | |*|N| | | ",
                " | |p| |P| | | ",
                " | | | | | |K| ",
                " | | | | | | | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.attackingKingSquares(Square.of("d5"), Color.BLACK, position);
        final Set<Square> expected = HashSet.of(Square.of("e6"));
        assertEquals(expected, got);
    }

    /*
     * Knight behaviour
     */

    @Test
    public void knightMoves() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void lockedKnightHasNoMoves() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void knightMustDefendKingIfCheck() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void knightCannotMoveOutOfTheBoard() {
        final Position position = Positions.fromText(
                " | | | |k| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | |K| | |N");
        final Set<Square> got = Rules.moves(position, Square.of("h1")).map(this::target);
        final Set<Square> expected = HashSet.of(Square.of("f2"), Square.of("g3"));
        assertEquals(expected, got);
    }

    @Test
    public void attackingKnightSquares() {
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | | | |N| | | ",
                " |n| | | | | | ",
                " | | |*|n| | | ",
                " | |p| |P| | | ",
                " | | | | | |K| ",
                " |k| | | | | | ",
                " | | | | | | | ");
        final Set<Square> got = Rules.attackingKnightSquares(Square.of("d5"), Color.BLACK, position);
        final Set<Square> expected = HashSet.of(Square.of("b6"));
        assertEquals(expected, got);
    }

    /*
     * Bishop behaviour
     */

    @Test
    public void bishopMoves() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void attackingBishopSquares() {
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | | |k|p| | | ",
                " |n| | | | | | ",
                " | | |*| | | | ",
                " |B| | |P| | | ",
                " | | | | | |K| ",
                " | | | | | |B| ",
                " | | | | | | | ");
        final Set<Square> got = Rules.attackingBishopSquares(Square.of("d5"), Color.WHITE, position);
        final Set<Square> expected = HashSet.empty();
        assertEquals(expected, got);
    }

    /*
     * Rook behaviour
     */

    @Test
    public void rookMoves() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void attackingRookSquares() {
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | |k| |p| | | ",
                " |n| | | | | | ",
                " | | |*| | |R| ",
                " |B| | |P| | | ",
                " | | | | | |K| ",
                " | | |R| | | | ",
                " | | |R| | | | ");
        final Set<Square> got = Rules.attackingRookSquares(Square.of("d5"), Color.WHITE, position);
        final Set<Square> expected = HashSet.of(Square.of("d2"), Square.of("g5"));
        assertEquals(expected, got);
    }

    /*
     * Queen behaviour
     */

    @Test
    public void queenMoves() {
        final Position position = Positions.fromText(
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
        assertEquals(expected, got);
    }

    @Test
    public void attackingQueenSquares() {
        final Position position = Positions.fromText(
                " |Q| | | | | | ",
                " | | | |p| |k| ",
                " | | | | | | | ",
                " | |q|*| | | | ",
                " | | | |P| | | ",
                " | | | | | |K| ",
                "Q| | |Q| | |Q| ",
                " | | | | | | | ");
        final Set<Square> got = Rules.attackingQueenSquares(Square.of("d5"), Color.WHITE, position);
        final Set<Square> expected = HashSet.of(Square.of("d2"), Square.of("a2"));
        assertEquals(expected, got);
    }
}