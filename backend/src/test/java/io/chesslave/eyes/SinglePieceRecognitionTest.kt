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
        withPieceOnSquare(Board.a1, Piece.whiteRook)
    }

    @Test
    fun blackRookOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece.blackRook)
    }

    @Test
    fun whiteRookOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece.whiteRook)
    }

    @Test
    fun blackRookOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece.blackRook)
    }

    @Test
    fun whiteKnightOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece.whiteKnight)
    }

    @Test
    fun blackKnightOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece.blackKnight)
    }

    @Test
    fun whiteKnightOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece.whiteKnight)
    }

    @Test
    fun blackKnightOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece.blackKnight)
    }

    @Test
    fun whitePawnOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece.whitePawn)
    }

    @Test
    fun blackPawnOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece.blackPawn)
    }

    @Test
    fun whitePawnOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece.whitePawn)
    }

    @Test
    fun blackPawnOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece.blackPawn)
    }

    @Test
    fun whiteQueenOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece.whiteQueen)
    }

    @Test
    fun blackQueenOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece.blackQueen)
    }

    @Test
    fun whiteQueenOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece.whiteQueen)
    }

    @Test
    fun blackQueenOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece.blackQueen)
    }

    @Test
    fun whiteKingOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece.whiteKing)
    }

    @Test
    fun blackKingOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece.blackKing)
    }

    @Test
    fun whiteKingOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece.whiteKing)
    }

    @Test
    fun blackKingOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece.blackKing)
    }

    @Test
    fun whiteBishopOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece.whiteBishop)
    }

    @Test
    fun blackBishopOnDarkSquare() {
        withPieceOnSquare(Board.a1, Piece.blackBishop)
    }

    @Test
    fun whiteBishopOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece.whiteBishop)
    }

    @Test
    fun blackBishopOnLightSquare() {
        withPieceOnSquare(Board.b1, Piece.blackBishop)
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
