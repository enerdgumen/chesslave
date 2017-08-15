package io.chesslave.eyes

import io.chesslave.model.Board
import io.chesslave.model.Piece
import io.chesslave.model.Square
import io.chesslave.visual.*
import io.chesslave.visual.model.BoardImage
import io.vavr.Tuple
import io.vavr.collection.HashMap
import java.awt.Color
import java.awt.Point
import java.awt.Rectangle
import java.awt.image.BufferedImage

fun analyzeBoardImage(image: BufferedImage): BoardConfiguration {
    val boardRect = image.detectBoard(Point(image.width / 2, image.height / 2)) ?: throw IllegalArgumentException("Board not found")
    val board = BoardImage(image.getSubimage(boardRect.x, boardRect.y, boardRect.width, boardRect.height))
    val chars = detectCharacteristics(board.image)
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

internal fun BufferedImage.detectBoard(point: Point): Rectangle? {
    val square = detectSquare(point) ?: return null
    val left = generateSequence(square) { detectSquare(Point(it.x - square.width, it.y)) }.last().x
    val right = generateSequence(square) { detectSquare(Point(it.x + square.width, it.y)) }.last().xw
    val top = generateSequence(square) { detectSquare(Point(it.x, it.y - square.height)) }.last().y
    val bottom = generateSequence(square) { detectSquare(Point(it.x, it.y + square.height)) }.last().yh
    val width = right - left
    val height = bottom - top
    val isBoard = almostEqualsLength(width / 8, square.width) && almostEqualsLength(height / 8, square.height)
    return if (isBoard) Rectangle(left, top, width, height) else null
}

private val Rectangle.xw: Int get() = x + width
private val Rectangle.yh: Int get() = y + height

internal fun BufferedImage.detectSquare(point: Point): Rectangle? =
    this.detectRectangle(point)?.let { rect ->
        if (almostEqualsLength(rect.height, rect.width)) rect else null
    }

private fun BufferedImage.detectRectangle(point: Point): Rectangle? {
    if (point !in this) return null
    val color = this.getColor(point)
    val up = this.moveFrom(point, Movement.UP) { Colors.areSimilar(color, Color(it)) }
    val down = this.moveFrom(point, Movement.DOWN) { Colors.areSimilar(color, Color(it)) }
    val left = this.moveFrom(point, Movement.LEFT) { Colors.areSimilar(color, Color(it)) }
    val right = this.moveFrom(point, Movement.RIGHT) { Colors.areSimilar(color, Color(it)) }
    val height = down.y - up.y - 1
    val width = right.x - left.x - 1
    return Rectangle(left.x + 1, up.y + 1, width, height)
}

private fun almostEqualsLength(a: Int, b: Int, tolerance: Double = .1): Boolean =
    Math.abs(a.toDouble() / b.toDouble() - 1) < tolerance