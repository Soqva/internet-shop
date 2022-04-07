package com.s0qva.application.exception.handler;

import com.s0qva.application.exception.InvalidPasswordDuringSignInException;
import com.s0qva.application.exception.NoSuchOrderException;
import com.s0qva.application.exception.NoSuchProductException;
import com.s0qva.application.exception.NoSuchUserException;
import com.s0qva.application.exception.UserAlreadyExistsException;
import com.s0qva.application.exception.model.IncorrectDataContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            NoSuchProductException.class,
            NoSuchUserException.class,
            NoSuchOrderException.class
    })
    public ResponseEntity<IncorrectDataContainer> handleNoSuchEntityException(RuntimeException exception) {
        return buildResponseEntity(exception, HttpStatus.NOT_FOUND, "noSuchEntityError");
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<IncorrectDataContainer> handleEntityAlreadyExistsException(RuntimeException exception) {
        return buildResponseEntity(exception, HttpStatus.CONFLICT, "entityAlreadyExists");
    }

    @ExceptionHandler({InvalidPasswordDuringSignInException.class})
    public ResponseEntity<IncorrectDataContainer> handleSignInException(RuntimeException exception) {
        return buildResponseEntity(exception, HttpStatus.CONFLICT, "invalidPassword");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<IncorrectDataContainer> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> exceptions = new HashMap<>();

        exception.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    exceptions.put(fieldName, errorMessage);
                });

        return buildResponseEntity(exceptions, HttpStatus.CONFLICT);
    }

    private ResponseEntity<IncorrectDataContainer> buildResponseEntity(Exception exception,
                                                                       HttpStatus httpStatus,
                                                                       String errorMessage) {
        Map<String, String> exceptions = new HashMap<>();

        exceptions.put(errorMessage, exception.getMessage());
        IncorrectDataContainer responseData = new IncorrectDataContainer(exceptions);

        return new ResponseEntity<>(responseData, httpStatus);
    }

    private ResponseEntity<IncorrectDataContainer> buildResponseEntity(Map<String, String> exceptions,
                                                                       HttpStatus httpStatus) {
        return new ResponseEntity<>(new IncorrectDataContainer(exceptions), httpStatus);
    }
}
