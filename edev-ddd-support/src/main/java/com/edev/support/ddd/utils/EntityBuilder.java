package com.edev.support.ddd.utils;

import com.edev.support.entity.Entity;
import com.edev.support.utils.BeanUtils;

import java.io.Serializable;

public class EntityBuilder <E extends Entity<S>, S extends Serializable> {
    private final Class<E> clazz;
    public EntityBuilder(Class<E> clazz) {
        this.clazz = clazz;
    }

    public EntityBuilder(String className) {
        this.clazz = EntityUtils.getClassOfEntity(className);
    }

    public E createEntity() {
        return BeanUtils.createBean(clazz);
    }

    public static <E extends Entity<S>, S extends Serializable> E build(Class<E> clazz) {
        return (new EntityBuilder<E, S>(clazz)).createEntity();
    }
}
