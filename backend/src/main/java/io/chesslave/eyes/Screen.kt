package io.chesslave.eyes;

import rx.Observable;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public interface Screen {

    BufferedImage captureAll();

    BufferedImage capture(Rectangle region);

    Observable<BufferedImage> select(String message);

    void highlight(Rectangle region, long time, TimeUnit unit);
}
