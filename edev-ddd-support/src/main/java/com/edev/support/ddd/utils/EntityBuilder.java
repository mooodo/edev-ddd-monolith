package com.edev.support.ddd.utils;

import com.edev.support.entity.Entity;
import com.edev.support.subclass.utils.SubClassBuilder;
import com.edev.support.utils.BeanUtils;

import java.io.Serializable;

/**
 * The builder for create entity by its class or class name
 * @param <E> the entity
 * @param <S> the id of the entity
 */
public class EntityBuilder <E extends Entity<S>, S extends Serializable> {
    protected final Class<E> clazz;
    public EntityBuilder(Class<E> clazz) {
        this.clazz = clazz;
    }
    public EntityBuilder(String className) {
        this.clazz = EntityUtils.getClassOfEntity(className);
    }
    public E createEntity() {
        if(EntityUtils.isSubClass(clazz))
            return SubClassBuilder.build(clazz);
        return BeanUtils.createBean(clazz);
    }
    public static <E extends Entity<S>, S extends Serializable> E build(Class<E> clazz) {
        return (new EntityBuilder<E, S>(clazz)).createEntity();
    }
}
