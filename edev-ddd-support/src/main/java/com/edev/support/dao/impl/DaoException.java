package com.edev.support.dao.impl;

public class DaoException extends RuntimeException {
    public DaoException(String message) {
        super(message);
    }
    public DaoException(String message, Throwable e) {
        super(message, e);
    }
    public DaoException(String message, Object...objects) {
        super(String.format(message, objects));
    }
    public DaoException(String message, Throwable e, Object...objects) {
        super(String.format(message, objects), e);
    }
}
