package io.chesslave.hands

import io.chesslave.model.Square
import io.chesslave.visual.model.BoardImage
import java.awt.Point

class SquarePoints(private val board: BoardImage) {

    fun of(square: Square): Point =
        with(board.squareImage(square)) {
            Point(
                board.offset().x + middleX(),
                board.offset().y + middleY())
        }
}