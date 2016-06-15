package io.chesslave.hands;

import java.awt.Point;

public interface Mouse {

    void moveTo(Point coords);

    void click(Point coords);

    void dragFrom(Point coords);

    void dropAt(Point coords);
}
