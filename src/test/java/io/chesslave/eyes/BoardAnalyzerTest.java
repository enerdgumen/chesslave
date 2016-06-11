package io.chesslave.eyes;

import io.chesslave.eyes.BoardAnalyzer;
import io.chesslave.eyes.BoardConfiguration;
import io.chesslave.eyes.Images;
import org.junit.Test;
import java.awt.image.BufferedImage;
import java.io.File;

public class BoardAnalyzerTest {
    private static final String DIR_IMAGES = "/images/";
    private static final String DIR_CHESS_SET_1 = DIR_IMAGES + "set1/";
    private static final String DIR_CHESS_SET_2 = DIR_IMAGES + "set2/";
    private static final String DIR_OUT = "target";

    @Test
    public void analyzeSet1() {
        final BufferedImage userImage = Images.read(DIR_CHESS_SET_1 +"initial-board-with-background.png");
        final BoardConfiguration conf = new BoardAnalyzer().analyze(userImage);
        conf.save(new File(DIR_OUT));
    }

    @Test
    public void analyzeSet2() {
        final BufferedImage userImage = Images.read(DIR_CHESS_SET_2 + "initial-board.png");
        final BoardConfiguration conf = new BoardAnalyzer().analyze(userImage);
        conf.save(new File(DIR_OUT));
    }
}
