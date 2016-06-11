package io.chesslave.eyes;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public interface Screen {

    BufferedImage captureAll();

    BufferedImage capture(Rectangle region);

    void highlight(Rectangle region, long time, TimeUnit unit);
}
