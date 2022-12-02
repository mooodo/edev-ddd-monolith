package com.edev.support.cache;

public class CacheException extends RuntimeException {
    public CacheException(String message) {
        super(message);
    }
    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }
    public CacheException(String message, Object...objects) {
        super(String.format(message, objects));
    }
    public CacheException(String message, Throwable cause, Object...objects) {
        super(String.format(message, objects), cause);
    }
}
