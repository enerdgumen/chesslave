package io.chesslave.visual.rendering;

import static org.junit.Assert.assertTrue;

import io.chesslave.eyes.Images;
import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ChessSetTest {
    private static final String DIR_IMAGES = "/images/";
    private static final String PATH_CHESS_SET_1 = DIR_IMAGES + "set1/";
    private static final String PATH_CHESS_SET_3 = DIR_IMAGES + "set3/";

    @Parameterized.Parameter
    public ChessSet chessSet;

    @Parameterized.Parameter(value = 1)
    public String chessSetPath;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {ChessSet.read(PATH_CHESS_SET_1), PATH_CHESS_SET_1},
                {ChessSet.read(PATH_CHESS_SET_3), PATH_CHESS_SET_3}
        });
    }

    @Test
    public void readTest() {
        final BufferedImage expectedBoardImage = Images.read(chessSetPath + "empty-board.png");
        assertTrue(Images.areEquals(chessSet.board.image(), expectedBoardImage));
        chessSet.pieces.forEach((piece, image) -> {
            final BufferedImage expectedPieceImage = Images.read(chessSetPath + getPieceBaseName(piece) + ".png");
            assertTrue(Images.areEquals(image, expectedPieceImage));
        });
    }

    private String getPieceBaseName(Piece piece) {
        StringBuilder pieceName = new StringBuilder();
        pieceName.append(piece.color == Color.WHITE ? 'w' : 'b');
        pieceName.append(getPieceChar(piece.type));
        return pieceName.toString();
    }

    private char getPieceChar(Piece.Type pieceType) {
        switch (pieceType) {
            case KING:
                return 'k';
            case QUEEN:
                return 'q';
            case ROOK:
                return 'r';
            case BISHOP:
                return 'b';
            case KNIGHT:
                return 'n';
            case PAWN:
                return 'p';
            default:
                throw new IllegalArgumentException("invalid piece type");
        }
    }
}
