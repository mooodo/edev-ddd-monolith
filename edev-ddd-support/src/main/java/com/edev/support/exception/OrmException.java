package com.edev.support.exception;

public class OrmException extends RuntimeException {
    public OrmException(String message) {
        super(message);
    }

    public OrmException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrmException(String message, Object...objects) {
        super(String.format(message, objects));
    }

    public OrmException(String message, Throwable cause, Object...objects) {
        super(String.format(message, objects), cause);
    }
}
