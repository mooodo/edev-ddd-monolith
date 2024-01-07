package com.edev.support.utils;

import com.edev.support.exception.OrmException;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * The utilities for the object reflect
 */
public class BeanUtils {
    private BeanUtils() {}

    /**
     * get class according to its class name
     * @param className the class name
     * @return the class
     */
    public static Class<?> getClazz(@NonNull String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new OrmException("The Class is not Found:[%s]", className);
        }
    }

    /**
     * build the bean according to its class
     * @param clazz the class
     * @return the bean
     * @param <T> the class
     */
    public static <T> T createBean(@NonNull Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new OrmException("Instance the entity error:[%s]", e, clazz);
        } catch (IllegalAccessException e) {
            throw new OrmException("Illegal access the entity:[%s]", e, clazz);
        }
    }

    /**
     * set the value of the field to the object
     * @param obj the object
     * @param fieldName the field name
     * @param value the value of the field
     */
    public static void setValue(@NonNull Object obj, @NonNull String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            boolean isAccessible = field.isAccessible();
            if(!isAccessible) field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(isAccessible);
        } catch (IllegalAccessException e) {
            throw new OrmException("Illegal access the field[%s] in the entity[%s]", e, fieldName, obj);
        } catch (NoSuchFieldException e) {
            throw new OrmException("No such field:[object: %s, field: %s]", obj, fieldName);
        }
    }

    /**
     * get the value of the field of the object
     * @param fieldName the field name
     * @return the value of the field
     */
    public static Object getValue(@NonNull Object obj, @NonNull String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            boolean isAccessible = field.isAccessible();
            if(!isAccessible) field.setAccessible(true);
            Object value =  field.get(obj);
            field.setAccessible(isAccessible);
            return value;
        } catch (IllegalAccessException e) {
            throw new OrmException("Illegal access the field[%s] in the object[%s]", e, fieldName, obj);
        } catch (NoSuchFieldException e) {
            throw new OrmException("No such field:[object: %s, field: %s]", obj, fieldName);
        }
    }

    /**
     * get the object's method according to its method name and size of parameters
     * @param obj the object
     * @param methodName the method name
     * @param sizeOfParameters size of parameters
     * @return the method
     */
    public static Method getMethod(@NonNull Object obj, @NonNull String methodName, int sizeOfParameters) {
        Method target = null;
        for(Method method : obj.getClass().getMethods())
            if(method.getName().equals(methodName) &&
                method.getParameterTypes().length==sizeOfParameters)
                    target = method;
        return target;
    }

    /**
     * get the object's method according to its method name.
     * If you get more methods with same name, return the first one.
     * @param service the service
     * @param methodName the method name
     * @return the method
     */
    public static Method getMethod(@NonNull Object service, @NonNull String methodName) {
        Method[] allOfMethods = service.getClass().getDeclaredMethods();
        Method rtn = null;
        for(Method method : allOfMethods) {
            if(method.getName().equals(methodName)) rtn = method;
        }
        if(rtn!=null) return rtn; //if it has an override, return the last one.
        throw new OrmException("No such method[%s] in the service[%s]", methodName, service.getClass().getName());
    }

    /**
     * invoke the object's method using reflect.
     * @param service the service
     * @param method the method
     * @param args the arguments
     * @return the return value
     */
    public static Object invoke(@NonNull Object service, @NonNull Method method, Object...args) {
        try {
            if(args==null||args.length==0) return method.invoke(service);
            else return method.invoke(service, args);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new OrmException("error when invoking the service by reflect [method: %s, args: %s]",
                    e, method, Arrays.toString(args));
        }
    }
}
