package io.chesslave;

import io.chesslave.recognition.BoardAnalyzer;
import io.chesslave.recognition.BoardConfiguration;
import io.chesslave.sugar.Images;
import org.junit.Test;
import java.awt.image.BufferedImage;
import java.io.File;

public class BoardAnalyzerTest {

    @Test
    public void init() {
        final BufferedImage userImage = Images.read(getClass(), "/images/initial-board-with-background.png");
        final BoardConfiguration conf = new BoardAnalyzer().analyze(userImage);
        conf.save(new File("target"));
    }

}
