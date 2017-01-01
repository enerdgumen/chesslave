package io.chesslave.eyes

import io.chesslave.model.Board
import io.chesslave.model.Piece
import io.chesslave.model.Position
import io.chesslave.model.Square
import io.chesslave.visual.model.BoardImage

class PositionRecogniser(private val vision: Vision, private val config: BoardConfiguration) {

    /**
     * Detects the position represented by the given board image.
     */
    fun position(board: BoardImage): Position {
        val recogniser = vision.recognise(board.image)
        val position = Position.Builder()
        Piece.all.forEach { piece ->
            findAllPieces(recogniser, piece).forEach { square ->
                position.withPiece(square, piece)
            }
        }
        return position.build()
    }

    private fun findAllPieces(recogniser: Vision.Recogniser, piece: Piece) =
        recogniser.matches(config.pieces.apply(piece))
            .map { match ->
                val region = match.region()
                val col = (Board.SIZE * region.getCenterX() / match.source().getWidth()).toInt()
                val row = Board.SIZE - (Board.SIZE * region.getCenterY() / match.source().getHeight()).toInt()
                Square(col, row - 1)
            }
            .toSet()
}
