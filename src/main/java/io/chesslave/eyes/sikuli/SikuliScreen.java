package io.chesslave.eyes.sikuli;

import io.chesslave.eyes.Screen;
import org.sikuli.script.Location;
import org.sikuli.util.ScreenHighlighter;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class SikuliScreen implements Screen {

    // TODO: detect the proper screen
    private static final int DEFAULT_SCREEN_ID = 0;

    private final org.sikuli.script.Screen screen = org.sikuli.script.Screen.getScreen(DEFAULT_SCREEN_ID);

    @Override
    public BufferedImage captureAll() {
        screen.setRect(org.sikuli.script.Screen.getBounds(DEFAULT_SCREEN_ID));
        return captureScreen();
    }

    @Override
    public BufferedImage capture(Rectangle region) {
        screen.setRect(region);
        return captureScreen();
    }

    @Override
    public void highlight(Rectangle region, long time, TimeUnit unit) {
        final ScreenHighlighter overlay = new ScreenHighlighter(screen, null);
        overlay.highlight(screen.newRegion(new Location(region.x, region.y), region.width, region.height),
                unit.convert(time, TimeUnit.SECONDS));
    }

    private BufferedImage captureScreen() {
        return screen.capture().getImage();
    }
}
