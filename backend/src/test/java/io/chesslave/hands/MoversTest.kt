package io.chesslave.hands

import io.chesslave.model.Board
import io.chesslave.model.Square
import org.junit.Assert.assertEquals
import org.junit.Test
import java.awt.Point

class MoveByClickTest {
    @Test
    fun itShouldMoveByClick() {
        val squarePoints = { square: Square -> Point(square.col, square.row) }
        val mover = moveByClick(squarePoints)
        val actions = mover(Board.a1, Board.c2)
        assertEquals(CompositeAction(listOf(Click(Point(0, 0)), (Click(Point(2, 1))))), actions)
    }
}

class MoveByDragTest {
    @Test
    fun itShouldMoveByDrag() {
        val squarePoints = { square: Square -> Point(square.col, square.row) }
        val mover = moveByDrag(squarePoints)
        val actions = mover(Board.a1, Board.c2)
        assertEquals(CompositeAction(listOf(DragFrom(Point(0, 0)), (DropAt(Point(2, 1))))), actions)
    }
}