package io.chesslave.recognition;

import io.chesslave.model.Piece;
import io.chesslave.sugar.Images;
import io.chesslave.sugar.Strings;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.Map;

public class BoardConfiguration {

    public final BufferedImage boardImage;
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

    public BoardConfiguration(BufferedImage boardImage, Map<Piece, BufferedImage> pieces, Characteristics characteristics, boolean reversed) {
        this.boardImage = boardImage;
        this.pieces = Collections.unmodifiableMap(pieces);
        this.characteristics = characteristics;
        this.reversed = reversed;
    }

    public void save(File dir) {
        Images.write(boardImage, new File(dir, "board.png"));
        pieces.forEach((piece, image) -> {
            Images.write(image, new File(dir, Strings.concat(piece.type.name(), "_", piece.color.name(), ".png")));
        });
    }
}
