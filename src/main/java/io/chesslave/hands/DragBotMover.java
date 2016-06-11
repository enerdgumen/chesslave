package io.chesslave.hands;

import io.chesslave.model.Square;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Region;

/**
 * A bot able to move pieces through a drag and drop strategy.
 */
public class DragBotMover extends BaseBotMover {

    public DragBotMover(Region boardArea) {
        super(boardArea);
    }

    @Override
    public void move(Square from, Square to) throws MoverException {
        try {
            Location fromLocation = getSquareLocation(from);
            screen.drag(fromLocation);

            Location toLocation = getSquareLocation(to);
            screen.dropAt(toLocation);
        } catch (FindFailed ff) {
            throw new MoverException(ff);
        }
    }
}
