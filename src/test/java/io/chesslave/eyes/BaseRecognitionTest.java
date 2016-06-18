package io.chesslave.eyes;

import io.chesslave.rendering.ChessSet;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public abstract class BaseRecognitionTest {

    private static final String DIR_IMAGES = "/images/";
    private static final String PATH_CHESS_SET_1 = DIR_IMAGES + "set1/";
    private static final String PATH_CHESS_SET_3 = DIR_IMAGES + "set3/";

    @Parameterized.Parameter
    public ChessSet chessSet;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {ChessSet.read(PATH_CHESS_SET_1)},
                {ChessSet.read(PATH_CHESS_SET_3)}
        });
    }
}
