package io.chesslave.hands.sikuli

import org.junit.Assume.assumeTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Matchers.any
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.sikuli.script.FindFailed
import org.sikuli.script.Location
import org.sikuli.script.Screen
import java.awt.Desktop
import java.awt.Point

// TODO: these tests fail
@Ignore
class SikuliPointerTest {

    @Mock
    lateinit var screen: Screen
    @InjectMocks
    lateinit var mockedPointer: SikuliPointer
    val realPointer = SikuliPointer()
    val point = Point(0, 0)

    @Before
    fun setUp() {
        assumeTrue(Desktop.isDesktopSupported())
        MockitoAnnotations.initMocks(this)
    }

    @Test(expected = RuntimeException::class)
    fun moveToTest() {
        // TODO: rewrite real pointer test as integration test
        realPointer.moveTo(point)

        `when`(screen.mouseMove(any(Location::class.java))).thenThrow(FindFailed::class.java)
        mockedPointer.moveTo(point)
    }

    @Test(expected = RuntimeException::class)
    fun clickTest() {
        realPointer.click(point)

        `when`(screen.click(any(Location::class.java))).thenThrow(FindFailed::class.java)
        mockedPointer.click(point)
    }

    @Test(expected = RuntimeException::class)
    fun dragFromTest() {
        realPointer.dragFrom(point)

        `when`(screen.drag(any(Location::class.java))).thenThrow(FindFailed::class.java)
        mockedPointer.dragFrom(point)
    }

    @Test(expected = RuntimeException::class)
    fun dropAtTest() {
        realPointer.dropAt(point)

        `when`(screen.dropAt(any(Location::class.java))).thenThrow(FindFailed::class.java)
        mockedPointer.dropAt(point)
    }
}
