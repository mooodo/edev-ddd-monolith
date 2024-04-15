package com.edev.support.entity;

import com.edev.support.exception.OrmException;
import com.edev.support.utils.BeanUtils;
import com.edev.support.utils.NameUtils;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Entity<T extends Serializable> implements Serializable, Cloneable {
    private T id;

    /**
     * get all fields, include the entity and its parents private, protected and public fields
     * @param clazz the class of the entity
     * @param <R> the entity
     * @return list all its fields
     */
    protected <R> Field[] getFields(Class<R> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        fields = cleanFields(fields);
        Class<?> superClass = clazz.getSuperclass();
        if(superClass!=null&&Entity.class.isAssignableFrom(superClass)&&!superClass.equals(Entity.class)) {
            Field[] superFields = getFields(superClass);
            fields = mergeFields(superFields, fields);
        }
        return fields;
    }

    /**
     * get all fields, include the entity and its parents private, protected and public fields
     * @return list all its fields
     */
    public Field[] getFields() {
        return getFields(this.getClass());
    }

    private Field[] mergeFields(Field[] fields0, Field[] fields1) {
        Field[] fields = new Field[fields0.length + fields1.length];
        System.arraycopy(fields0, 0, fields, 0, fields0.length);
        System.arraycopy(fields1, 0, fields, fields0.length, fields1.length);
        return fields;
    }

    private Field[] cleanFields(Field[] fields) {
        List<Field> fieldList = new ArrayList<>();
        for(Field field : fields)
            if(!field.getName().contains("$")) fieldList.add(field);
        return fieldList.toArray(new Field[0]);
    }

    /**
     * get the field by its name, which it can be the entity and its parents private, protected and public field
     * @param clazz the class of the entity
     * @param fieldName the field name
     * @param <R> the entity
     * @return the field
     */
    protected <R> Field getField(Class<R> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if(superClass!=null&&Entity.class.isAssignableFrom(superClass)&&!superClass.equals(Entity.class))
                return getField(superClass, fieldName);
            else
                throw new OrmException("No such field[%s] in the class[%s]",fieldName,clazz);
        }
    }

    protected <R> boolean hasField(Class<R> clazz, String fieldName) {
        try {
            clazz.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if(superClass!=null&&Entity.class.isAssignableFrom(superClass)&&!superClass.equals(Entity.class))
                return hasField(superClass, fieldName);
            else
                return false;
        }
    }

    /**
     * get the field by its name, which it can be the entity and its parents private, protected and public field
     * @param fieldName the field name
     * @return the field
     */
    public Field getField(String fieldName) {
        return getField(this.getClass(), fieldName);
    }

    /**
     * get the value of the field, which the field can be the entity and its parents private, protected and public field
     * @param fieldName the field name
     * @return the value of the field
     */
    public Object getValue(String fieldName) {
        Field field = getField(this.getClass(), fieldName);
        boolean isAccessible = field.isAccessible();
        try {
            if(!isAccessible) field.setAccessible(true);
            Object value =  field.get(this);
            field.setAccessible(isAccessible);
            return value;
        } catch (IllegalAccessException e) {
            throw new OrmException("Illegal access the field[%s] in the entity[%s]", e, fieldName, this.getClass());
        }
    }

    /**
     * set the value of the field, which the field can be the entity and its parents private, protected and public field
     * @param fieldName the field name
     * @param value the value of the field
     */
    public void setValue(String fieldName, Object value) {
        Field field = getField(this.getClass(), fieldName);
        if(Modifier.isStatic(field.getModifiers())) return;
        try {
            boolean isAccessible = field.isAccessible();
            if(!isAccessible) field.setAccessible(true);
            field.set(this, value);
            field.setAccessible(isAccessible);
        } catch (IllegalAccessException e) {
            throw new OrmException("Illegal access the field[%s] in the entity[%s]", e, fieldName, this.getClass());
        }
    }

    /**
     * get the type of the field, which the field can be the entity and its parents private, protected and public field
     * @param fieldName the field name
     * @return the type of the field
     */
    public Type getType(String fieldName) {
        Field field = getField(this.getClass(), fieldName);
        return (field==null) ? null : field.getType();
    }

    private Method findGetMethod(String fieldName) {
        String firstUpperCaseName = NameUtils.convertToFirstUpperCase(fieldName);
        String methodName = "get"+ firstUpperCaseName;
        Method method;
        try {
            method = this.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            try {
                methodName = "is"+ firstUpperCaseName;
                method = this.getClass().getMethod(methodName);
            } catch (NoSuchMethodException ex) {
                return null;
            }
        }
        return method;
    }

    /**
     * get the value by calling the get method of the field, and if it has no get method, then return null.
     * @param fieldName the field name
     * @return the value
     */
    public Object getValueByMethod(String fieldName) {
        Method method = findGetMethod(fieldName);
        if(method==null) return null;
        try {
            return method.invoke(this);
        } catch (InvocationTargetException e) {
            throw new OrmException("error when invoke the method[entity:%s, method:%s]", e, this.getClass().getName(), method.getName());
        } catch (IllegalAccessException e) {
            throw new OrmException("Illegal access the method[entity:%s, method:%s]", e, this.getClass().getName(), method.getName());
        }
    }

    /**
     * get the type of the field by the get method, and if it has no get method, then return null.
     * @param fieldName the field name
     * @return the type of the field
     */
    public Type getTypeByMethod(String fieldName) {
        Method method = findGetMethod(fieldName);
        if(method==null) return null;
        return method.getGenericReturnType();
    }

    /**
     * set the value by calling the set method of the field, and if it has no set method, then it will do nothing.
     * @param fieldName the field name
     * @param value the value
     */
    public void setValueByMethod(String fieldName, Object value) {
        String methodName = "set"+ NameUtils.convertToFirstUpperCase(fieldName);
        Method setMethod = BeanUtils.getMethod(this, methodName, 1);
        if(setMethod==null)
            throw new OrmException("No such method: [entity:%s, method:%s, sizeOfParameters: 1", this.getClass(), methodName);
        Class<?> type = setMethod.getParameterTypes()[0];
        try {
            Method method = this.getClass().getMethod(methodName, type);
            method.invoke(this, value);
        } catch (NoSuchMethodException e) {
            throw new OrmException("No such method: [entity:%s, method:%s, sizeOfParameters: 1", this.getClass(), methodName);
        } catch (InvocationTargetException e) {
            throw new OrmException("error when invoke the method[entity:%s, method:%s]", e, this.getClass().getName(), methodName);
        } catch (IllegalAccessException e) {
            if(type.equals(boolean.class) && value==null)
                throw new OrmException("Please set the type of the parameter with Boolean, instance of boolean: [entity:%s, method:%s]", e, this.getClass().getName(), methodName);
            else
                throw new OrmException("Illegal access the method[entity:%s, method:%s]", e, this.getClass().getName(), methodName);
        }
    }

    /**
     * merge values of the two entities together
     * @param entity another entity
     */
    public void merge(Entity<T> entity) {
        Field[] fields = this.getFields(getClass());
        for(Field field : fields) {
            String fieldName = field.getName();
            if(!entity.hasField(entity.getClass(), fieldName)
                    ||entity.getValue(fieldName)==null) continue;
            this.setValue(fieldName, entity.getValue(fieldName));
        }
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new OrmException("Not support clone method", e);
        }
    }
}
