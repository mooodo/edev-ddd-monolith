package com.edev.support.utils.downcast;

import org.springframework.stereotype.Component;

@Component
public class DowncastInteger implements DowncastValue<Integer> {
    @Override
    public boolean isAvailable(Class<?> clazz) {
        return clazz.equals(Integer.class)||clazz.equals(int.class);
    }

    @Override
    public Integer downcast(Object value) {
        if(value instanceof Integer) return (Integer) value;
        return Integer.parseInt(value.toString());
    }
}
