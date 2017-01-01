package io.chesslave.visual.model

import io.chesslave.model.Square

import java.awt.Point
import java.awt.image.BufferedImage

class BoardImage(
    val image: BufferedImage,
    val offset: Point = Point(0, 0),
    val flipped: Boolean = false) {

    fun squareImage(square: Square) = SquareImage(image, square, flipped)
}
