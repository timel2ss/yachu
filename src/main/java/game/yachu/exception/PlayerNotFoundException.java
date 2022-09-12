package game.yachu.exception;

import org.springframework.http.HttpStatus;

public class PlayerNotFoundException extends YachuException {
    public static final String message = "존재하지 않거나 세션이 만료된 상태입니다.";

    public PlayerNotFoundException() {
        super(message);
    }

    public PlayerNotFoundException(Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
