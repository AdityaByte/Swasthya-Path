package in.ayush.swasthyapath.controllerAdvice;

import in.ayush.swasthyapath.exception.UserAlreadyExists;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExists ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

}
