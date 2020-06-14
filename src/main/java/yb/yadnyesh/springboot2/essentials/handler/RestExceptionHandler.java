package yb.yadnyesh.springboot2.essentials.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import yb.yadnyesh.springboot2.essentials.exception.ResourceNotFoundDetails;
import yb.yadnyesh.springboot2.essentials.exception.ResourceNotFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundDetails> handleResourceNotFoundException(ResourceNotFoundException rnfe) {
        return new ResponseEntity<>(ResourceNotFoundDetails.builder()
                                        .timeStamp(LocalDateTime.now())
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .title("Resource not found")
                                        .details(rnfe.getMessage())
                                        .developerMessage(rnfe.getClass().getName())
                                        .build(), HttpStatus.NOT_FOUND);
    }
}
