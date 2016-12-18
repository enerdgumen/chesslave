package io.chesslave.hands

import java.awt.Point

interface Pointer {

    fun moveTo(coords: Point)

    fun click(coords: Point)

    fun dragFrom(coords: Point)

    fun dropAt(coords: Point)
}
