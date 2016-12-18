package io.chesslave.hands.sikuli

import io.chesslave.hands.Pointer
import org.sikuli.script.Location
import org.sikuli.script.Screen
import java.awt.Point

/**
 * An abstraction of mouse based on Sikuli.
 */
class SikuliPointer(private val screen: Screen) : Pointer {

    override fun moveTo(coords: Point) {
        screen.mouseMove(location(coords))
    }

    override fun click(coords: Point) {
        screen.click(location(coords))
    }

    override fun dragFrom(coords: Point) {
        screen.drag(location(coords))
    }

    override fun dropAt(coords: Point) {
        screen.dropAt(location(coords))
    }

    private fun location(coords: Point) = Location(coords.x, coords.y)
}
