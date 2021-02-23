package com.kolak.kambucurrency.exception;

public class CurrencyNotSupportedException extends RuntimeException {

    public CurrencyNotSupportedException(String notSupportedCurrency) {
        super(notSupportedCurrency + " is not supported currency");
    }
}
