package io.chesslave.hands;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;

import io.chesslave.model.Square;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.Desktop;
import java.awt.Point;

@RunWith(MockitoJUnitRunner.class)
public class FailingDragBotMoverTest {

    @Mock
    private Pointer pointer;

    @InjectMocks
    private DragBotMover botMover;

    @Before
    public void setUp() {
        try {
            assumeTrue(Desktop.isDesktopSupported());
        } catch (Throwable t) {
            assumeTrue(false);
        }
    }

    @Test(expected = RuntimeException.class)
    public void moveTest() {
        doThrow(RuntimeException.class).when(pointer).dragFrom(any(Point.class));
        botMover.move(Square.of("a1"), Square.of("b3"));
        fail("Move should fail");
    }
}
