package io.chesslave.recognition;

import io.chesslave.model.Board;
import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.sugar.Ensure;
import io.chesslave.sugar.Images;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class BoardAnalyzer {

    public BoardConfiguration analyze(BufferedImage userImage) {
        final BufferedImage boardImage = cropBoard(userImage);

        final Map<Piece, BufferedImage> pieces = new HashMap<>();
        pieces.put(new Piece(Piece.Type.PAWN, Color.BLACK), cropPiece(boardImage, Board.standard.square("b7")));
        pieces.put(new Piece(Piece.Type.KNIGHT, Color.BLACK), cropPiece(boardImage, Board.standard.square("g8")));
        pieces.put(new Piece(Piece.Type.BISHOP, Color.BLACK), cropPiece(boardImage, Board.standard.square("c8")));
        pieces.put(new Piece(Piece.Type.ROOK, Color.BLACK), cropPiece(boardImage, Board.standard.square("a8")));
        pieces.put(new Piece(Piece.Type.QUEEN, Color.BLACK), cropPiece(boardImage, Board.standard.square("d8")));
        pieces.put(new Piece(Piece.Type.KING, Color.BLACK), cropPiece(boardImage, Board.standard.square("e8")));

        pieces.put(new Piece(Piece.Type.PAWN, Color.WHITE), cropPiece(boardImage, Board.standard.square("b2")));
        pieces.put(new Piece(Piece.Type.KNIGHT, Color.WHITE), cropPiece(boardImage, Board.standard.square("g1")));
        pieces.put(new Piece(Piece.Type.BISHOP, Color.WHITE), cropPiece(boardImage, Board.standard.square("c1")));
        pieces.put(new Piece(Piece.Type.ROOK, Color.WHITE), cropPiece(boardImage, Board.standard.square("a1")));
        pieces.put(new Piece(Piece.Type.QUEEN, Color.WHITE), cropPiece(boardImage, Board.standard.square("d1")));
        pieces.put(new Piece(Piece.Type.KING, Color.WHITE), cropPiece(boardImage, Board.standard.square("e1")));

        return new BoardConfiguration(boardImage, pieces, false);
    }

    private static BufferedImage cropBoard(BufferedImage image) {
        final int bgColor = image.getRGB(0, 0);
        final BufferedImage boardImage = Images.cropWhile(image, color -> color == bgColor);
        final int cellWidth = boardImage.getWidth() / 8;
        final int cellHeight = boardImage.getHeight() / 8;
        final int whiteColor = boardImage.getRGB(cellWidth / 2, (int) (cellHeight * 4.5));
        final int blackColor = boardImage.getRGB(cellWidth / 2, (int) (cellHeight * 3.5));
        Ensure.isTrue(whiteColor != blackColor, "White and black cells should be different, found %s", whiteColor);
        return Images.cropWhile(boardImage, color -> color != whiteColor && color != blackColor);
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
