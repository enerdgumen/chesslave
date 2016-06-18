package io.chesslave.eyes;

import io.chesslave.model.*;
import javaslang.Tuple2;
import javaslang.collection.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoveRecogniser {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Move detect(Position previous, Position current) {
        final Set<Tuple2<Square, Piece>> from = previous.toSet().diff(current.toSet());
        final Set<Tuple2<Square, Piece>> to = current.toSet().diff(previous.toSet());
        logger.debug("changed squares: from={}, to={}", from, to);
        if (to.length() != 1) {
            throw new UnexpectedMoveException(
                    String.format("cannot detect move (from: %s, to: %s)", previous, current));
        }
        final Piece movedPiece = to.get()._2;
        final Square fromSquare = from.find(it -> it._2.equals(movedPiece)).map(it -> it._1).get();
        final Square toSquare = to.get()._1;
        return Rules.moves(previous, fromSquare)
                .filter(it -> it.to.equals(toSquare))
                .getOption()
                .getOrElseThrow(() -> new UnexpectedMoveException(
                        String.format("invalid move %s:%s (from: %s, to: %s)", fromSquare, toSquare, previous, current)));
    }
}
