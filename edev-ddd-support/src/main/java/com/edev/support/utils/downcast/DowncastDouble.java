package com.edev.support.utils.downcast;

import org.springframework.stereotype.Component;

@Component
public class DowncastDouble implements DowncastValue<Double> {
    @Override
    public boolean isAvailable(Class<?> clazz) {
        return clazz.equals(Double.class)||clazz.equals(double.class);
    }

    @Override
    public Double downcast(Object value) {
        if(value instanceof Double) return (Double) value;
        return Double.parseDouble(value.toString());
    }
}
