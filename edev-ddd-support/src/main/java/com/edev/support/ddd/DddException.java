package com.edev.support.ddd;

public class DddException extends RuntimeException {
    public DddException() {
        super();
    }
    public DddException(String message) {
        super(message);
    }
    public DddException(String message, Throwable e) {
        super(message, e);
    }
    public DddException(String message, Object...objects) {
        super(String.format(message, objects));
    }
    public DddException(String message, Throwable e, Object...objects) {
        super(String.format(message, objects), e);
    }
}
