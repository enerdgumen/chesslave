package io.chesslave.eyes

import java.awt.Color

object Colors {

    @JvmStatic @JvmOverloads
    fun areSimilar(a: Color, b: Color, tolerance: Double = .02): Boolean = Math.abs(hue(a) - hue(b)) < tolerance

    private fun hue(color: Color): Float {
        val hsb = FloatArray(3)
        Color.RGBtoHSB(color.red, color.green, color.blue, hsb)
        return hsb[0]
    }
}