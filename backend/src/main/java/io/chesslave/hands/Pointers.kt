package io.chesslave.hands

import java.awt.Point

interface Pointer {

    fun moveTo(coords: Point)

    fun click(coords: Point)

    fun dragFrom(coords: Point)

    fun dropAt(coords: Point)
}

interface PointerAction {
    fun execute(pointer: Pointer): Unit
}

data class Click(val target: Point) : PointerAction {
    override fun execute(pointer: Pointer) = pointer.click(target)
}

data class MoveTo(val target: Point) : PointerAction {
    override fun execute(pointer: Pointer) = pointer.moveTo(target)
}

data class DragFrom(val target: Point) : PointerAction {
    override fun execute(pointer: Pointer) = pointer.dragFrom(target)
}

data class DropAt(val target: Point) : PointerAction {
    override fun execute(pointer: Pointer) = pointer.dropAt(target)
}

data class CompositeAction(val actions: List<PointerAction>) : PointerAction {
    override fun execute(pointer: Pointer) = actions.forEach { it.execute(pointer) }
}