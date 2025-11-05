package in.ayush.swasthyapath.controllerAdvice;

import in.ayush.swasthyapath.exception.UserAlreadyExists;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExists e) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "response", e.getMessage()
                ));
    }

}
