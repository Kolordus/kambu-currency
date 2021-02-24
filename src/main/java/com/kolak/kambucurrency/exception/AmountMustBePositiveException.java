package com.kolak.kambucurrency.exception;

public class AmountMustBePositiveException extends RuntimeException {

    public AmountMustBePositiveException() {
        super("Amount must be a positive number");
    }
}
