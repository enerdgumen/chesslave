package io.chesslave.hands;

public class UninstantiableMoverException extends MoverException {
    private static final long serialVersionUID = 5915275573738380456L;

    public UninstantiableMoverException(Throwable cause) {
        super(cause);
    }

    public UninstantiableMoverException(String message, Throwable cause) {
        super(message, cause);
    }
}
