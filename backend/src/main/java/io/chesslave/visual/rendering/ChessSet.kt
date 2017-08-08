package io.chesslave.visual.rendering

import io.chesslave.visual.Images
import io.chesslave.model.Color
import io.chesslave.model.Piece
import io.chesslave.model.Piece.Type
import io.chesslave.visual.model.BoardImage
import io.vavr.Tuple
import io.vavr.collection.HashMap
import io.vavr.collection.Map

import java.awt.image.BufferedImage

class ChessSet private constructor(val board: BoardImage, val pieces: Map<Piece, BufferedImage>) {
    companion object {
        private val COLOR_TO_CODE = HashMap.of<Color, String>(
            Color.WHITE, "w",
            Color.BLACK, "b")
        private val PIECE_TYPE_TO_CODE = HashMap.of<Type, String>(
            Type.BISHOP, "b",
            Type.KING, "k",
            Type.KNIGHT, "n",
            Type.PAWN, "p",
            Type.QUEEN, "q",
            Type.ROOK, "r")

        // TODO: path could not end with /
        fun read(path: String): ChessSet {
            val board = BoardImage(Images.read(path + "empty-board.png"))
            val pieces = Piece.all.toMap { Tuple.of(it, Images.read(path + ChessSet.name(it) + ".png")) }
            return ChessSet(board, pieces)
        }

        private fun name(piece: Piece) = COLOR_TO_CODE.apply(piece.color) + PIECE_TYPE_TO_CODE.apply(piece.type)
    }
}
