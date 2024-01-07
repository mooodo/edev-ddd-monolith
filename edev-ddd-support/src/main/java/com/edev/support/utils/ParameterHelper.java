package com.edev.support.utils;

import com.edev.support.exception.OrmException;
import lombok.Getter;
import lombok.NonNull;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Getter
public class ParameterHelper {
    private final Method method;
    public ParameterHelper(@NonNull Method method) {
        this.method = method;
    }
    public static ParameterHelper build(@NonNull Method method) {
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
                throw new OrmException("no such method[class:%s, method:%s, parameters:%s]",
                        method.getDeclaringClass(),method.getName(),method.getParameterTypes());
            }
        } else {
            return method.getParameters();
        }
    }
}
