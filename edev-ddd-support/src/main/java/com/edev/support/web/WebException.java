package com.edev.support.web;

public class WebException extends RuntimeException {
    public WebException(String message) {
        super(message);
    }
    public WebException(String message, Throwable e) {
        super(message, e);
    }
    public WebException(String message, Object...objects) {
        super(String.format(message, objects));
    }
    public WebException(String message, Throwable e, Object...objects) {
        super(String.format(message, objects), e);
    }
}
