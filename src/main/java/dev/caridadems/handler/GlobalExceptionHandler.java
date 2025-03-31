package dev.caridadems.handler;

import dev.caridadems.exception.ObjectAlreadyExistsException;
import dev.caridadems.exception.ObjectDeleteException;
import dev.caridadems.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR_MESSAGE_KEY = "message";

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleProductAlreadyExists(ObjectAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(ERROR_MESSAGE_KEY, ex.getMessage()));
    }

    @ExceptionHandler(ObjectDeleteException.class)
    public ResponseEntity<Map<String, String>> handleDeleteError(ObjectDeleteException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(ERROR_MESSAGE_KEY, ex.getMessage()));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleObjectNotFoundError(ObjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(ERROR_MESSAGE_KEY, ex.getMessage()));
    }
}
