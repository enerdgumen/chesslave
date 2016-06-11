package io.chesslave.hands;

import io.chesslave.model.Square;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Region;

/**
 * A bot able to move pieces through a point and click strategy.
 */
public class ClickBotMover extends BaseBotMover {

    public ClickBotMover(Region boardArea) {
        super(boardArea);
    }

    @Override
    public void move(Square from, Square to) throws MoverException {
        try {
            Location fromLocation = getSquareLocation(from);
            screen.click(fromLocation);

            Location toLocation = getSquareLocation(to);
            screen.click(toLocation);
        } catch (FindFailed ff) {
            throw new MoverException(ff);
        }
    }
}
