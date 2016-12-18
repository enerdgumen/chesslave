package io.chesslave.visual.model

import io.chesslave.model.Square

import java.awt.Point
import java.awt.image.BufferedImage

class BoardImage @JvmOverloads constructor(
    private val image: BufferedImage,
    private val offset: Point = Point(0, 0),
    private val flipped: Boolean = false) {

    constructor(image: BufferedImage, flipped: Boolean) : this(image, Point(0, 0), flipped) {
    }

    fun image(): BufferedImage = image

    fun offset(): Point = offset

    fun flipped(): Boolean = flipped

    fun squareImage(square: Square): SquareImage {
        return SquareImage(image, square, flipped)
    }
}
