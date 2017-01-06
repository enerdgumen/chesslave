package io.chesslave.eyes

import io.chesslave.model.Piece
import io.chesslave.model.Position
import io.chesslave.model.Square
import io.chesslave.visual.rendering.BoardRenderer
import io.chesslave.visual.rendering.ChessSet
import org.junit.Assert.assertEquals

class EmptySquareRecogniserTest(chessSet: ChessSet) : SinglePieceRecognitionTest(chessSet) {

    override fun withPieceOnSquare(square: Square, piece: Piece) {
        val position = Position.Builder().withPiece(square, piece).build()
        val board = BoardRenderer(chessSet).withPosition(position).render()
        val got = EmptySquareRecogniser.isEmpty(board.squareImage(square))
        assertEquals(false, got)
    }

    override fun withEmptySquare(square: Square) {
        val position = Position.Builder().build()
        val board = BoardRenderer(chessSet).withPosition(position).render()
        val got = EmptySquareRecogniser.isEmpty(board.squareImage(square))
        assertEquals(true, got)
    }
}