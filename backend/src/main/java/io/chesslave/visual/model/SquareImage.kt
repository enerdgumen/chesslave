package io.chesslave.visual.model

import io.chesslave.model.Board
import io.chesslave.model.Square
import javaslang.Lazy

import java.awt.image.BufferedImage

class SquareImage(board: BufferedImage, private val square: Square, private val flipped: Boolean) {

    private val image: Lazy<BufferedImage>
    private val size: Int

    init {
        this.size = board.width / Board.SIZE
        this.image = Lazy.of { board.getSubimage(left(), top(), size, size) }
    }

    fun square(): Square = square

    fun image(): BufferedImage = image.get()

    fun size(): Int = size

    fun left(): Int = size * horizontalOffset(square.col)

    fun right(): Int = size * (horizontalOffset(square.col) + 1)

    fun top(): Int = size * verticalOffset(square.row)

    fun bottom(): Int = size * (verticalOffset(square.row) + 1)

    fun middleX(): Int = left() + size / 2

    fun middleY(): Int = top() + size / 2

    private fun horizontalOffset(col: Int): Int = if (flipped) Board.SIZE - 1 - col else col

    private fun verticalOffset(row: Int): Int = if (flipped) row else Board.SIZE - 1 - row

    override fun toString() = square.toString()
}
