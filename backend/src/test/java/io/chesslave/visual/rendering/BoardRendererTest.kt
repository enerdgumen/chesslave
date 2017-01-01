package io.chesslave.visual.rendering

import io.chesslave.model.*
import io.chesslave.model.Piece.Type
import io.chesslave.visual.Images
import org.junit.Assert.assertTrue
import org.junit.Test

class BoardRendererTest {

    // TODO: move images into this package
    val imagesPath = "/images/visual/rendering/"
    val chessSet = ChessSet.read("/images/set1/")
    val position = Position.Builder()
        .withPiece(Board.a1, Piece.whiteRook)
        .withPiece(Board.b2, Piece.whiteKnight)
        .withPiece(Board.c3, Piece.whiteBishop)
        .withPiece(Board.d4, Piece.whiteQueen)
        .withPiece(Board.e5, Piece.whiteKing)
        .withPiece(Board.f6, Piece.whitePawn)
        .withPiece(Board.h8, Piece.blackRook)
        .withPiece(Board.g7, Piece.blackKnight)
        .withPiece(Board.f5, Piece.blackBishop)
        .withPiece(Board.e4, Piece.blackQueen)
        .withPiece(Board.d3, Piece.blackKing)
        .withPiece(Board.c2, Piece.blackPawn)
        .build()

    @Test
    fun renderEmptyPositionTest() {
        val got = BoardRenderer.using(chessSet).toBoardImage().image
        val expected = Images.read(imagesPath + "no-position-no-custom-bg.png")
        assertTrue(Images.areEquals(expected, got))
    }

    @Test
    fun renderEmptyPositionCustomBackgroundTest() {
        val got = BoardRenderer.using(chessSet)
            .withBackground(Board.d4, java.awt.Color.RED)
            .withBackground(Board.e5, java.awt.Color.YELLOW)
            .toBoardImage().image
        val expected = Images.read(imagesPath + "no-position-with-custom-bg.png")
        assertTrue(Images.areEquals(expected, got))
    }

    @Test
    fun renderPositionTest() {
        val got = BoardRenderer.using(chessSet)
            .withPosition(position)
            .toBoardImage().image
        val expected = Images.read(imagesPath + "with-position-no-custom-bg.png")
        assertTrue(Images.areEquals(expected, got))
    }

    @Test
    fun renderPositionCustomBackgroundTest() {
        val got = BoardRenderer.using(chessSet)
            .withPosition(position)
            .withBackground(Board.h8, java.awt.Color.BLUE)
            .toBoardImage().image
        val expected = Images.read(imagesPath + "with-position-with-custom-bg.png")
        assertTrue(Images.areEquals(expected, got))
    }
}