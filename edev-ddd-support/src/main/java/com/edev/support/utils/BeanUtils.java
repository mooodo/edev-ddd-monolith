package com.edev.support.utils;

import com.edev.support.exception.OrmException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class BeanUtils {
    private BeanUtils() {}
    public static Class<?> getClazz(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new OrmException("The Class is not Found:[%s]", className);
        }
    }

    public static <T> T createBean(Class<T> clazz) {
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
    public static void setValue(Object obj, String fieldName, Object value) {
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
    public static Object getValue(Object obj, String fieldName) {
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

    public static Method getMethod(Object obj, String methodName, int sizeOfParameters) {
        if(methodName==null||methodName.isEmpty())
            throw new OrmException("The method name is empty!");
        Method target = null;
        for(Method method : obj.getClass().getMethods())
            if(method.getName().equals(methodName) &&
                method.getParameterTypes().length==sizeOfParameters)
                    target = method;
        return target;
    }

    public static Object getService(String beanName, ApplicationContext applicationContext) {
        if (beanName == null || beanName.isEmpty())
            throw new OrmException("The bean name is empty!");
        try {
            return applicationContext.getBean(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            throw new OrmException("No such bean definition in the spring context!", e);
        } catch (BeansException e) {
            throw new OrmException("error when get the bean[%s]", beanName);
        }
    }

    public static Method getMethod(Object service, String methodName) {
        if(methodName==null||methodName.isEmpty())
            throw new OrmException("The method name is empty!");
        Method[] allOfMethods = service.getClass().getDeclaredMethods();
        Method rtn = null;
        for(Method method : allOfMethods) {
            if(method.getName().equals(methodName)) rtn = method;
        }
        if(rtn!=null) return rtn; //if it has an override, return the last one.
        throw new OrmException("No such method[%s] in the service[%s]", methodName, service.getClass().getName());
    }

    public static Object invoke(Object service, Method method, Object...args) {
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
