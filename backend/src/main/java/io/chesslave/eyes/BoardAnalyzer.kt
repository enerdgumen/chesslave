package io.chesslave.eyes

import io.chesslave.model.Board
import io.chesslave.model.Color
import io.chesslave.model.Piece
import io.chesslave.model.Piece.Type
import io.chesslave.model.Square
import io.chesslave.visual.Images
import io.chesslave.visual.model.BoardImage
import io.vavr.Tuple
import io.vavr.Tuple2
import io.vavr.collection.HashMap
import java.awt.image.BufferedImage

class BoardAnalyzer {

    fun analyze(userImage: BufferedImage): BoardConfiguration {
        val bgColor = userImage.getRGB(0, 0)
        val withoutBackground = Images.crop(userImage) { it == bgColor }
        val chars = detectCharacteristics(withoutBackground)
        val withoutBorder = Images.crop(withoutBackground) { it != chars.whiteColor && it != chars.blackColor }
        val board = BoardImage(withoutBorder)
        val pieces = HashMap.ofEntries(
            Tuple.of(Piece.blackPawn, cropPiece(board, Board.b7)),
            Tuple.of(Piece.blackKnight, cropPiece(board, Board.g8)),
            Tuple.of(Piece.blackBishop, cropPiece(board, Board.c8)),
            Tuple.of(Piece.blackRook, cropPiece(board, Board.a8)),
            Tuple.of(Piece.blackQueen, Images.fillOuterBackground(cropPiece(board, Board.d8), chars.whiteColor)),
            Tuple.of(Piece.blackKing, cropPiece(board, Board.e8)),
            Tuple.of(Piece.whitePawn, cropPiece(board, Board.b2)),
            Tuple.of(Piece.whiteKnight, cropPiece(board, Board.g1)),
            Tuple.of(Piece.whiteBishop, cropPiece(board, Board.c1)),
            Tuple.of(Piece.whiteRook, cropPiece(board, Board.a1)),
            Tuple.of(Piece.whiteQueen, Images.fillOuterBackground(cropPiece(board, Board.d1), chars.blackColor)),
            Tuple.of(Piece.whiteKing, cropPiece(board, Board.e1)))
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
