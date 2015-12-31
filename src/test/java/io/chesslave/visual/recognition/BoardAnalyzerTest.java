package io.chesslave.visual.recognition;

import io.chesslave.visual.Images;
import org.junit.Test;
import java.awt.image.BufferedImage;
import java.io.File;

public class BoardAnalyzerTest {

    @Test
    public void analyzeSet1() {
        final BufferedImage userImage = Images.read("/images/set1/initial-board-with-background.png");
        final BoardConfiguration conf = new BoardAnalyzer().analyze(userImage);
        conf.save(new File("target"));
    }

    @Test
    public void analyzeSet2() {
        final BufferedImage userImage = Images.read("/images/set2/initial-board.png");
        final BoardConfiguration conf = new BoardAnalyzer().analyze(userImage);
        conf.save(new File("target"));
    }
}
