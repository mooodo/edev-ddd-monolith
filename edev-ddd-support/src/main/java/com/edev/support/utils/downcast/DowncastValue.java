package com.edev.support.utils.downcast;

public interface DowncastValue<T> {
    boolean isAvailable(Class<?> clazz);
    T downcast(Object value);
}
