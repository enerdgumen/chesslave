package io.chesslave.eyes

import io.chesslave.visual.rendering.ChessSet
import org.junit.Assume
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.awt.Desktop

@RunWith(Parameterized::class)
abstract class BaseRecognitionTest(val chessSet: ChessSet) {

    companion object {
        @Parameterized.Parameters
        @JvmStatic fun data(): Collection<Array<Any>> = listOf(
            arrayOf<Any>(ChessSet.read("/images/set1/")),
            arrayOf<Any>(ChessSet.read("/images/set3/"))
        )
    }

    @Before
    fun assumeIsDesktopSupported() {
        Assume.assumeTrue(Desktop.isDesktopSupported())
    }
}
