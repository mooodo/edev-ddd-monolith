package com.edev.support.subclass.utils;

import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.dsl.Property;
import com.edev.support.dsl.SubClass;
import com.edev.support.entity.Entity;
import com.edev.support.utils.BeanUtils;

import java.io.Serializable;

public class SubClassBuilder <E extends Entity<S>, S extends Serializable> extends EntityBuilder<E,S> {
    public SubClassBuilder(Class<E> clazz) {
        super(clazz);
    }

    public SubClassBuilder(String className) {
        super(className);
    }

    @Override
    public E createEntity() {
        E entity = BeanUtils.createBean(clazz);
        if(EntityUtils.isSubClass(entity.getClass())) {
            DomainObject parent = DomainObjectFactory.getDomainObject(entity.getClass().getSuperclass());
            SubClass subClass = SubClassUtils.getSubClass(parent, entity.getClass());
            Property discriminator = SubClassUtils.getDiscriminator(parent);
            entity.setValue(discriminator.getName(), subClass.getValue());
        }
        return entity;
    }

    public static <E extends Entity<S>, S extends Serializable> E build(Class<E> clazz) {
        return (new SubClassBuilder<E,S>(clazz)).createEntity();
    }
}
