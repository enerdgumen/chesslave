package io.chesslave.eyes

import io.chesslave.model.Board
import io.chesslave.model.Color
import io.chesslave.model.Piece
import io.chesslave.model.Piece.Type
import io.chesslave.model.Square
import io.chesslave.visual.rendering.ChessSet
import org.junit.Test

abstract class SinglePieceRecognitionTest(chessSet: ChessSet) : BaseRecognitionTest(chessSet) {

    @Test
    fun whiteRookOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece(Type.ROOK, Color.WHITE))
    }

    @Test
    fun blackRookOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece(Type.ROOK, Color.BLACK))
    }

    @Test
    fun whiteRookOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece(Type.ROOK, Color.WHITE))
    }

    @Test
    fun blackRookOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece(Type.ROOK, Color.BLACK))
    }

    @Test
    fun whiteKnightOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece(Type.KNIGHT, Color.WHITE))
    }

    @Test
    fun blackKnightOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece(Type.KNIGHT, Color.BLACK))
    }

    @Test
    fun whiteKnightOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece(Type.KNIGHT, Color.WHITE))
    }

    @Test
    fun blackKnightOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece(Type.KNIGHT, Color.BLACK))
    }

    @Test
    fun whitePawnOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece(Type.PAWN, Color.WHITE))
    }

    @Test
    fun blackPawnOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece(Type.PAWN, Color.BLACK))
    }

    @Test
    fun whitePawnOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece(Type.PAWN, Color.WHITE))
    }

    @Test
    fun blackPawnOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece(Type.PAWN, Color.BLACK))
    }

    @Test
    fun whiteQueenOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece(Type.QUEEN, Color.WHITE))
    }

    @Test
    fun blackQueenOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece(Type.QUEEN, Color.BLACK))
    }

    @Test
    fun whiteQueenOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece(Type.QUEEN, Color.WHITE))
    }

    @Test
    fun blackQueenOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece(Type.QUEEN, Color.BLACK))
    }

    @Test
    fun whiteKingOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece(Type.KING, Color.WHITE))
    }

    @Test
    fun blackKingOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece(Type.KING, Color.BLACK))
    }

    @Test
    fun whiteKingOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece(Type.KING, Color.WHITE))
    }

    @Test
    fun blackKingOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece(Type.KING, Color.BLACK))
    }

    @Test
    fun whiteBishopOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece(Type.BISHOP, Color.WHITE))
    }

    @Test
    fun blackBishopOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece(Type.BISHOP, Color.BLACK))
    }

    @Test
    fun whiteBishopOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece(Type.BISHOP, Color.WHITE))
    }

    @Test
    fun blackBishopOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece(Type.BISHOP, Color.BLACK))
    }

    @Test
    fun emptyLightSquare() {
        withEmptySquare(Board.b1)
    }

    @Test
    fun emptyDarkSquare() {
        withEmptySquare(Board.a1)
    }

    open fun withPieceOnSquare(square: Square, piece: Piece) {
    }

    open fun withEmptySquare(square: Square) {
    }
}
