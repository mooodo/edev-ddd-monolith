package com.edev.support.utils.downcast;

import org.springframework.stereotype.Component;

@Component
public class DowncastShort implements DowncastValue<Short> {
    @Override
    public boolean isAvailable(Class<?> clazz) {
        return clazz.equals(Short.class)||clazz.equals(short.class);
    }

    @Override
    public Short downcast(Object value) {
        if(value instanceof Short) return (Short) value;
        return Short.parseShort(value.toString());
    }
}
