package game.yachu.controller;

import game.yachu.controller.response.ExceptionResponse;
import game.yachu.exception.YachuException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> yachuException(YachuException exception) {
        int statusCode = exception.getStatusCode();
        log.info("statusCode: {}, message: {}", statusCode, exception.getMessage());

        return ResponseEntity.status(statusCode)
                .body(new ExceptionResponse(exception.getMessage(), String.valueOf(statusCode)));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> httpMessageNotReadableException() {
        int statusCode = 400;
        final String message = "잘못된 형식의 요청입니다. 새로고침 후에 다음 다시 시도해주세요.";
        log.info("statusCode: {}, message: {}", statusCode, message);

        return ResponseEntity.status(statusCode)
                .body(new ExceptionResponse(message, String.valueOf(statusCode)));
    }
}
