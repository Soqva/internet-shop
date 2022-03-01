package com.s0qva.application.exception;

import com.s0qva.application.exception.model.ProductIncorrectData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoSuchProductException.class})
    public ResponseEntity<ProductIncorrectData> handleNoSuchProductException(NoSuchProductException exception) {
        ProductIncorrectData responseData = new ProductIncorrectData(exception.getMessage());
        return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
    }
}
