package com.example.gymtracker.exception;

import com.example.gymtracker.exception.customException.EntityNotFoundException;
import com.example.gymtracker.exception.customException.ErrorResponse;
import com.example.gymtracker.exception.customException.ValueCastException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse response = new ErrorResponse("NOT FOUND", ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(ValueCastException.class)
    public ResponseEntity<ErrorResponse> handleValueInvalid(ValueCastException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Invalid value", ex.getMessage(), Instant.now()));
    }
}
