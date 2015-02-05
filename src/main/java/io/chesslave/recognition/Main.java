package io.chesslave.recognition;

import io.chesslave.model.Board;
import io.chesslave.model.Position;
import io.chesslave.sugar.Images;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import org.sikuli.api.API;
import org.sikuli.api.ScreenRegion;

public class Main {

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        API.browse(new URL("http://www.chess.com/analysis-board-editor"));
        final BufferedImage initialBoardImage = Images.read(Main.class, "sample/initial-board.png");
        final BoardRecogniser boardRecogniser = new BoardRecogniser(initialBoardImage);
        final PositionRecogniser positionRecogniser = new PositionRecogniser(new PieceRecogniser(Board.standard));
        while (true) {
            final ScreenRegion boardRegion = boardRecogniser.get();
            final Position position = positionRecogniser.apply(boardRegion);
            System.out.println(String.format("Current position:\n%s", position));
            Thread.sleep(1000);
        }
    }
}
