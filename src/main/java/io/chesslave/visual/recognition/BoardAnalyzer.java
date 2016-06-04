package io.chesslave.visual.recognition;

import io.chesslave.Ensure;
import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Square;
import io.chesslave.visual.BoardImage;
import io.chesslave.visual.Images;
import javaslang.Tuple;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import java.awt.image.BufferedImage;

public class BoardAnalyzer {

    public BoardConfiguration analyze(BufferedImage userImage) {
        final int bgColor = userImage.getRGB(0, 0);
        final BufferedImage withoutBackground = Images.crop(userImage, color -> color == bgColor);
        final BoardConfiguration.Characteristics chars = detectCharacteristics(withoutBackground);
        final BufferedImage withoutBorder = Images.crop(withoutBackground,
                color -> color != chars.whiteColor && color != chars.blackColor);
        final BoardImage board = new BoardImage(withoutBorder);
        final Map<Piece, BufferedImage> pieces = HashMap.ofAll(
                Tuple.of(Piece.of(Piece.Type.PAWN, Color.BLACK), cropPiece(board, Square.of("b7"))),
                Tuple.of(Piece.of(Piece.Type.KNIGHT, Color.BLACK), cropPiece(board, Square.of("g8"))),
                Tuple.of(Piece.of(Piece.Type.BISHOP, Color.BLACK), cropPiece(board, Square.of("c8"))),
                Tuple.of(Piece.of(Piece.Type.ROOK, Color.BLACK), cropPiece(board, Square.of("a8"))),
                Tuple.of(Piece.of(Piece.Type.QUEEN, Color.BLACK),
                        Images.fillOuterBackground(cropPiece(board, Square.of("d8")), chars.whiteColor)),
                Tuple.of(Piece.of(Piece.Type.KING, Color.BLACK), cropPiece(board, Square.of("e8"))),
                Tuple.of(Piece.of(Piece.Type.PAWN, Color.WHITE), cropPiece(board, Square.of("b2"))),
                Tuple.of(Piece.of(Piece.Type.KNIGHT, Color.WHITE), cropPiece(board, Square.of("g1"))),
                Tuple.of(Piece.of(Piece.Type.BISHOP, Color.WHITE), cropPiece(board, Square.of("c1"))),
                Tuple.of(Piece.of(Piece.Type.ROOK, Color.WHITE), cropPiece(board, Square.of("a1"))),
                Tuple.of(Piece.of(Piece.Type.QUEEN, Color.WHITE),
                        Images.fillOuterBackground(cropPiece(board, Square.of("d1")), chars.blackColor)),
                Tuple.of(Piece.of(Piece.Type.KING, Color.WHITE), cropPiece(board, Square.of("e1"))));
        return new BoardConfiguration(board, pieces, chars, false);
    }

    private static BoardConfiguration.Characteristics detectCharacteristics(BufferedImage board) {
        final int cellWidth = board.getWidth() / 8;
        final int cellHeight = board.getHeight() / 8;
        final int whiteColor = board.getRGB(cellWidth / 2, (int) (cellHeight * 4.5));
        final int blackColor = board.getRGB(cellWidth / 2, (int) (cellHeight * 3.5));
        Ensure.isTrue(whiteColor != blackColor, "White and black cells should be different, found %s", whiteColor);
        return new BoardConfiguration.Characteristics(cellWidth, cellHeight, whiteColor, blackColor);
    }

    private static BufferedImage cropPiece(BoardImage board, Square square) {
        final BufferedImage squareImage = board.squareImage(square);
        return Images.crop(squareImage, color -> color == squareImage.getRGB(0, 0));
    }
}
