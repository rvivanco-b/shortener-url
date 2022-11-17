package com.upwork.shortenerapi.config;

import com.upwork.shortenerapi.exception.ShortenUrlDoesntExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ShortenerExceptionHandler {

    @ExceptionHandler(ShortenUrlDoesntExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleShortenUrlDoesntExists() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
