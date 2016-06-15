package io.chesslave.hands.sikuli;

import io.chesslave.hands.Mouse;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

import java.awt.Point;

/**
 * An abstraction of mouse based on Sikuli.
 */
public class SikuliMouse implements Mouse {
    private final Screen screen;

    public SikuliMouse() {
        // TODO: similar to SikuliScreen, we need to detect the proper screen
        screen = Screen.getPrimaryScreen();
    }

    @Override
    public void moveTo(Point coords) {
        try {
            screen.mouseMove(location(coords));
        } catch (FindFailed ff) {
            throw new RuntimeException("Unable to move to " + coords, ff);
        }
    }

    @Override
    public void click(Point coords) {
        try {
            screen.click(location(coords));
        } catch (FindFailed ff) {
            throw new RuntimeException("Unable to click on " + coords, ff);
        }
    }

    @Override
    public void dragFrom(Point coords) {
        try {
            screen.drag(location(coords));
        } catch (FindFailed ff) {
            throw new RuntimeException("Unable to dragFrom starting at " + coords, ff);
        }
    }

    @Override
    public void dropAt(Point coords) {
        try {
            screen.dropAt(location(coords));
        } catch (FindFailed ff) {
            throw new RuntimeException("Unable to drop at " + coords, ff);
        }
    }

    private Location location(Point coords) {
        return new Location(coords.x, coords.y);
    }
}
