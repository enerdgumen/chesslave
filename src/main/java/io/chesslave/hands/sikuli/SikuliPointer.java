package io.chesslave.hands.sikuli;

import io.chesslave.hands.Pointer;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;

import java.awt.Point;

/**
 * An abstraction of mouse based on Sikuli.
 */
public class SikuliPointer implements Pointer {
    private Screen screen;

    public SikuliPointer() {
        screen = Screen.getPrimaryScreen();
    }

    public SikuliPointer(final int screenId) {
        screen = Screen.getScreen(screenId);
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
            throw new RuntimeException("Unable to drag starting at " + coords, ff);
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

    int getScreenId() {
        return screen.getID();
    }

    private Location location(Point coords) {
        return new Location(coords.x, coords.y);
    }
}
