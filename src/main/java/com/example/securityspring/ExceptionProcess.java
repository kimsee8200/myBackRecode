package com.example.securityspring;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionProcess {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> Exc(Exception e){
        return new ResponseEntity<>(e.getCause(), HttpStatusCode.valueOf(404));
    }
}
