package io.chesslave.ears

import io.chesslave.model.MoveDescription.*
import io.chesslave.model.Piece.Type
import org.junit.Assert.assertEquals
import org.junit.Test


class EarsTest {

    val subject: UtteranceParser = parseUtterance

    @Test
    fun parseNamedSquare() {
        assertEquals(Regular(toSquare = Square(col = 4, row = 2)), subject("e3"))
    }

    @Test
    fun colAndRowCouldBeSeparatedByWhitespace() {
        assertEquals(Regular(toSquare = Square(col = 4, row = 2)), subject("e 3"))
    }

    @Test
    fun parsePieceAndSquare() {
        assertEquals(Regular(toSquare = Square(piece = Type.PAWN, col = 4, row = 2)), subject("pedone e3"))
        assertEquals(Regular(toSquare = Square(piece = Type.KNIGHT, col = 4, row = 2)), subject("cavallo e3"))
        assertEquals(Regular(toSquare = Square(piece = Type.BISHOP, col = 4, row = 2)), subject("alfiere e3"))
        assertEquals(Regular(toSquare = Square(piece = Type.ROOK, col = 4, row = 2)), subject("torre e3"))
        assertEquals(Regular(toSquare = Square(piece = Type.QUEEN, col = 4, row = 2)), subject("donna e3"))
        assertEquals(Regular(toSquare = Square(piece = Type.KING, col = 4, row = 2)), subject("re e3"))
    }

    @Test
    fun parseFromAndToSquare() {
        assertEquals(Regular(fromSquare = Square(col = 4, row = 2), toSquare = Square(col = 5, row = 3)), subject("e3 f4"))
    }

    @Test
    fun parsePieceFromAndToSquare() {
        assertEquals(Regular(fromSquare = Square(piece = Type.KING, col = 4, row = 2), toSquare = Square(col = 5, row = 3)), subject("re e3 f4"))
    }

    @Test
    fun parseFromAndToSquares() {
        assertEquals(Regular(fromSquare = Square(col = 4, row = 2), toSquare = Square(col = 5, row = 4)), subject("e3 f5"))
    }

    @Test
    fun parsePieceCaptureSquare() {
        assertEquals(Regular(fromSquare = Square(piece = Type.KNIGHT), toSquare = Square(col = 5, row = 4), capture = true), subject("cavallo mangia f5"))
    }

    @Test
    fun parsePieceCapturePiece() {
        assertEquals(Regular(fromSquare = Square(piece = Type.KNIGHT), toSquare = Square(piece = Type.BISHOP), capture = true), subject("cavallo mangia alfiere"))
    }

    @Test
    fun parseImplicitPawnCaptureColumn() {
        assertEquals(Regular(fromSquare = Square(col = 3), toSquare = Square(col = 5), capture = true), subject("d mangia f"))
    }

    @Test
    fun parsePieceInSquare() {
        assertEquals(Regular(toSquare = Square(piece = Type.KNIGHT, col = 5, row = 4)), subject("cavallo in f5"))
    }

    @Test
    fun parseCheck() {
        assertEquals(Regular(toSquare = Square(col = 5, row = 4), status = Status.CHECK), subject("f5 scacco"))
    }

    @Test
    fun parseCheckmate() {
        assertEquals(Regular(toSquare = Square(col = 5, row = 4), status = Status.CHECKMATE), subject("f5 scaccomatto"))
        assertEquals(Regular(toSquare = Square(col = 5, row = 4), status = Status.CHECKMATE), subject("f5 scacco matto"))
        assertEquals(Regular(toSquare = Square(col = 5, row = 4), status = Status.CHECKMATE), subject("f5 matto"))
    }

    @Test
    fun parseGenericCastling() {
        assertEquals(Castling(), subject("arrocco"))
    }

    @Test
    fun parseShortCastling() {
        assertEquals(Castling(short = true), subject("arrocco corto"))
        assertEquals(Castling(short = true), subject("arrocco di re"))
    }

    @Test
    fun parseLongCastling() {
        assertEquals(Castling(short = false), subject("arrocco lungo"))
        assertEquals(Castling(short = false), subject("arrocco di regina"))
        assertEquals(Castling(short = false), subject("arrocco di donna"))
    }
}