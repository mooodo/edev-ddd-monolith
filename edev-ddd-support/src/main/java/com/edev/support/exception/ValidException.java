package com.edev.support.exception;

public class ValidException extends RuntimeException {
    public ValidException() { super(); }

    public ValidException(String message) {
        super(message);
    }

    public ValidException(String message, Object...objects) {
        super(String.format(message, objects));
    }
}
