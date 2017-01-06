package io.chesslave.hands

import io.chesslave.model.Square
import io.chesslave.visual.model.BoardImage
import java.awt.Point

class MoverException(from: Square, to: Square, cause: RuntimeException)
    : RuntimeException("Cannot move piece from $from to $to", cause)

/**
 * Move a piece.
 *
 * @param from starting square
 * @param to destination square
 */
typealias Mover = (from: Square, to: Square) -> PointerAction

/**
 * A bot able to move pieces through a point and click strategy.
 */
fun moveByClick(points: SquarePoints): Mover = { from: Square, to: Square ->
    CompositeAction(listOf(
        Click(points(from)),
        Click(points(to))))
}

/**
 * A bot able to move pieces through a drag and drop strategy.
 */
fun moveByDrag(points: SquarePoints): Mover = { from: Square, to: Square ->
    CompositeAction(listOf(
        DragFrom(points(from)),
        DropAt(points(to))))
}

/**
 * Get the point of the square on the screen.
 */
typealias SquarePoints = (Square) -> Point

fun squarePoints(board: BoardImage): SquarePoints = { square ->
    with(board.squareImage(square)) { Point(board.offset.x + middleX(), board.offset.y + middleY()) }
}