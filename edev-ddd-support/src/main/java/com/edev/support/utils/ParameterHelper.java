package com.edev.support.utils;

import lombok.Getter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Getter
public class ParameterHelper {
    private final Method method;
    public ParameterHelper(Method method) {
        this.method = method;
    }
    public static ParameterHelper build(Method method) {
        return new ParameterHelper(method);
    }

    public Parameter[] getParameters() {
        //whether is cglib proxy object
        if(method.getDeclaringClass().getName().contains("$$")) {
            try {
                return method.getDeclaringClass().getSuperclass()
                        .getMethod(method.getName(),method.getParameterTypes())
                        .getParameters();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        } else {
            return method.getParameters();
        }
    }
}
