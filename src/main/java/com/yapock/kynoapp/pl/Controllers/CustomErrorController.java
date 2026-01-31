package com.yapock.kynoapp.pl.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomErrorController {
    @ExceptionHandler
    ResponseEntity handleBindErrors(MethodArgumentNotValidException ex) {
        List errorList = ex.getFieldErrors().stream()
            .map(error -> {
                Map<String, String> errors = new HashMap<>();
                errors.put(error.getField(), error.getDefaultMessage());
                return errors;
            })
            .toList();

        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler
    ResponseEntity handleJPAViolation(TransactionSystemException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
