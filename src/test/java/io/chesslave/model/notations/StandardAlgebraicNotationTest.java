package io.chesslave.model.notations;

import static org.junit.Assert.assertEquals;

import io.chesslave.model.Color;
import io.chesslave.model.Move;
import io.chesslave.model.Movements;
import io.chesslave.model.Position;
import io.chesslave.model.Positions;
import io.chesslave.model.Square;
import org.junit.Before;
import org.junit.Test;

public class StandardAlgebraicNotationTest {

    private StandardAlgebraicNotation algebraicNotation;

    @Before
    public void setUp() {
        algebraicNotation = new StandardAlgebraicNotation();
    }

    /*
    * Pawn moves.
    */

    @Test
    public void printStandardPawnMove() {
        final Move move = Movements.regular(Square.of("e2"), Square.of("e4"));
        final Position position = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p|p|p|p|p|p|p",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                "P|P|P|P|P|P|P|P",
                "R|N|B|Q|K|B|N|R");
        assertEquals("e4", algebraicNotation.print(move, position));
    }

    @Test
    public void printCapturePawnMove() {
        final Move move = Movements.regular(Square.of("c5"), Square.of("d4"));
        final Position position = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | |p| | | | | ",
                " | | |P|P| | | ",
                " | | | | |N| | ",
                "P|P|P| | |P|P|P",
                "R|N|B|Q|K|B| |R");
        assertEquals("cxd4", algebraicNotation.print(move, position));
    }

    @Test
    public void printCheckPawnMove() {
        final Move move = Movements.regular(Square.of("h2"), Square.of("h3"));
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | |k| ",
                " | | | | | | | ",
                " | | | | | |K|P",
                " | | | | | | | ");
        assertEquals("h3+", algebraicNotation.print(move, position));
    }

    @Test
    public void printCheckmatePawnMove() {
        final Move move = Movements.regular(Square.of("h2"), Square.of("h3"));
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | |Q| | | ",
                " | | | | | |k|p",
                " | | | | | | | ",
                " | | | | | |K|P",
                " | | | | | | | ");
        assertEquals("h3#", algebraicNotation.print(move, position));
    }

    @Test
    public void printAmbiguousCapturePawnMove() {
        final Move move = Movements.regular(Square.of("c4"), Square.of("d5"));
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | | | | | | | ",
                " | | |k| | | | ",
                " | | |p| | | | ",
                " | |P| |P| | | ",
                " | |K| | | | |p",
                " | | | | | | |P",
                " | | | | | | | ");
        assertEquals("cxd5", algebraicNotation.print(move, position));
    }

    @Test
    public void printAmbiguousCaptureCheckPawnMove() {
        final Move move = Movements.regular(Square.of("c4"), Square.of("d5"));
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | |k| | | ",
                " | | |p| | | | ",
                " | |P| |P| | | ",
                " | |K| | | | |p",
                " | | | | | | |P",
                " | | | | | | | ");
        assertEquals("cxd5+", algebraicNotation.print(move, position));
    }

    @Test
    public void printAmbiguousEnPassantPawnMove() {
        final Move move = Movements.regular(Square.of("d5"), Square.of("c6"));
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " |k|p|P| | | | ",
                " | | |P| | | | ",
                " | |K| | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        assertEquals("dxc6", algebraicNotation.print(move, position));
    }

    @Test
    public void printFalseEnPassantPawnMove() {
        final Move move = Movements.regular(Square.of("d4"), Square.of("c5"));
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " |k|n|P| | | | ",
                " | | |P| | | | ",
                " | |K| | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        assertEquals("dxc5", algebraicNotation.print(move, position));
    }

    /*
    * Knight moves.
    */

    @Test
    public void printStandardKnightMove() {
        final Move move = Movements.regular(Square.of("g1"), Square.of("f3"));
        final Position position = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p|p|p|p|p|p|p",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                "P|P|P|P|P|P|P|P",
                "R|N|B|Q|K|B|N|R");
        assertEquals("Nf3", algebraicNotation.print(move, position));
    }

    @Test
    public void printCaptureKnightMove() {
        final Move move = Movements.regular(Square.of("f3"), Square.of("d4"));
        final Position position = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | | | | | | | ",
                " | | |p|P| | | ",
                " | | | | |N| | ",
                "P|P|P| | |P|P|P",
                "R|N|B|Q|K|B| |R");
        assertEquals("Nxd4", algebraicNotation.print(move, position));
    }

    @Test
    public void printCheckKnightMove() {
        final Move move = Movements.regular(Square.of("e4"), Square.of("f6"));
        final Position position = Positions.fromText(
                " |r| | | | | | ",
                " | | |k| | | | ",
                " | | | | | | | ",
                " |P|K| | | | | ",
                " | | | |N| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        assertEquals("Nf6+", algebraicNotation.print(move, position));
    }

    @Test
    public void printCheckmateKnightMove() {
        final Move move = Movements.regular(Square.of("g5"), Square.of("f7"));
        final Position position = Positions.fromText(
                " |r| | | | |r|k",
                " | | | | | |p|p",
                " | | | | | | | ",
                " |P| | | | |N| ",
                " |K| | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        assertEquals("Nf7#", algebraicNotation.print(move, position));
    }

    @Test
    public void printAmbiguousKnightMove() {
        final Move move = Movements.regular(Square.of("g3"), Square.of("e4"));
        final Position position = Positions.fromText(
                " |r| | | | |r| ",
                " | | |k| | |p|p",
                " | | | | | | | ",
                " |P| | | | |N| ",
                " |K| | | | | | ",
                " | | | | | |N| ",
                " | | | | | | | ",
                " | | | | | | | ");
        assertEquals("N3e4", algebraicNotation.print(move, position));
    }

    @Test
    public void printAmbiguousCaptureKnightMove() {
        final Move move = Movements.regular(Square.of("d2"), Square.of("e4"));
        final Position position = Positions.fromText(
                " |r| | | | | | ",
                " | | |k| | |p|p",
                " | | | | | | | ",
                " |P| | | | |N| ",
                " |K| | |r| | | ",
                " | | | | | | | ",
                " | | |N| | | | ",
                " | | | | | | | ");
        assertEquals("Ndxe4", algebraicNotation.print(move, position));
    }

    @Test
    public void printAmbiguousCheckmateKnightMove() {
        final Move move = Movements.regular(Square.of("g5"), Square.of("f7"));
        final Position position = Positions.fromText(
                " |r| | | | |r|k",
                " | | | | | |p|p",
                " | | |N| | | | ",
                " |P| | | | |N| ",
                " |K| | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        assertEquals("Ngf7#", algebraicNotation.print(move, position));
    }

    /*
    * Bishop moves.
    */

    @Test
    public void printStandardBishopMove() {
        final Move move = Movements.regular(Square.of("f1"), Square.of("b5"));
        final Position position = Positions.fromText(
                "r| |b|q|k|b|n|r",
                "p|p|p|p| |p|p|p",
                " | |n| | | | | ",
                " | | | |p| | | ",
                " | | | |P| | | ",
                " | | | | |N| | ",
                "P|P|P|P| |P|P|P",
                "R|N|B|Q|K|B| |R");
        assertEquals("Bb5", algebraicNotation.print(move, position));
    }

    @Test
    public void printCaptureBishopMove() {
        final Move move = Movements.regular(Square.of("b5"), Square.of("c6"));
        final Position position = Positions.fromText(
                "r| |b|q|k|b|n|r",
                " |p|p|p| |p|p|p",
                "p| |n| | | | | ",
                " |B| | |p| | | ",
                " | | | |P| | | ",
                " | | | | |N| | ",
                "P|P|P|P| |P|P|P",
                "R|N|B|Q|K| | |R");
        assertEquals("Bxc6", algebraicNotation.print(move, position));
    }

    @Test
    public void printCheckBishopMove() {
        final Move move = Movements.regular(Square.of("f1"), Square.of("b5"));
        final Position position = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | |p| | | | | ",
                " | | | |P| | | ",
                " | | | | |N| | ",
                "P|P|P|P| |P|P|P",
                "R|N|B|Q|K|B| |R");
        assertEquals("Bb5+", algebraicNotation.print(move, position));
    }

    @Test
    public void printCheckmateBishopMove() {
        final Move move = Movements.regular(Square.of("f1"), Square.of("b5"));
        final Position position = Positions.fromText(
                " | | | |k|r| | ",
                "p|p| | |b|p|p|p",
                "n| | |p|p| | | ",
                "B| |p| | | | | ",
                " | | | |P| |b| ",
                " |P|N| | |N| | ",
                "P| |P|P| |P|P|P",
                "R| | | |K|B| |R");
        assertEquals("Bb5#", algebraicNotation.print(move, position));
    }

    /*
    * Rook moves.
    */

    @Test
    public void printStandardRookMove() {
        final Move move = Movements.regular(Square.of("f1"), Square.of("e1"));
        final Position position = Positions.fromText(
                "r| |b|q|k| | |r",
                "p|p|p|p|b|p|p|p",
                " | |n| | |n| | ",
                " |B| | |p| | | ",
                " | | | |P| | | ",
                " | | | | |N| | ",
                "P|P|P|P| |P|P|P",
                "R|N|B|Q| |R|K| ");
        assertEquals("Re1", algebraicNotation.print(move, position));
    }

    @Test
    public void printCaptureRookMove() {
        final Move move = Movements.regular(Square.of("b8"), Square.of("b5"));
        final Position position = Positions.fromText(
                " |r| | | | | | ",
                " | | |k| | | | ",
                " | | | |p| | | ",
                " |R| | | | | | ",
                " | |K| | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        assertEquals("Rxb5", algebraicNotation.print(move, position));
    }

    @Test
    public void printCheckRookMove() {
        final Move move = Movements.regular(Square.of("b8"), Square.of("c8"));
        final Position position = Positions.fromText(
                " |r| | | | | | ",
                " | | |k| | | | ",
                " | | | | | | | ",
                " |P|K| | | | | ",
                " | | | |N| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        assertEquals("Rc8+", algebraicNotation.print(move, position));
    }

    @Test
    public void printCheckmateRookMove() {
        final Move move = Movements.regular(Square.of("c2"), Square.of("a2"));
        final Position position = Positions.fromText(
                " |r| | | | | | ",
                " | | |k| | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                "K| | | | | | | ",
                " | | | | | | | ",
                " | |r| | | | | ",
                " | | | | | | | ");
        assertEquals("Ra2#", algebraicNotation.print(move, position));
    }

    @Test
    public void printAmbiguousRookMove() {
        final Move move = Movements.regular(Square.of("b8"), Square.of("b2"));
        final Position position = Positions.fromText(
                " |r| | | | | | ",
                " | | |k| | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | |r| | | | | ",
                " | | | | |K| | ");
        assertEquals("Rbb2", algebraicNotation.print(move, position));
    }

    @Test
    public void printAmbiguousCheckmateRookMove() {
        final Move move = Movements.regular(Square.of("c2"), Square.of("b2"));
        final Position position = Positions.fromText(
                " |r| | | | | | ",
                " | | |k| | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                "P| | | | | | | ",
                "K|P|r| | | | | ",
                " | | | | | | |Q");
        assertEquals("Rcxb2#", algebraicNotation.print(move, position));
    }

    /*
    * Queen moves.
    */

    @Test
    public void printStandardQueenMove() {
        final Move move = Movements.regular(Square.of("d1"), Square.of("e2"));
        final Position position = Positions.fromText(
                "r| |b|q|k| | |r",
                "p|p|p|p|b|p|p|p",
                " | |n| | |n| | ",
                " |B| | |p| | | ",
                " | | | |P| | | ",
                " | | | | |N| | ",
                "P|P|P|P| |P|P|P",
                "R|N|B|Q| |R|K| ");
        assertEquals("Qe2", algebraicNotation.print(move, position));
    }

    @Test
    public void printCaptureQueenMove() {
        final Move move = Movements.regular(Square.of("d8"), Square.of("d7"));
        final Position position = Positions.fromText(
                "r|n| |q|k|b|n|r",
                "p|p| |B|p|p|p|p",
                " | | |p| | | | ",
                " | |p| | | | | ",
                " | | | |P| | | ",
                " | | | | |N| | ",
                "P|P|P|P| |P|P|P",
                "R|N|B|Q|K| | |R");
        assertEquals("Qxd7", algebraicNotation.print(move, position));
    }

    @Test
    public void printCheckQueenMove() {
        final Move move = Movements.regular(Square.of("d3"), Square.of("h7"));
        final Position position = Positions.fromText(
                " |r| |q|r| |k| ",
                " | | | | |p|p| ",
                " | | | |p| | |p",
                " | | | | | | | ",
                " | | |P| | | | ",
                " | |P|Q|P| |P| ",
                " | |B| | |P| |P",
                "R| | | | | |K| ");
        assertEquals("Qh7+", algebraicNotation.print(move, position));
    }

    @Test
    public void printCheckmateQueenMove() {
        final Move move = Movements.regular(Square.of("d3"), Square.of("h7"));
        final Position position = Positions.fromText(
                " |r| |q| |r|k| ",
                " | | | | |p|p| ",
                " | | | |p| | |p",
                " | | | | | | | ",
                " | | |P| | | | ",
                " | |P|Q|P| |P| ",
                " | |B| | |P| |P",
                "R| | | | | |K| ");
        assertEquals("Qh7#", algebraicNotation.print(move, position));
    }

    @Test
    public void printCaptureCheckmateQueenMove() {
        final Move move = Movements.regular(Square.of("d3"), Square.of("h7"));
        final Position position = Positions.fromText(
                " |r| |q| |r|k| ",
                " | | | | |p|p|p",
                " | | | |p| | | ",
                " | | | | | | | ",
                " | | |P| | | | ",
                " | |P|Q|P| |P| ",
                " | |B| | |P| |P",
                "R| | | | | |K| ");
        assertEquals("Qxh7#", algebraicNotation.print(move, position));
    }

    @Test
    public void printAmbiguousQueenMove() {
        final Move move = Movements.regular(Square.of("g8"), Square.of("b8"));
        final Position position = Positions.fromText(
                " | | | | | |Q| ",
                " | | | |k| | | ",
                " | | | |p| | | ",
                " |Q| | | | | | ",
                " | |K| | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        assertEquals("Qgb8", algebraicNotation.print(move, position));
    }

    @Test
    public void printAmbiguousCheckQueenMove() {
        final Move move = Movements.regular(Square.of("b5"), Square.of("e8"));
        final Position position = Positions.fromText(
                " | | | | | |Q| ",
                " | | | |k| | | ",
                " | | | |p| | | ",
                " |Q| | | | | | ",
                " | |K| | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        assertEquals("Qbe8+", algebraicNotation.print(move, position));
    }

    /*
    * King moves.
    */

    @Test
    public void printStandardKingMove() {
        final Move move = Movements.regular(Square.of("g1"), Square.of("h1"));
        final Position position = Positions.fromText(
                "r| |b|q| |r|k| ",
                "p|p|p|p|b|p|p|p",
                " | |n| | |n| | ",
                " |B| | |p| | | ",
                " | | | |P| | | ",
                " | | | | |N| | ",
                "P|P|P|P| |P|P|P",
                "R|N|B|Q| |R|K| ");
        assertEquals("Kh1", algebraicNotation.print(move, position));
    }

    @Test
    public void printCaptureKingMove() {
        final Move move = Movements.regular(Square.of("c4"), Square.of("b5"));
        final Position position = Positions.fromText(
                " | | | | | | | ",
                " | | |k| | | | ",
                " | | | |p| | | ",
                " |r| | | | | | ",
                " | |K| | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        assertEquals("Kxb5", algebraicNotation.print(move, position));
    }

    @Test
    public void printWhiteShortCastling() {
        final Move move = Movements.shortCastling(Color.WHITE);
        final Position position = Positions.fromText(
                "r| |b|q|k| | |r",
                "p|p|p|p|b|p|p|p",
                " | |n| | |n| | ",
                " |B| | |p| | | ",
                " | | | |P| | | ",
                " | | | | |N| | ",
                "P|P|P|P| |P|P|P",
                "R|N|B|Q|K| | |R");
        assertEquals("0-0", algebraicNotation.print(move, position));
    }

    @Test
    public void printBlackShortCastling() {
        final Move move = Movements.shortCastling(Color.BLACK);
        final Position position = Positions.fromText(
                "r| |b|q|k| | |r",
                "p|p|p|p|b|p|p|p",
                " | |n| | |n| | ",
                " |B| | |p| | | ",
                " | | | |P| | | ",
                " | | | | |N| | ",
                "P|P|P|P| |P|P|P",
                "R|N|B|Q| |R|K| ");
        assertEquals("0-0", algebraicNotation.print(move, position));
    }

    @Test
    public void printWhiteLongCastling() {
        final Move move = Movements.longCastling(Color.WHITE);
        final Position position = Positions.fromText(
                "r| |b|q|k| | |r",
                " |p| |n|b|p|p|p",
                "p| | |p|p|n| | ",
                " | | | | | |B| ",
                " | | |N|P|P| | ",
                " | |N| | |Q| | ",
                "P|P|P| | | |P|P",
                "R| | | |K|B| |R");
        assertEquals("0-0-0", algebraicNotation.print(move, position));
    }

    @Test
    public void printBlackLongCastling() {
        final Move move = Movements.longCastling(Color.BLACK);
        final Position position = Positions.fromText(
                "r| | | |k|b|n|r",
                "p|p|p|q| |p|p|p",
                " | |n|p| | | | ",
                " | | | |p| | | ",
                " | |B| |P| |b| ",
                " | | |P| |N| | ",
                "P|P|P|N| |P|P|P",
                "R| |B|Q| |R|K| ");
        assertEquals("0-0-0", algebraicNotation.print(move, position));
    }

    @Test
    public void printShortCastlingCheck() {
        final Move move = Movements.shortCastling(Color.WHITE);
        final Position position = Positions.fromText(
                " | | | | |k| |r",
                " |p| | | | | |p",
                "p| | | | | |p| ",
                " | | | | | | | ",
                " |P|P| | | | | ",
                " | | | | | | | ",
                "P| | | | | |P|P",
                " | |R| |K| | |R");
        assertEquals("0-0+", algebraicNotation.print(move, position));
    }

    @Test
    public void printLongCastlingCheck() {
        final Move move = Movements.longCastling(Color.WHITE);
        final Position position = Positions.fromText(
                "r| |b|k| | | |r",
                " |p| | | |p|p|p",
                "p|q| | | |b| | ",
                " | | | | |P| | ",
                " | | | | | | | ",
                " | |N| | |Q| | ",
                "P|P|P| | | |P|P",
                "R| | | |K| | |R");
        assertEquals("0-0-0+", algebraicNotation.print(move, position));
    }

    @Test
    public void printShortCastlingCheckmate() {
        final Move move = Movements.shortCastling(Color.WHITE);
        final Position position = Positions.fromText(
                " | | | | |k|r| ",
                " |p| | | | | |p",
                "p| | | | | |p| ",
                " | | | | | | | ",
                " |P| | | | | | ",
                " | |B| | | | | ",
                "P| | | |R| |P|P",
                " | | | |K| | |R");
        assertEquals("0-0#", algebraicNotation.print(move, position));
    }

    @Test
    public void printLongCastlingCheckmate() {
        final Move move = Movements.longCastling(Color.WHITE);
        final Position position = Positions.fromText(
                " | |r|k| | | |r",
                " | |p| |p| |p|p",
                "p| | | | | |b| ",
                " | | | | |P| | ",
                "Q| | | | | | | ",
                " | |N| | | | | ",
                "P|P|P| | | |P|P",
                "R| | | |K| | |R");
        assertEquals("0-0-0#", algebraicNotation.print(move, position));
    }
}
