package io.chesslave.visual.rendering

import io.chesslave.model.Color
import io.chesslave.model.Piece
import io.chesslave.model.Piece.Type
import io.chesslave.model.Position
import io.chesslave.model.Square
import io.chesslave.visual.Images
import org.junit.Assert.assertTrue
import org.junit.Test

class BoardRendererTest {

    // TODO: move images into this package
    val imagesPath = "/images/visual/rendering/"
    val chessSet = ChessSet.read("/images/set1/")
    val position = Position.Builder()
        .withPiece(Square.of("a1"), Piece(Type.ROOK, Color.WHITE))
        .withPiece(Square.of("b2"), Piece(Type.KNIGHT, Color.WHITE))
        .withPiece(Square.of("c3"), Piece(Type.BISHOP, Color.WHITE))
        .withPiece(Square.of("d4"), Piece(Type.QUEEN, Color.WHITE))
        .withPiece(Square.of("e5"), Piece(Type.KING, Color.WHITE))
        .withPiece(Square.of("f6"), Piece(Type.PAWN, Color.WHITE))
        .withPiece(Square.of("h8"), Piece(Type.ROOK, Color.BLACK))
        .withPiece(Square.of("g7"), Piece(Type.KNIGHT, Color.BLACK))
        .withPiece(Square.of("f5"), Piece(Type.BISHOP, Color.BLACK))
        .withPiece(Square.of("e4"), Piece(Type.QUEEN, Color.BLACK))
        .withPiece(Square.of("d3"), Piece(Type.KING, Color.BLACK))
        .withPiece(Square.of("c2"), Piece(Type.PAWN, Color.BLACK))
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
            .withBackground(Square.of("d4"), java.awt.Color.RED)
            .withBackground(Square.of("e5"), java.awt.Color.YELLOW)
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
            .withBackground(Square.of("h8"), java.awt.Color.BLUE)
            .toBoardImage().image()
        val expected = Images.read(imagesPath + "with-position-with-custom-bg.png")
        assertTrue(Images.areEquals(expected, got))
    }
}