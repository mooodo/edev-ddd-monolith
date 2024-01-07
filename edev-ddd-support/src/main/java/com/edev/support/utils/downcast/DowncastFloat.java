package com.edev.support.utils.downcast;

import org.springframework.stereotype.Component;

@Component
public class DowncastFloat implements DowncastValue<Float> {
    @Override
    public boolean isAvailable(Class<?> clazz) {
        return clazz.equals(Float.class)||clazz.equals(float.class);
    }

    @Override
    public Float downcast(Object value) {
        if(value instanceof Float) return (Float) value;
        return Float.parseFloat(value.toString());
    }
}
