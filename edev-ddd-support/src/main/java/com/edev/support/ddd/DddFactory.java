package com.edev.support.ddd;

import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.entity.Entity;
import com.edev.support.subclass.SubClassFactory;
import com.edev.support.utils.BeanUtils;
import com.edev.support.utils.DowncastHelper;
import com.edev.support.utils.NameUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@Component
public class DddFactory {
    @Autowired
    private SubClassFactory subClassFactory;
    @Autowired
    private DowncastHelper downcastHelper;

    public boolean isEntity(Class<?> clazz) {
        return EntityUtils.isEntity(clazz);
    }

    public <E extends Entity<S>,S extends Serializable> Class<E> getClazz(String className) {
        return (Class<E>)BeanUtils.getClazz(className);
    }

    public <E extends Entity<S>,S extends Serializable> E createEntityByJson(String className, Map<String, Object> json) {
        if(json==null) return null;
        Class<E> clazz = getClazz(className);
        return createEntityByJson(clazz, json);
    }

    public <E extends Entity<S>,S extends Serializable> E createEntityByJson(@NonNull Class<E> clazz, Map<String, Object> json) {
        if(json==null) return null;
        if(EntityUtils.hasSubClass(clazz)) //create subclass entity
            return subClassFactory.chooseSubClass(clazz).createEntityByJson(clazz, json);
        else //create normal entity
            return createSimpleEntityByJson(clazz, json);
    }

    public <E extends Entity<S>,S extends Serializable> E createSimpleEntityByJson(@NonNull Class<E> clazz, @NonNull Map<String, Object> json) {
        E entity = EntityBuilder.build(clazz);
        Field[] fields = entity.getFields();
        for(Field field : fields) {
            String fieldName = field.getName();
            Type type = entity.getTypeByMethod(fieldName);
            Object value = json.get(fieldName);
            if(type==null) continue;
            if((type instanceof Class)&&isEntity((Class<?>) type))
                value = createEntityByJson((Class<E>) type, (Map<String, Object>) value);
            else if(isListOrSetOfEntities(type))
                value = createEntityByJsonForList(type, value);
            else
                value = downcastHelper.downcast(type, value);
            entity.setValueByMethod(fieldName, value);
        }
        return entity;
    }

    /**
     * whether the type is a list or set of entities
     * @param type the type
     * @return true if it is a list or set of entities
     */
    public boolean isListOrSetOfEntities(Type type) {
        if(type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)type;
            Class<?> clazz = (Class<?>)pt.getRawType();
            Type ata = pt.getActualTypeArguments()[0];
            return (clazz.equals(List.class) || clazz.equals(Set.class)) &&
                    ((ata instanceof Class)&&isEntity((Class<?>) ata));
        }
        return false;
    }

    public <E extends Entity<S>, S extends Serializable> List<E> createEntityByJsonForList(@NonNull Type type, Object value) {
        if (!(type instanceof ParameterizedType)) return new ArrayList<>();
        if (!(value instanceof Collection)) return new ArrayList<>();
        ParameterizedType pt = (ParameterizedType)type;
        Collection<Map<String,Object>> collection = (Collection<Map<String,Object>>)value;
        Type ata = pt.getActualTypeArguments()[0];
        Class<E> ataClass = (Class<E>) ata;
        List<E> list = new ArrayList<>();
        collection.forEach(json->list.add(createEntityByJson(ataClass, json)));
        return list;
    }

    public <E extends Entity<S>, S extends Serializable> E createEntityByRow(@NonNull String className, Map<String, Object> row) {
        Class<E> clazz = getClazz(className);
        return createEntityByRow(clazz, row);
    }

    public <E extends Entity<S>, S extends Serializable> E createEntityByRow(@NonNull Class<E> clazz, Map<String, Object> row) {
        if(row==null|| row.isEmpty()) return null;
        if(EntityUtils.hasSubClass(clazz))
            return subClassFactory.chooseSubClass(clazz).createEntityByRow(clazz, row);
        return createSimpleEntityByRow(clazz, row);
    }

    public <E extends Entity<S>, S extends Serializable> E createSimpleEntityByRow(@NonNull Class<E> clazz, @NonNull Map<String, Object> row) {
        E entity = EntityBuilder.build(clazz);
        for(Map.Entry<String, Object> entry: row.entrySet()) {
            String column = entry.getKey();
            Object value = entry.getValue();
            String fieldName = NameUtils.convertToCamelCase(column);
            Type type = entity.getType(fieldName);
            if (type==null) continue;
            value = downcastHelper.downcast(type, value);
            entity.setValue(fieldName, value);
        }
        return entity;
    }

    public <E extends Entity<S>, S extends Serializable> List<E> createEntityByRowForList(@NonNull String className, Collection<Map<String, Object>> list) {
        Class<E> clazz = getClazz(className);
        return createEntityByRowForList(clazz, list);
    }

    public <E extends Entity<S>, S extends Serializable> List<E> createEntityByRowForList(@NonNull Class<E> clazz, Collection<Map<String, Object>> list) {
        if(list==null||list.isEmpty()) return new ArrayList<>();
        List<E> entities = new ArrayList<>();
        list.forEach(row->entities.add(createEntityByRow(clazz,row)));
        return entities;
    }
}
