package game.yachu.exception;

public abstract class YachuException extends RuntimeException {
    public YachuException(String message) {
        super(message);
    }

    public YachuException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();
}
