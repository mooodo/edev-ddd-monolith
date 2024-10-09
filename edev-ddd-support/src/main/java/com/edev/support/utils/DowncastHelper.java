package com.edev.support.utils;

import com.edev.support.utils.downcast.DowncastValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * The utilities for downcast object to its type
 */
@Component
public class DowncastHelper {
    @Autowired
    private ApplicationContext applicationContext;
    /**
     * downcast the object to the type
     * @param type the type
     * @param value the object
     * @return the value downcast to the type
     */
    public Object downcast(Type type, Object value) {
        if(type==null||value==null) return value;
        if(type instanceof Class)
            return downcastWithSimpleClass(type, value);
        else if(type instanceof ParameterizedType)
            return downcastWithParameterizedType(type, value);
        return null;
    }
    private Object downcastWithSimpleClass(Type type, Object value) {
        if(type==null||value==null) return value;
        Class<?> clazz = (Class<?>)type;
        Map<String, DowncastValue> map = applicationContext.getBeansOfType(DowncastValue.class);
        if(map.isEmpty()) return value;
        for (DowncastValue downcastValue : map.values())
            if(downcastValue.isAvailable(clazz))
                return downcastValue.downcast(value);
        return value;
    }

    /**
     * downcast the Object which have templates, such as {@code List<String>}
     * @param type the type of the object
     * @param value the value of the object
     * @return the downcast value
     */
    private Object downcastWithParameterizedType(Type type, Object value) {
        ParameterizedType pt = (ParameterizedType) type;
        Class<?> clazz = (Class<?>) pt.getRawType();
        if(Collection.class.isAssignableFrom(clazz) && !Map.class.isAssignableFrom(clazz))
            return downcastWithSetOrList(pt, value);
        return value;
    }

    /**
     * downcast the Set or List to it should be, such as {@code List<String>}, {@code Set<Long>}
     * @param pt the ParameterizedType of the object
     * @param value the value of the object
     * @return the downcast value
     */
    private Object downcastWithSetOrList(ParameterizedType pt, Object value) {
        Class<?> clazz = (Class<?>)pt.getRawType();
        Collection<?> list = (value instanceof String) ?
                Arrays.asList(((String)value).split(",")) : (Collection<?>) value;
        Type ata = pt.getActualTypeArguments()[0];
        if(ata instanceof Class)
            return convert(list, clazz, row->downcastWithSimpleClass(ata, row));
        else if(ata instanceof ParameterizedType)
            return convert(list, clazz, row->downcastWithParameterizedType(ata,row));
        return value;
    }

    /**
     * convert list of String to Collection<T>
     * @param list the list
     * @param clazz the raw type such as List or Set
     * @param doConvert the function how to convert each of members
     * @return Collection<T>
     */
    private <T> Collection<T> convert(Collection<?> list, Class<?> clazz,
                                            DoConvert<T> doConvert) {
        Collection<T> c;
        if(clazz.equals(Collection.class))
            c = (list instanceof List) ? new ArrayList<>() : new HashSet<>();
        else
            c = (clazz.equals(List.class)) ? new ArrayList<>() : new HashSet<>();
        for(Object obj : list) c.add(doConvert.apply(obj));
        return c;
    }

    @FunctionalInterface
    interface DoConvert<T> {
        T apply(Object s);
    }
}
