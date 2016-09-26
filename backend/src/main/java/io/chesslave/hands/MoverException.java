package io.chesslave.hands;

public class MoverException extends RuntimeException {
    private static final long serialVersionUID = 2338005287898395747L;

    public MoverException(Throwable cause) {
        super(cause);
    }

    public MoverException(String message, Throwable cause) {
        super(message, cause);
    }
}
