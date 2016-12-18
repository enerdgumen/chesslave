package io.chesslave.hands

import io.chesslave.hands.sikuli.SikuliPointer
import io.chesslave.model.Square
import io.chesslave.visual.model.BoardImage
import org.sikuli.script.Screen
import java.awt.Point

/**
 * Base implementation of a bot which can move pieces on the board.
 */
abstract class BaseBotMover(private val board: BoardImage) : Mover {

    protected fun getSquareCoords(square: Square): Point {
        val squareImage = board.squareImage(square)
        return Point(
            board.offset().x + squareImage.middleX(),
            board.offset().y + squareImage.middleY())
    }
}