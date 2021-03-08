package com.rincentral.test.error;

public class NoValuePresentException extends RuntimeException{
    public NoValuePresentException(String message) {
        super(message);
    }
}
