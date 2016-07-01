package io.chesslave.eyes;

import io.chesslave.model.Piece;
import io.chesslave.visual.model.BoardImage;
import javaslang.collection.List;
import javaslang.collection.Map;
import java.awt.image.BufferedImage;
import java.io.File;

public class BoardConfiguration {

    public final BoardImage board;
    public final Map<Piece, BufferedImage> pieces;
    public final Characteristics characteristics;
    public final boolean reversed;

    public static class Characteristics {
        public final int cellWidth;
        public final int cellHeight;
        public final int whiteColor;
        public final int blackColor;

        public Characteristics(int cellWidth, int cellHeight, int whiteColor, int blackColor) {
            this.cellWidth = cellWidth;
            this.cellHeight = cellHeight;
            this.whiteColor = whiteColor;
            this.blackColor = blackColor;
        }
    }

    public BoardConfiguration(BoardImage board, Map<Piece, BufferedImage> pieces, Characteristics characteristics, boolean reversed) {
        this.board = board;
        this.pieces = pieces;
        this.characteristics = characteristics;
        this.reversed = reversed;
    }

    public void save(File dir) {
        Images.write(board.image(), new File(dir, "board.png"));
        pieces.forEach((piece, image) -> {
            Images.write(image, new File(dir, List.of(piece.type.name(), "_", piece.color.name(), ".png").reduce(String::concat)));
        });
    }
}
