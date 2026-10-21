package com.expensetracker.api.Exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Response> UserNotFound(NoSuchElementException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response("Not Found", e.getMessage(), LocalDateTime.now()));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> InvalidDataField(IllegalArgumentException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response("Invalid Data", e.getMessage(),LocalDateTime.now()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationException(MethodArgumentNotValidException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response("Validation Error", e.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleJsonParseException(
            HttpMessageNotReadableException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response("Invalid Data", "Invalid value in request body",LocalDateTime.now()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleConstraintViolation(
            ConstraintViolationException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response("Validation Error", "Invalid field value", LocalDateTime.now()));
    }
}
//