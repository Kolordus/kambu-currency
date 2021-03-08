package com.kolak.kambucurrency.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrencyNotSupportedException extends RuntimeException {

    public CurrencyNotSupportedException(String notSupportedCurrency, String msg) {
        super(notSupportedCurrency + " " + msg);
    }
}
