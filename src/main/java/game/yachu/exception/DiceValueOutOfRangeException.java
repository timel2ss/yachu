package game.yachu.exception;

import org.springframework.http.HttpStatus;

public class DiceValueOutOfRangeException extends YachuException {
    public static final String message = "주사위 눈금은 1에서 6까지의 값만 가질 수 있습니다.";

    public DiceValueOutOfRangeException() {
        super(message);
    }

    public DiceValueOutOfRangeException(Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
