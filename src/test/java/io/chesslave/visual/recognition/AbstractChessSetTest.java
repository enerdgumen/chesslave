package io.chesslave.visual.recognition;

import io.chesslave.visual.rendering.ChessSet;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public abstract class AbstractChessSetTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {ChessSet.read(Paths.get("/images/set1"))},
                {ChessSet.read(Paths.get("/images/set3"))}
        });
    }

    @Parameterized.Parameter
    public ChessSet set;
}
