package sample.authentication.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sample.authentication.constant.MessageConstants;
import sample.authentication.exception.BadRequestException;
import sample.authentication.exception.NotFoundException;
import sample.authentication.exception.UnauthorizedException;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handle(NotFoundException ex) {
        log.error(ex.getMessage());
        log.info(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handle(UnauthorizedException ex) {
        log.error(ex.getMessage());
        log.info(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handle(BadRequestException ex) {
        log.error(ex.getMessage());
        log.info(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<String> handle(MissingRequestHeaderException ex) {
        log.error(ex.getMessage());
        log.info(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException ex) {
        log.error(ex.toString());
        log.info(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex
                        .getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(" "))
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception ex) {
        log.error(ex.getMessage());
        log.info(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageConstants.INTERNAL_SERVER_ERROR);
    }
}