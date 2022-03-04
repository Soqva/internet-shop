package com.s0qva.application.exception.handler;

import com.s0qva.application.exception.NoSuchProductException;
import com.s0qva.application.exception.NoSuchUserException;
import com.s0qva.application.exception.UnsavedProductHasIdException;
import com.s0qva.application.exception.UnsavedUserHasIdException;
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
            NoSuchUserException.class
    })
    public ResponseEntity<IncorrectDataContainer> handleNoSuchEntityException(RuntimeException exception) {
        Map<String, String> exceptions = new HashMap<>();

        exceptions.put("noSuchEntityError", exception.getMessage());
        IncorrectDataContainer responseData = new IncorrectDataContainer(exceptions);

        return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            UnsavedProductHasIdException.class,
            UnsavedUserHasIdException.class
    })
    public ResponseEntity<IncorrectDataContainer> handleUnsavedEntityHasIdException(RuntimeException exception) {
        Map<String, String> exceptions = new HashMap<>();

        exceptions.put("unsavedEntityHasIdError", exception.getMessage());
        IncorrectDataContainer responseData = new IncorrectDataContainer(exceptions);

        return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
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

        IncorrectDataContainer responseData = new IncorrectDataContainer(exceptions);

        return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
    }
}
