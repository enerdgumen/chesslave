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
        .withPiece(Board.a1, Piece(Type.ROOK, Color.WHITE))
        .withPiece(Board.b2, Piece(Type.KNIGHT, Color.WHITE))
        .withPiece(Board.c3, Piece(Type.BISHOP, Color.WHITE))
        .withPiece(Board.d4, Piece(Type.QUEEN, Color.WHITE))
        .withPiece(Board.e5, Piece(Type.KING, Color.WHITE))
        .withPiece(Board.f6, Piece(Type.PAWN, Color.WHITE))
        .withPiece(Board.h8, Piece(Type.ROOK, Color.BLACK))
        .withPiece(Board.g7, Piece(Type.KNIGHT, Color.BLACK))
        .withPiece(Board.f5, Piece(Type.BISHOP, Color.BLACK))
        .withPiece(Board.e4, Piece(Type.QUEEN, Color.BLACK))
        .withPiece(Board.d3, Piece(Type.KING, Color.BLACK))
        .withPiece(Board.c2, Piece(Type.PAWN, Color.BLACK))
        .build()

    @Test
    fun renderEmptyPositionTest() {
        val got = BoardRenderer.using(chessSet).toBoardImage().image()
        val expected = Images.read(imagesPath + "no-position-no-custom-bg.png")
        assertTrue(Images.areEquals(expected, got))
    }

    @Test
    fun renderEmptyPositionCustomBackgroundTest() {
        val got = BoardRenderer.using(chessSet)
            .withBackground(Board.d4, java.awt.Color.RED)
            .withBackground(Board.e5, java.awt.Color.YELLOW)
            .toBoardImage().image()
        val expected = Images.read(imagesPath + "no-position-with-custom-bg.png")
        assertTrue(Images.areEquals(expected, got))
    }

    @Test
    fun renderPositionTest() {
        val got = BoardRenderer.using(chessSet)
            .withPosition(position)
            .toBoardImage().image()
        val expected = Images.read(imagesPath + "with-position-no-custom-bg.png")
        assertTrue(Images.areEquals(expected, got))
    }

    @Test
    fun renderPositionCustomBackgroundTest() {
        val got = BoardRenderer.using(chessSet)
            .withPosition(position)
            .withBackground(Board.h8, java.awt.Color.BLUE)
            .toBoardImage().image()
        val expected = Images.read(imagesPath + "with-position-with-custom-bg.png")
        assertTrue(Images.areEquals(expected, got))
    }
}