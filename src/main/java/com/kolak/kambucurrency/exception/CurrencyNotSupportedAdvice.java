package com.kolak.kambucurrency.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CurrencyNotSupportedAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CurrencyNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleCurrencyNotSupported(CurrencyNotSupportedException e, WebRequest webRequest) {
        e.printStackTrace();
        return handleExceptionInternal(e, e.getMessage(), HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, webRequest);
    }

}
