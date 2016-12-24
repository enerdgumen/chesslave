package io.chesslave.model.notations

import io.chesslave.model.*
import io.chesslave.model.Move.Regular.Variation.EnPassant
import org.junit.Assert.assertEquals
import org.junit.Test

// TODO: test it without use MoveDescriptor
class StandardAlgebraicNotationTest {

    private fun notation(move: Move, position: Position): String {
        val description = MoveDescriptor().describe(move, position)
        val notation = StandardAlgebraicNotation()
        return notation.print(description)
    }

    /*
    * Pawn moves.
    */

    @Test
    fun printStandardPawnMove() {
        val move = Move.Regular(Board.e2, Board.e4)
        val position = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p|p|p|p|p|p|p",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "P|P|P|P|P|P|P|P",
            "R|N|B|Q|K|B|N|R")
        assertEquals("e4", notation(move, position))
    }

    @Test
    fun printCapturePawnMove() {
        val move = Move.Regular(Board.c5, Board.d4)
        val position = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | |p| | | | | ",
            " | | |P|P| | | ",
            " | | | | |N| | ",
            "P|P|P| | |P|P|P",
            "R|N|B|Q|K|B| |R")
        assertEquals("cxd4", notation(move, position))
    }

    @Test
    fun printEnPassantPawnMove() {
        val move = Move.Regular(Board.d5, Board.c6, EnPassant())
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " |k|p|P| | | | ",
            " | | | | | | | ",
            " | |K| | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("dxc6", notation(move, position))
    }

    @Test
    fun printCheckPawnMove() {
        val move = Move.Regular(Board.h2, Board.h3)
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | |k| ",
            " | | | | | | | ",
            " | | | | | |K|P",
            " | | | | | | | ")
        assertEquals("h3+", notation(move, position))
    }

    @Test
    fun printCheckmatePawnMove() {
        val move = Move.Regular(Board.h2, Board.h3)
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |Q| | | ",
            " | | | | | |k|p",
            " | | | | | | | ",
            " | | | | | |K|P",
            " | | | | | | | ")
        assertEquals("h3#", notation(move, position))
    }

    @Test
    fun printAmbiguousCapturePawnMove() {
        val move = Move.Regular(Board.c4, Board.d5)
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | |k| | | | ",
            " | | |p| | | | ",
            " | |P| |P| | | ",
            " | |K| | | | |p",
            " | | | | | | |P",
            " | | | | | | | ")
        assertEquals("cxd5", notation(move, position))
    }

    @Test
    fun printAmbiguousCaptureCheckPawnMove() {
        val move = Move.Regular(Board.c4, Board.d5)
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |k| | | ",
            " | | |p| | | | ",
            " | |P| |P| | | ",
            " | |K| | | | |p",
            " | | | | | | |P",
            " | | | | | | | ")
        assertEquals("cxd5+", notation(move, position))
    }

    @Test
    fun printAmbiguousEnPassantPawnMove() {
        val move = Move.Regular(Board.d5, Board.c6, EnPassant())
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | |k| ",
            " |P|p|P| | | | ",
            " | | |P| | | | ",
            " | |K| | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("dxc6", notation(move, position))
    }

    @Test
    fun printFalseEnPassantPawnMove() {
        val move = Move.Regular(Board.d4, Board.c5)
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " |k|n|P| | | | ",
            " | | |P| | | | ",
            " | |K| | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("dxc5", notation(move, position))
    }

    /*
    * Knight moves.
    */

    @Test
    fun printStandardKnightMove() {
        val move = Move.Regular(Board.g1, Board.f3)
        val position = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p|p|p|p|p|p|p",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "P|P|P|P|P|P|P|P",
            "R|N|B|Q|K|B|N|R")
        assertEquals("Nf3", notation(move, position))
    }

    @Test
    fun printCaptureKnightMove() {
        val move = Move.Regular(Board.f3, Board.d4)
        val position = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | | | |N| | ",
            "P|P|P| | |P|P|P",
            "R|N|B|Q|K|B| |R")
        assertEquals("Nxd4", notation(move, position))
    }

    @Test
    fun printCheckKnightMove() {
        val move = Move.Regular(Board.e4, Board.f6)
        val position = Positions.fromText(
            " |r| | | | | | ",
            " | | |k| | | | ",
            " | | | | | | | ",
            " |P|K| | | | | ",
            " | | | |N| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("Nf6+", notation(move, position))
    }

    @Test
    fun printCheckmateKnightMove() {
        val move = Move.Regular(Board.g5, Board.f7)
        val position = Positions.fromText(
            " |r| | | | |r|k",
            " | | | | | |p|p",
            " | | | | | | | ",
            " |P| | | | |N| ",
            " |K| | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("Nf7#", notation(move, position))
    }

    @Test
    fun printAmbiguousKnightMove() {
        val move = Move.Regular(Board.g3, Board.e4)
        val position = Positions.fromText(
            " |r| | | | |r| ",
            " | | |k| | |p|p",
            " | | | | | | | ",
            " |P| | | | |N| ",
            " |K| | | | | | ",
            " | | | | | |N| ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("N3e4", notation(move, position))
    }

    @Test
    fun printAmbiguousCaptureKnightMove() {
        val move = Move.Regular(Board.d2, Board.e4)
        val position = Positions.fromText(
            " |r| | | | | | ",
            " | | |k| | |p|p",
            " | | | | | | | ",
            " |P| | | | |N| ",
            " |K| | |r| | | ",
            " | | | | | | | ",
            " | | |N| | | | ",
            " | | | | | | | ")
        assertEquals("Ndxe4", notation(move, position))
    }

    @Test
    fun printAmbiguousCheckmateKnightMove() {
        val move = Move.Regular(Board.g5, Board.f7)
        val position = Positions.fromText(
            " |r| | | | |r|k",
            " | | | | | |p|p",
            " | | |N| | | | ",
            " |P| | | | |N| ",
            " |K| | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("Ngf7#", notation(move, position))
    }

    /*
    * Bishop moves.
    */

    @Test
    fun printStandardBishopMove() {
        val move = Move.Regular(Board.f1, Board.b5)
        val position = Positions.fromText(
            "r| |b|q|k|b|n|r",
            "p|p|p|p| |p|p|p",
            " | |n| | | | | ",
            " | | | |p| | | ",
            " | | | |P| | | ",
            " | | | | |N| | ",
            "P|P|P|P| |P|P|P",
            "R|N|B|Q|K|B| |R")
        assertEquals("Bb5", notation(move, position))
    }

    @Test
    fun printCaptureBishopMove() {
        val move = Move.Regular(Board.b5, Board.c6)
        val position = Positions.fromText(
            "r| |b|q|k|b|n|r",
            " |p|p|p| |p|p|p",
            "p| |n| | | | | ",
            " |B| | |p| | | ",
            " | | | |P| | | ",
            " | | | | |N| | ",
            "P|P|P|P| |P|P|P",
            "R|N|B|Q|K| | |R")
        assertEquals("Bxc6", notation(move, position))
    }

    @Test
    fun printCheckBishopMove() {
        val move = Move.Regular(Board.f1, Board.b5)
        val position = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | |p| | | | | ",
            " | | | |P| | | ",
            " | | | | |N| | ",
            "P|P|P|P| |P|P|P",
            "R|N|B|Q|K|B| |R")
        assertEquals("Bb5+", notation(move, position))
    }

    @Test
    fun printCheckmateBishopMove() {
        val move = Move.Regular(Board.f1, Board.b5)
        val position = Positions.fromText(
            " | | | |k|r| | ",
            "p|p| | |b|p|p|p",
            "n| | |p|p| | | ",
            "B| |p| | | | | ",
            " | | | |P| |b| ",
            " |P|N| | |N| | ",
            "P| |P|P| |P|P|P",
            "R| | | |K|B| |R")
        assertEquals("Bb5#", notation(move, position))
    }

    /*
    * Rook moves.
    */

    @Test
    fun printStandardRookMove() {
        val move = Move.Regular(Board.f1, Board.e1)
        val position = Positions.fromText(
            "r| |b|q|k| | |r",
            "p|p|p|p|b|p|p|p",
            " | |n| | |n| | ",
            " |B| | |p| | | ",
            " | | | |P| | | ",
            " | | | | |N| | ",
            "P|P|P|P| |P|P|P",
            "R|N|B|Q| |R|K| ")
        assertEquals("Re1", notation(move, position))
    }

    @Test
    fun printCaptureRookMove() {
        val move = Move.Regular(Board.b8, Board.b5)
        val position = Positions.fromText(
            " |r| | | | | | ",
            " | | |k| | | | ",
            " | | | |p| | | ",
            " |R| | | | | | ",
            " | |K| | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("Rxb5", notation(move, position))
    }

    @Test
    fun printCheckRookMove() {
        val move = Move.Regular(Board.b8, Board.c8)
        val position = Positions.fromText(
            " |r| | | | | | ",
            " | | |k| | | | ",
            " | | | | | | | ",
            " |P|K| | | | | ",
            " | | | |N| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("Rc8+", notation(move, position))
    }

    @Test
    fun printCheckmateRookMove() {
        val move = Move.Regular(Board.c2, Board.a2)
        val position = Positions.fromText(
            " |r| | | | | | ",
            " | | |k| | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "K| | | | | | | ",
            " | | | | | | | ",
            " | |r| | | | | ",
            " | | | | | | | ")
        assertEquals("Ra2#", notation(move, position))
    }

    @Test
    fun printAmbiguousRookMove() {
        val move = Move.Regular(Board.b8, Board.b2)
        val position = Positions.fromText(
            " |r| | | | | | ",
            " | | |k| | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | |r| | | | | ",
            " | | | | |K| | ")
        assertEquals("Rbb2", notation(move, position))
    }

    @Test
    fun printExtremeAmbiguousRookMove() {
        val move = Move.Regular(Board.b1, Board.b2)
        val position = Positions.fromText(
            " |r| | | | | | ",
            " | | |k| | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | |K| | | | ",
            " | | | | | | | ",
            " | |r| | | | | ",
            " |r| | | | | | ")
        assertEquals("Rb1b2", notation(move, position))
    }

    @Test
    fun printAmbiguousCheckmateRookMove() {
        val move = Move.Regular(Board.c2, Board.b2)
        val position = Positions.fromText(
            " |r| | | | | | ",
            " | | |k| | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            "P| | | | | | | ",
            "K|P|r| | | | | ",
            " | | | | | | |q")
        assertEquals("Rcxb2#", notation(move, position))
    }

    /*
    * Queen moves.
    */

    @Test
    fun printStandardQueenMove() {
        val move = Move.Regular(Board.d1, Board.e2)
        val position = Positions.fromText(
            "r| |b|q|k| | |r",
            "p|p|p|p|b|p|p|p",
            " | |n| | |n| | ",
            " |B| | |p| | | ",
            " | | | |P| | | ",
            " | | | | |N| | ",
            "P|P|P|P| |P|P|P",
            "R|N|B|Q| |R|K| ")
        assertEquals("Qe2", notation(move, position))
    }

    @Test
    fun printCaptureQueenMove() {
        val move = Move.Regular(Board.d8, Board.d7)
        val position = Positions.fromText(
            "r|n| |q|k|b|n|r",
            "p|p| |B|p|p|p|p",
            " | | |p| | | | ",
            " | |p| | | | | ",
            " | | | |P| | | ",
            " | | | | |N| | ",
            "P|P|P|P| |P|P|P",
            "R|N|B|Q|K| | |R")
        assertEquals("Qxd7", notation(move, position))
    }

    @Test
    fun printCheckQueenMove() {
        val move = Move.Regular(Board.d3, Board.h7)
        val position = Positions.fromText(
            " |r| |q|r| |k| ",
            " | | | | |p|p| ",
            " | | | |p| | |p",
            " | | | | | | | ",
            " | | |P| | | | ",
            " | |P|Q|P| |P| ",
            " | |B| | |P| |P",
            "R| | | | | |K| ")
        assertEquals("Qh7+", notation(move, position))
    }

    @Test
    fun printCheckmateQueenMove() {
        val move = Move.Regular(Board.d3, Board.h7)
        val position = Positions.fromText(
            " |r| |q| |r|k| ",
            " | | | | |p|p| ",
            " | | | |p| | |p",
            " | | | | | | | ",
            " | | |P| | | | ",
            " | |P|Q|P| |P| ",
            " | |B| | |P| |P",
            "R| | | | | |K| ")
        assertEquals("Qh7#", notation(move, position))
    }

    @Test
    fun printCaptureCheckmateQueenMove() {
        val move = Move.Regular(Board.d3, Board.h7)
        val position = Positions.fromText(
            " |r| |q| |r|k| ",
            " | | | | |p|p|p",
            " | | | |p| | | ",
            " | | | | | | | ",
            " | | |P| | | | ",
            " | |P|Q|P| |P| ",
            " | |B| | |P| |P",
            "R| | | | | |K| ")
        assertEquals("Qxh7#", notation(move, position))
    }

    @Test
    fun printAmbiguousQueenMove() {
        val move = Move.Regular(Board.g8, Board.b8)
        val position = Positions.fromText(
            " | | | | | |Q| ",
            " | | | |k| | | ",
            " | | | |p| | | ",
            " |Q| | | | | | ",
            " | |K| | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("Qgb8", notation(move, position))
    }

    @Test
    fun printAmbiguousCheckQueenMove() {
        val move = Move.Regular(Board.b5, Board.e8)
        val position = Positions.fromText(
            " | | | | | |Q| ",
            " | | | |k| | | ",
            " | | | |p| | | ",
            " |Q| | | | | | ",
            " | |K| | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("Qbe8+", notation(move, position))
    }

    @Test
    fun printExtremeAmbiguousCheckQueenMove() {
        val move = Move.Regular(Board.b5, Board.e8)
        val position = Positions.fromText(
            " |Q| | | | |Q| ",
            " | | | |k| | | ",
            " | | | |p| | | ",
            " |Q| | | | | | ",
            " | |K| | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("Qb5e8+", notation(move, position))
    }

    /*
    * King moves.
    */

    @Test
    fun printStandardKingMove() {
        val move = Move.Regular(Board.g1, Board.h1)
        val position = Positions.fromText(
            "r| |b|q| |r|k| ",
            "p|p|p|p|b|p|p|p",
            " | |n| | |n| | ",
            " |B| | |p| | | ",
            " | | | |P| | | ",
            " | | | | |N| | ",
            "P|P|P|P| |P|P|P",
            "R|N|B|Q| |R|K| ")
        assertEquals("Kh1", notation(move, position))
    }

    @Test
    fun printCaptureKingMove() {
        val move = Move.Regular(Board.c4, Board.b5)
        val position = Positions.fromText(
            " | | | | | | | ",
            " | | |k| | | | ",
            " | | | |p| | | ",
            " |r| | | | | | ",
            " | |K| | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ")
        assertEquals("Kxb5", notation(move, position))
    }

    @Test
    fun printWhiteShortCastling() {
        val move = Move.ShortCastling(Color.WHITE)
        val position = Positions.fromText(
            "r| |b|q|k| | |r",
            "p|p|p|p|b|p|p|p",
            " | |n| | |n| | ",
            " |B| | |p| | | ",
            " | | | |P| | | ",
            " | | | | |N| | ",
            "P|P|P|P| |P|P|P",
            "R|N|B|Q|K| | |R")
        assertEquals("0-0", notation(move, position))
    }

    @Test
    fun printBlackShortCastling() {
        val move = Move.ShortCastling(Color.BLACK)
        val position = Positions.fromText(
            "r| |b|q|k| | |r",
            "p|p|p|p|b|p|p|p",
            " | |n| | |n| | ",
            " |B| | |p| | | ",
            " | | | |P| | | ",
            " | | | | |N| | ",
            "P|P|P|P| |P|P|P",
            "R|N|B|Q| |R|K| ")
        assertEquals("0-0", notation(move, position))
    }

    @Test
    fun printWhiteLongCastling() {
        val move = Move.LongCastling(Color.WHITE)
        val position = Positions.fromText(
            "r| |b|q|k| | |r",
            " |p| |n|b|p|p|p",
            "p| | |p|p|n| | ",
            " | | | | | |B| ",
            " | | |N|P|P| | ",
            " | |N| | |Q| | ",
            "P|P|P| | | |P|P",
            "R| | | |K|B| |R")
        assertEquals("0-0-0", notation(move, position))
    }

    @Test
    fun printBlackLongCastling() {
        val move = Move.LongCastling(Color.BLACK)
        val position = Positions.fromText(
            "r| | | |k|b|n|r",
            "p|p|p|q| |p|p|p",
            " | |n|p| | | | ",
            " | | | |p| | | ",
            " | |B| |P| |b| ",
            " | | |P| |N| | ",
            "P|P|P|N| |P|P|P",
            "R| |B|Q| |R|K| ")
        assertEquals("0-0-0", notation(move, position))
    }

    @Test
    fun printShortCastlingCheck() {
        val move = Move.ShortCastling(Color.WHITE)
        val position = Positions.fromText(
            " | | | | |k| |r",
            " |p| | | | | |p",
            "p| | | | | |p| ",
            " | | | | | | | ",
            " |P|P| | | | | ",
            " | | | | | | | ",
            "P| | | | | |P|P",
            " | |R| |K| | |R")
        assertEquals("0-0+", notation(move, position))
    }

    @Test
    fun printLongCastlingCheck() {
        val move = Move.LongCastling(Color.WHITE)
        val position = Positions.fromText(
            "r| |b|k| | | |r",
            " |p| | | |p|p|p",
            "p|q| | | |b| | ",
            " | | | | |P| | ",
            " | | | | | | | ",
            " | |N| | |Q| | ",
            "P|P|P| | | |P|P",
            "R| | | |K| | |R")
        assertEquals("0-0-0+", notation(move, position))
    }

    @Test
    fun printShortCastlingCheckmate() {
        val move = Move.ShortCastling(Color.WHITE)
        val position = Positions.fromText(
            " | | | | |k|r| ",
            " |p| | | | | |p",
            "p| | | | | |p| ",
            " | | | | | | | ",
            " |P| | | | | | ",
            " | |B| | | | | ",
            "P| | | |R| |P|P",
            " | | | |K| | |R")
        assertEquals("0-0#", notation(move, position))
    }

    @Test
    fun printLongCastlingCheckmate() {
        val move = Move.LongCastling(Color.WHITE)
        val position = Positions.fromText(
            " | |r|k| | | |r",
            " | |p| |p| |p|p",
            "p| | | | | |b| ",
            " | | | | |P| | ",
            "Q| | | | | | | ",
            " | |N| | | | | ",
            "P|P|P| | | |P|P",
            "R| | | |K| | |R")
        assertEquals("0-0-0#", notation(move, position))
    }
}
