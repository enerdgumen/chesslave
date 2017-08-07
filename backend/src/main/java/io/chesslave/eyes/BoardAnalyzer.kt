package io.chesslave.eyes

import io.chesslave.model.Board
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
        val withoutBackground = Images.crop(userImage) { it == bgColor }
        val chars = detectCharacteristics(withoutBackground)
        val withoutBorder = Images.crop(withoutBackground) { it != chars.whiteColor && it != chars.blackColor }
        val board = BoardImage(withoutBorder)
        val pieces = HashMap.of<Piece, BufferedImage>(
            Piece.blackPawn, cropPiece(board, Board.b7),
            Piece.blackKnight, cropPiece(board, Board.g8),
            Piece.blackBishop, cropPiece(board, Board.c8),
            Piece.blackRook, cropPiece(board, Board.a8),
            Piece.blackQueen, Images.fillOuterBackground(cropPiece(board, Board.d8), chars.whiteColor),
            Piece.blackKing, cropPiece(board, Board.e8),
            Piece.whitePawn, cropPiece(board, Board.b2),
            Piece.whiteKnight, cropPiece(board, Board.g1),
            Piece.whiteBishop, cropPiece(board, Board.c1),
            Piece.whiteRook, cropPiece(board, Board.a1),
            Piece.whiteQueen, Images.fillOuterBackground(cropPiece(board, Board.d1), chars.blackColor),
            Piece.whiteKing, cropPiece(board, Board.e1))
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
        val squareImage = board.squareImage(square).image
        return Images.crop(squareImage) { it == squareImage.getRGB(0, 0) }
    }
}
