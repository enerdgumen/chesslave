package io.chesslave.recognition;

import io.chesslave.Ensure;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Region;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;
import org.sikuli.api.visual.StyleBuilder;
import org.sikuli.api.visual.element.BoxElement;

public class BoardRecogniser implements Supplier<ScreenRegion> {

    private BufferedImage boardImage;

    public BoardRecogniser(BufferedImage initialImage) {
        this.boardImage = initialImage;
    }

    @Override
    public ScreenRegion get() {
        final DesktopScreenRegion desktop = new DesktopScreenRegion();
        final ImageTarget target = new ImageTarget(boardImage);
        final ScreenRegion region = Ensure.notNull(desktop.find(target), "board detection failed");
        boardImage = region.getLastCapturedImage();
        return region;
    }

    private static void highlight(Region region) {
        final Canvas canvas = new DesktopCanvas();
        final Rectangle r = region.getBounds();
        final BoxElement box = new BoxElement();
        box.x = r.x;
        box.y = r.y;
        box.width = r.width;
        box.height = r.height;
        canvas.add(box);
        final StyleBuilder style = new StyleBuilder(canvas, box);
        style.display(1);
    }
}
