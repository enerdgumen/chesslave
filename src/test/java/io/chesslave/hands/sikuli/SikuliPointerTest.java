package io.chesslave.hands.sikuli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

import java.awt.Desktop;
import java.awt.Point;

@RunWith(MockitoJUnitRunner.class)
public class SikuliPointerTest {

    @Mock
    private Screen screen;

    @InjectMocks
    private SikuliPointer mockedPointer;

    private SikuliPointer realPointer;
    private Point point;

    @Before
    public void setUp() {
        assumeTrue(Desktop.isDesktopSupported());

        realPointer = new SikuliPointer();
        point = new Point(0, 0);
    }

    @Test
    public void screenIdConstructorTest() {
        final SikuliPointer otherPointer = new SikuliPointer(1);
        assertEquals(realPointer.getScreenId(), otherPointer.getScreenId());
    }

    @Test(expected = RuntimeException.class)
    public void moveToTest() throws FindFailed {
        realPointer.moveTo(point);

        when(screen.mouseMove(any(Location.class))).thenThrow(FindFailed.class);
        mockedPointer.moveTo(point);
        fail("Moving the pointer should fail");
    }

    @Test(expected = RuntimeException.class)
    public void clickTest() throws FindFailed {
        realPointer.click(point);

        when(screen.click(any(Location.class))).thenThrow(FindFailed.class);
        mockedPointer.click(point);
        fail("Clicking should fail");
    }

    @Test(expected = RuntimeException.class)
    public void dragFromTest() throws FindFailed {
        realPointer.dragFrom(point);

        when(screen.drag(any(Location.class))).thenThrow(FindFailed.class);
        mockedPointer.dragFrom(point);
        fail("Start dragging at pointer position should fail");
    }

    @Test(expected = RuntimeException.class)
    public void dropAtTest() throws FindFailed {
        realPointer.dropAt(point);

        when(screen.dropAt(any(Location.class))).thenThrow(FindFailed.class);
        mockedPointer.dropAt(point);
        fail("Dropping at pointer position should fail");
    }
}
