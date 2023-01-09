package com.edev.support.subclass.utils;

import com.edev.support.ddd.DddException;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.dsl.Property;
import com.edev.support.dsl.SubClass;
import com.edev.support.entity.Entity;
import com.edev.support.subclass.exception.NoDiscriminatorException;
import com.edev.support.subclass.exception.SubClassNotExistsException;
import com.edev.support.utils.BeanUtils;

import java.io.Serializable;
import java.util.List;

public class SubClassUtils {
    /**
     * get dsl of the subclass
     * @param dObj the dsl of the parent class
     * @param subClazz the class of the subclass
     * @return the dsl of the subclass
     */
    public static SubClass getSubClass(DomainObject dObj, Class<?> subClazz) {
        List<SubClass> subClasses = dObj.getSubClasses();
        for(SubClass subClass : subClasses)
            if(subClass.getClazz().equals(subClazz.getName()))
                return subClass;
        throw new SubClassNotExistsException(subClazz);
    }

    /**
     * get dsl of the subclass by the value of the discriminator
     * @param dObj the dsl of the parent class
     * @param value the value of the discriminator
     * @return the dsl of the subclass
     */
    public static SubClass getSubClassByValue(DomainObject dObj, Object value) {
        List<SubClass> subClasses = dObj.getSubClasses();
        for(SubClass subClass : subClasses)
            if(subClass.getValue().equals(value))
                return subClass;
        throw new SubClassNotExistsException(dObj.getClazz(), value);
    }

    public static <E extends Entity<S>, S extends Serializable>
    Object getValueOfDiscriminator(E parent) {
        Class<?> parentClass = isParent(parent) ? parent.getClass() : parent.getClass().getSuperclass();
        DomainObject dObj = DomainObjectFactory.getDomainObject(parentClass);
        Property discriminator = getDiscriminator(dObj);
        return parent.getValue(discriminator.getName());
    }

    public static <E extends Entity<S>, S extends Serializable> E createSubClassByParent(E parent) {
        Object value = getValueOfDiscriminator(parent);
        if(value==null) throw new NoDiscriminatorException("No value of the Discriminator");
        DomainObject dObj = DomainObjectFactory.getDomainObject(parent.getClass());
        SubClass subClass = getSubClassByValue(dObj, value);
        Class<E> classOfChild = getClazz(subClass.getClazz());
        E child =  EntityBuilder.build(classOfChild);
        child.merge(parent);
        return child;
    }

    /**
     * get the discriminator of the subclass
     * @param dObj the dsl of the parent class
     * @return the discriminator
     */
    public static Property getDiscriminator(DomainObject dObj) {
        for(Property property : dObj.getProperties())
            if(property.isDiscriminator()) return property;
        throw new NoDiscriminatorException(dObj.getClazz());
    }

    public static <E extends Entity<S>, S extends Serializable> void setDiscriminator(E entity) {
        Class<?> clazz = entity.getClass();
        if(isParent(clazz)) return;
        DomainObject dObj = DomainObjectFactory.getDomainObject(clazz.getSuperclass());
        String value = getSubClass(dObj, clazz).getValue();
        Property property = getDiscriminator(dObj);
        entity.setValue(property.getName(), value);
    }

    public static <E extends Entity<S>, S extends Serializable> Class<E> getClazz(String className) {
        Class<?> clazz = BeanUtils.getClazz(className);
        if(!EntityUtils.isEntity(className))
            throw new DddException("The class is not an entity: ["+className+"]");
        return (Class<E>) clazz;
    }

    /**
     * whether the entity is a parent class
     * @param entity the entity
     * @param <E> the entity
     * @param <S> the id
     * @return true if the entity is a parent class
     */
    public static <E extends Entity<S>, S extends Serializable> boolean isParent(E entity) {
        return isParent(entity.getClass());
    }

    /**
     * whether the entity is a parent class
     * @param clazz the class of the entity
     * @return true if the entity is a parent class
     */
    public static boolean isParent(Class<?> clazz) {
        return (clazz.getSuperclass().equals(Entity.class));
    }

    public static <E extends Entity<S>, S extends Serializable>
    void doForEachSubClass(Class<E> clazz, Fallback<E,S> fallback) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
        List<SubClass> subClasses = dObj.getSubClasses();
        for (SubClass subClass : subClasses) {
            Class<E> subClazz = EntityUtils.getClassOfEntity(subClass.getClazz());
            fallback.apply(subClazz);
        }
    }

    @FunctionalInterface
    public interface Fallback<E extends Entity<S>, S extends Serializable> {
        void apply(Class<E> subClass);
    }
}
