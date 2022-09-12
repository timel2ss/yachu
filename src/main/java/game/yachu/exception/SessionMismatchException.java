package game.yachu.exception;

import org.springframework.http.HttpStatus;

public class SessionMismatchException extends YachuException {
    public static final String message = "존재하지 않거나 만료된 세션입니다.";

    public SessionMismatchException() {
        super(message);
    }

    public SessionMismatchException(Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
