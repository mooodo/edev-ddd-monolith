package com.edev.support.subclass.exception;

public class NoDiscriminatorException extends RuntimeException {
    public NoDiscriminatorException(String className) {
        super("No Discriminator to be found: ["+className+"]");
    }
    public NoDiscriminatorException(Class<?> clazz) {
        super("No Discriminator to be found: ["+clazz+"]");
    }
}
