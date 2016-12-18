package io.chesslave.eyes

import io.chesslave.model.Color
import io.chesslave.model.Piece
import io.chesslave.model.Piece.Type
import io.chesslave.model.Square
import io.chesslave.visual.rendering.ChessSet
import org.junit.Test

abstract class SinglePieceRecognitionTest(chessSet: ChessSet) : BaseRecognitionTest(chessSet) {

    @Test
    fun whiteRookOnDarkSquare() {
        withPieceOnSquare(Square.of("a1"), Piece(Type.ROOK, Color.WHITE))
    }

    @Test
    fun blackRookOnDarkSquare() {
        withPieceOnSquare(Square.of("a1"), Piece(Type.ROOK, Color.BLACK))
    }

    @Test
    fun whiteRookOnLightSquare() {
        withPieceOnSquare(Square.of("b1"), Piece(Type.ROOK, Color.WHITE))
    }

    @Test
    fun blackRookOnLightSquare() {
        withPieceOnSquare(Square.of("b1"), Piece(Type.ROOK, Color.BLACK))
    }

    @Test
    fun whiteKnightOnDarkSquare() {
        withPieceOnSquare(Square.of("a1"), Piece(Type.KNIGHT, Color.WHITE))
    }

    @Test
    fun blackKnightOnDarkSquare() {
        withPieceOnSquare(Square.of("a1"), Piece(Type.KNIGHT, Color.BLACK))
    }

    @Test
    fun whiteKnightOnLightSquare() {
        withPieceOnSquare(Square.of("b1"), Piece(Type.KNIGHT, Color.WHITE))
    }

    @Test
    fun blackKnightOnLightSquare() {
        withPieceOnSquare(Square.of("b1"), Piece(Type.KNIGHT, Color.BLACK))
    }

    @Test
    fun whitePawnOnDarkSquare() {
        withPieceOnSquare(Square.of("a1"), Piece(Type.PAWN, Color.WHITE))
    }

    @Test
    fun blackPawnOnDarkSquare() {
        withPieceOnSquare(Square.of("a1"), Piece(Type.PAWN, Color.BLACK))
    }

    @Test
    fun whitePawnOnLightSquare() {
        withPieceOnSquare(Square.of("b1"), Piece(Type.PAWN, Color.WHITE))
    }

    @Test
    fun blackPawnOnLightSquare() {
        withPieceOnSquare(Square.of("b1"), Piece(Type.PAWN, Color.BLACK))
    }

    @Test
    fun whiteQueenOnDarkSquare() {
        withPieceOnSquare(Square.of("a1"), Piece(Type.QUEEN, Color.WHITE))
    }

    @Test
    fun blackQueenOnDarkSquare() {
        withPieceOnSquare(Square.of("a1"), Piece(Type.QUEEN, Color.BLACK))
    }

    @Test
    fun whiteQueenOnLightSquare() {
        withPieceOnSquare(Square.of("b1"), Piece(Type.QUEEN, Color.WHITE))
    }

    @Test
    fun blackQueenOnLightSquare() {
        withPieceOnSquare(Square.of("b1"), Piece(Type.QUEEN, Color.BLACK))
    }

    @Test
    fun whiteKingOnDarkSquare() {
        withPieceOnSquare(Square.of("a1"), Piece(Type.KING, Color.WHITE))
    }

    @Test
    fun blackKingOnDarkSquare() {
        withPieceOnSquare(Square.of("a1"), Piece(Type.KING, Color.BLACK))
    }

    @Test
    fun whiteKingOnLightSquare() {
        withPieceOnSquare(Square.of("b1"), Piece(Type.KING, Color.WHITE))
    }

    @Test
    fun blackKingOnLightSquare() {
        withPieceOnSquare(Square.of("b1"), Piece(Type.KING, Color.BLACK))
    }

    @Test
    fun whiteBishopOnDarkSquare() {
        withPieceOnSquare(Square.of("a1"), Piece(Type.BISHOP, Color.WHITE))
    }

    @Test
    fun blackBishopOnDarkSquare() {
        withPieceOnSquare(Square.of("a1"), Piece(Type.BISHOP, Color.BLACK))
    }

    @Test
    fun whiteBishopOnLightSquare() {
        withPieceOnSquare(Square.of("b1"), Piece(Type.BISHOP, Color.WHITE))
    }

    @Test
    fun blackBishopOnLightSquare() {
        withPieceOnSquare(Square.of("b1"), Piece(Type.BISHOP, Color.BLACK))
    }

    @Test
    fun emptyLightSquare() {
        withEmptySquare(Square.of("b1"))
    }

    @Test
    fun emptyDarkSquare() {
        withEmptySquare(Square.of("a1"))
    }

    open fun withPieceOnSquare(square: Square, piece: Piece) {
    }

    open fun withEmptySquare(square: Square) {
    }
}
