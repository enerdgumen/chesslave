package io.chesslave.eyes

import io.chesslave.model.Color
import io.chesslave.model.Piece
import io.chesslave.model.Piece.Type
import io.chesslave.model.Square
import io.chesslave.visual.Images
import io.chesslave.visual.model.BoardImage
import javaslang.collection.HashMap
import java.awt.image.BufferedImage

class BoardAnalyzer {

    fun analyze(userImage: BufferedImage): BoardConfiguration {
        val bgColor = userImage.getRGB(0, 0)
        val withoutBackground = Images.crop(userImage) { it === bgColor }
        val chars = detectCharacteristics(withoutBackground)
        val withoutBorder = Images.crop(withoutBackground) { it !== chars.whiteColor && it !== chars.blackColor }
        val board = BoardImage(withoutBorder)
        val pieces = HashMap.of<Piece, BufferedImage>(
            Piece(Type.PAWN, Color.BLACK), cropPiece(board, Square.of("b7")),
            Piece(Type.KNIGHT, Color.BLACK), cropPiece(board, Square.of("g8")),
            Piece(Type.BISHOP, Color.BLACK), cropPiece(board, Square.of("c8")),
            Piece(Type.ROOK, Color.BLACK), cropPiece(board, Square.of("a8")),
            Piece(Type.QUEEN, Color.BLACK), Images.fillOuterBackground(cropPiece(board, Square.of("d8")), chars.whiteColor),
            Piece(Type.KING, Color.BLACK), cropPiece(board, Square.of("e8")),
            Piece(Type.PAWN, Color.WHITE), cropPiece(board, Square.of("b2")),
            Piece(Type.KNIGHT, Color.WHITE), cropPiece(board, Square.of("g1")),
            Piece(Type.BISHOP, Color.WHITE), cropPiece(board, Square.of("c1")),
            Piece(Type.ROOK, Color.WHITE), cropPiece(board, Square.of("a1")),
            Piece(Type.QUEEN, Color.WHITE), Images.fillOuterBackground(cropPiece(board, Square.of("d1")), chars.blackColor),
            Piece(Type.KING, Color.WHITE), cropPiece(board, Square.of("e1")))
        return BoardConfiguration(board, pieces, chars, false)
    }

    private fun detectCharacteristics(board: BufferedImage): BoardConfiguration.Characteristics {
        val cellWidth = board.width / 8
        val cellHeight = board.height / 8
        val whiteColor = board.getRGB(cellWidth / 2, (cellHeight * 4.5).toInt())
        val blackColor = board.getRGB(cellWidth / 2, (cellHeight * 3.5).toInt())
        assert(whiteColor != blackColor) { "White and black cells should be different, found ${whiteColor}" }
        return BoardConfiguration.Characteristics(cellWidth, cellHeight, whiteColor, blackColor)
    }

    private fun cropPiece(board: BoardImage, square: Square): BufferedImage {
        val squareImage = board.squareImage(square).image()
        return Images.crop(squareImage) { it === squareImage.getRGB(0, 0) }
    }
}
