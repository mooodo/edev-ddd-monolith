package com.edev.utils;

import com.edev.support.exception.ValidException;

public class ValidUtils {
    private static String msg(String desc, Object... objects) {
        return String.format(desc, objects);
    }
    public static void isNull(Object value, String desc, Object... objects) {
        if(value==null) throw new ValidException("Null of %s!", msg(desc, objects));
    }
    public static void isNotExists(Long value, ValidFunc func, String desc, Object... objects) {
        isNull(value, msg(desc, objects));
        if(!func.apply(value)) throw new ValidException("Not exists %s", msg(desc, objects));
    }
    public static void isExists(Long value, ValidFunc func, String desc, Object... objects) {
        isNull(value, msg(desc, objects));
        if(func.apply(value)) throw new ValidException("Exists %s", msg(desc, objects));
    }
    @FunctionalInterface
    public interface ValidFunc {
        boolean apply(Long id);
    }
    public static void isError(boolean expression, String desc, Object... objects) {
        if(expression) throw new ValidException(msg(desc, objects));
    }
}
