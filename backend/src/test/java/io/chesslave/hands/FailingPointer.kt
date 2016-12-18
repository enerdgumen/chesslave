package io.chesslave.hands

import java.awt.Point

open class FailingPointer : Pointer {

    override fun moveTo(coords: Point) {
        throw UnsupportedOperationException()
    }

    override fun click(coords: Point) {
        throw UnsupportedOperationException()
    }

    override fun dragFrom(coords: Point) {
        throw UnsupportedOperationException()
    }

    override fun dropAt(coords: Point) {
        throw UnsupportedOperationException()
    }
}