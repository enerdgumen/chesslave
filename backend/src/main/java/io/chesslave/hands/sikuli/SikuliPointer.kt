package io.chesslave.hands.sikuli

import io.chesslave.hands.Pointer
import org.sikuli.script.FindFailed
import org.sikuli.script.Location
import org.sikuli.script.Screen

import java.awt.Point

/**
 * An abstraction of mouse based on Sikuli.
 */
class SikuliPointer(private val screen: Screen = Screen.getPrimaryScreen()) : Pointer {

    override fun moveTo(coords: Point) {
        try {
            screen.mouseMove(location(coords))
        } catch (ff: FindFailed) {
            throw RuntimeException("Unable to move to " + coords, ff)
        }
    }

    override fun click(coords: Point) {
        try {
            screen.click(location(coords))
        } catch (ff: FindFailed) {
            throw RuntimeException("Unable to click on " + coords, ff)
        }
    }

    override fun dragFrom(coords: Point) {
        try {
            screen.drag(location(coords))
        } catch (ff: FindFailed) {
            throw RuntimeException("Unable to drag starting at " + coords, ff)
        }
    }

    override fun dropAt(coords: Point) {
        try {
            screen.dropAt(location(coords))
        } catch (ff: FindFailed) {
            throw RuntimeException("Unable to drop at " + coords, ff)
        }
    }

    private fun location(coords: Point) = Location(coords.x, coords.y)
}
