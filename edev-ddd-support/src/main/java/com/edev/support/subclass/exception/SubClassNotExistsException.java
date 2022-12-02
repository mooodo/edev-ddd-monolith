package com.edev.support.subclass.exception;

public class SubClassNotExistsException extends RuntimeException {
    public SubClassNotExistsException(Class<?> clazz) {
        super("The Subclass is not exists: "+clazz);
    }
    public SubClassNotExistsException(String className) {
        super("The Subclass is not exists: "+className);
    }

    public SubClassNotExistsException(String className, Object value) { super("The Subclass is not exists: [class:"+className+",value:"+value+"]"); }
}
