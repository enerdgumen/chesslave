package io.chesslave.model;

import javaslang.collection.List;
import static io.chesslave.model.Piece.Type;

public class Game {

    private final Position initialPosition;
    private final List<Move> moves;
    private final Color turn;

    public Game(Position initialPosition, List<Move> moves, Color turn) {
        this.initialPosition = initialPosition;
        this.moves = moves;
        this.turn = turn;
    }

    public static Game empty() {
        final Position position = new Position.Builder()
                .withPiece(Square.of("a1"), Piece.of(Type.ROOK, Color.WHITE))
                .withPiece(Square.of("b1"), Piece.of(Type.KNIGHT, Color.WHITE))
                .withPiece(Square.of("c1"), Piece.of(Type.BISHOP, Color.WHITE))
                .withPiece(Square.of("d1"), Piece.of(Type.QUEEN, Color.WHITE))
                .withPiece(Square.of("e1"), Piece.of(Type.KING, Color.WHITE))
                .withPiece(Square.of("f1"), Piece.of(Type.BISHOP, Color.WHITE))
                .withPiece(Square.of("g1"), Piece.of(Type.KNIGHT, Color.WHITE))
                .withPiece(Square.of("h1"), Piece.of(Type.ROOK, Color.WHITE))
                .withPiece(Square.of("a2"), Piece.of(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("b2"), Piece.of(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("c2"), Piece.of(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("d2"), Piece.of(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("e2"), Piece.of(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("f2"), Piece.of(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("g2"), Piece.of(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("h2"), Piece.of(Type.PAWN, Color.WHITE))
                .withPiece(Square.of("a8"), Piece.of(Type.ROOK, Color.BLACK))
                .withPiece(Square.of("b8"), Piece.of(Type.KNIGHT, Color.BLACK))
                .withPiece(Square.of("c8"), Piece.of(Type.BISHOP, Color.BLACK))
                .withPiece(Square.of("d8"), Piece.of(Type.QUEEN, Color.BLACK))
                .withPiece(Square.of("e8"), Piece.of(Type.KING, Color.BLACK))
                .withPiece(Square.of("f8"), Piece.of(Type.BISHOP, Color.BLACK))
                .withPiece(Square.of("g8"), Piece.of(Type.KNIGHT, Color.BLACK))
                .withPiece(Square.of("h8"), Piece.of(Type.ROOK, Color.BLACK))
                .withPiece(Square.of("a7"), Piece.of(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("b7"), Piece.of(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("c7"), Piece.of(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("d7"), Piece.of(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("e7"), Piece.of(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("f7"), Piece.of(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("g7"), Piece.of(Type.PAWN, Color.BLACK))
                .withPiece(Square.of("h7"), Piece.of(Type.PAWN, Color.BLACK))
                .build();
        return new Game(position, List.empty(), Color.WHITE);
    }

    public Game move(Move move) {
        return new Game(initialPosition, moves.append(move), turn.opponent());
    }

    public Position position() {
        return moves.foldLeft(initialPosition, Position::move);
    }
}
