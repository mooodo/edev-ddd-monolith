package com.edev.support.subclass.exception;

public class SubClassException extends RuntimeException {
    public SubClassException(String message) {
        super(message);
    }
    public SubClassException(String message, Throwable cause) {
        super(message, cause);
    }
    public SubClassException(String message, Object...objects) {
        super(String.format(message, objects));
    }
    public SubClassException(String message, Throwable cause, Object...objects) {
        super(String.format(message, objects), cause);
    }
}
