package com.edev.support.dsl;

public class NoSuchDObjException extends RuntimeException {
    public NoSuchDObjException(String className) {
        super("No such domain object:["+className+"]");
    }
}
