package io.chesslave.hands

import io.chesslave.model.Square
import org.junit.Assume.assumeTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.doThrow
import org.mockito.MockitoAnnotations
import java.awt.Desktop
import java.awt.Point

// TODO: fix tests
@Ignore
class FailingDragBotMoverTest {

    @Mock
    lateinit var pointer: Pointer
    @InjectMocks
    lateinit var botMover: DragBotMover

    @Before
    fun setUp() {
        assumeTrue(Desktop.isDesktopSupported())
        MockitoAnnotations.initMocks(this)
    }

    @Test(expected = RuntimeException::class)
    fun moveTest() {
        doThrow(RuntimeException::class.java).`when`<Pointer>(pointer).dragFrom(any(Point::class.java))
        botMover.move(Square.of("a1"), Square.of("b3"))
    }
}
