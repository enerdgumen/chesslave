package io.chesslave.recognition;

import io.chesslave.model.Board;
import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.sugar.Ensure;
import io.chesslave.sugar.Images;
import javaslang.Tuple;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import java.awt.image.BufferedImage;

public class BoardAnalyzer {

    public BoardConfiguration analyze(BufferedImage userImage) {
        final BufferedImage board = cropBoard(userImage);
        final BoardConfiguration.Characteristics chars = detectCharacteristics(board);
        final Map<Piece, BufferedImage> pieces = HashMap.ofAll(
                Tuple.of(new Piece(Piece.Type.PAWN, Color.BLACK), cropPiece(board, Board.standard.square("b7"))),
                Tuple.of(new Piece(Piece.Type.KNIGHT, Color.BLACK), cropPiece(board, Board.standard.square("g8"))),
                Tuple.of(new Piece(Piece.Type.BISHOP, Color.BLACK), cropPiece(board, Board.standard.square("c8"))),
                Tuple.of(new Piece(Piece.Type.ROOK, Color.BLACK), cropPiece(board, Board.standard.square("a8"))),
                Tuple.of(new Piece(Piece.Type.QUEEN, Color.BLACK), Images.fillOuterBackground(cropPiece(board, Board.standard.square("d8")), chars.whiteColor)),
                Tuple.of(new Piece(Piece.Type.KING, Color.BLACK), cropPiece(board, Board.standard.square("e8"))),
                Tuple.of(new Piece(Piece.Type.PAWN, Color.WHITE), cropPiece(board, Board.standard.square("b2"))),
                Tuple.of(new Piece(Piece.Type.KNIGHT, Color.WHITE), cropPiece(board, Board.standard.square("g1"))),
                Tuple.of(new Piece(Piece.Type.BISHOP, Color.WHITE), cropPiece(board, Board.standard.square("c1"))),
                Tuple.of(new Piece(Piece.Type.ROOK, Color.WHITE), cropPiece(board, Board.standard.square("a1"))),
                Tuple.of(new Piece(Piece.Type.QUEEN, Color.WHITE), Images.fillOuterBackground(cropPiece(board, Board.standard.square("d1")), chars.blackColor)),
                Tuple.of(new Piece(Piece.Type.KING, Color.WHITE), cropPiece(board, Board.standard.square("e1"))));
        return new BoardConfiguration(board, pieces, chars, false);
    }

    private static BufferedImage cropBoard(BufferedImage image) {
        final int bgColor = image.getRGB(0, 0);
        final BufferedImage board = Images.crop(image, color -> color == bgColor);
        final BoardConfiguration.Characteristics chars = detectCharacteristics(board);
        return Images.crop(board, color -> color != chars.whiteColor && color != chars.blackColor);
    }

    private static BoardConfiguration.Characteristics detectCharacteristics(BufferedImage board) {
        final int cellWidth = board.getWidth() / 8;
        final int cellHeight = board.getHeight() / 8;
        final int whiteColor = board.getRGB(cellWidth / 2, (int) (cellHeight * 4.5));
        final int blackColor = board.getRGB(cellWidth / 2, (int) (cellHeight * 3.5));
        Ensure.isTrue(whiteColor != blackColor, "White and black cells should be different, found %s", whiteColor);
        return new BoardConfiguration.Characteristics(cellWidth, cellHeight, whiteColor, blackColor);
    }

    private static BufferedImage cropPiece(BufferedImage boardImage, Board.Square square) {
        final int cellWidth = boardImage.getWidth() / 8;
        final int cellHeight = boardImage.getHeight() / 8;
        final int x = square.col * cellWidth;
        final int y = (7 - square.row) * cellHeight;
        final BufferedImage pieceImage = boardImage.getSubimage(x + 1, y + 1, cellWidth - 2, cellHeight - 2);
        return Images.crop(pieceImage, color -> color == pieceImage.getRGB(0, 0));
    }
}
