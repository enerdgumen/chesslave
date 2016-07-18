package io.chesslave.eyes;

public class UnexpectedMoveException extends RuntimeException {
    
    public UnexpectedMoveException(String message) {
        super(message);
    }
}
