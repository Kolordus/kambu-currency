package com.kolak.kambucurrency.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AmountMustBePositiveException extends RuntimeException {

    public AmountMustBePositiveException(String msg) {
        super(msg);
    }
}
