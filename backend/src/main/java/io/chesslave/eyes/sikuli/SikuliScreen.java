package io.chesslave.eyes.sikuli;

import io.chesslave.eyes.Screen;
import org.sikuli.script.Location;
import org.sikuli.util.OverlayCapturePrompt;
import org.sikuli.util.ScreenHighlighter;
import rx.Observable;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class SikuliScreen implements Screen {

    private final org.sikuli.script.Screen screen = org.sikuli.script.Screen.all();

    @Override
    public BufferedImage captureAll() {
        return screen.capture().getImage();
    }

    @Override
    public BufferedImage capture(Rectangle region) {
        return screen.capture(region).getImage();
    }

    @Override
    public Observable<BufferedImage> select(String message) {
        return Observable.create(result -> {
            final OverlayCapturePrompt overlay = new OverlayCapturePrompt(screen, subject -> {
                final OverlayCapturePrompt me = (OverlayCapturePrompt) subject;
                result.onNext(me.getSelection().getImage());
            });
            overlay.prompt(message);
        });
    }

    @Override
    public void highlight(Rectangle region, long time, TimeUnit unit) {
        final ScreenHighlighter overlay = new ScreenHighlighter(screen, null);
        overlay.highlight(screen.newRegion(new Location(region.x, region.y), region.width, region.height),
                unit.convert(time, TimeUnit.SECONDS));
    }
}
