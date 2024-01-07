package com.edev.support.utils.downcast;

import org.springframework.stereotype.Component;

@Component
public class DowncastLong implements DowncastValue<Long>{
    @Override
    public boolean isAvailable(Class<?> clazz) {
        return clazz.equals(Long.class)||clazz.equals(long.class);
    }

    @Override
    public Long downcast(Object value) {
        if(value instanceof Long) return (Long) value;
        return Long.parseLong(value.toString());
    }
}
