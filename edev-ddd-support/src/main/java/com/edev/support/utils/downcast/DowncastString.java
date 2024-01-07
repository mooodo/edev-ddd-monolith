package com.edev.support.utils.downcast;

import org.springframework.stereotype.Component;

@Component
public class DowncastString implements DowncastValue<String> {
    @Override
    public boolean isAvailable(Class<?> clazz) {
        return clazz.equals(String.class);
    }

    @Override
    public String downcast(Object value) {
        return value.toString();
    }
}
