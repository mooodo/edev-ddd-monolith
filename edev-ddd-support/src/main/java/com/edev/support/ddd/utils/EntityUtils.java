package com.edev.support.ddd.utils;

import com.edev.support.ddd.DddException;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.entity.Entity;
import com.edev.support.utils.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntityUtils {

    /**
     * whether the class is an entity
     * @param clazz the class
     * @return true if it is an entity
     */
    public static boolean isEntity(Class<?> clazz) {
        if(clazz==null||Entity.class.equals(clazz)) return false;
        if(Entity.class.isAssignableFrom(clazz)) {
            if (DomainObjectFactory.isExists(clazz.getName())) return true;
            if(clazz.getSuperclass()!=null&&DomainObjectFactory.isExists(clazz.getSuperclass().getName())) return true;
            throw new DddException("The entity[%s] isn't register in domain objects register files.", clazz.getName());
        }
        return false;
    }

    public static boolean isSubClass(Class<?> clazz) {
        return isEntity(clazz)&&!clazz.getSuperclass().equals(Entity.class);
    }

    /**
     * whether the class is an entity
     * @param className the class name
     * @return true if it is an entity
     */
    public static boolean isEntity(String className) {
        return isEntity(getClassOfEntity(className));
    }

    /**
     * whether the class has subclass
     * @param clazz the class
     * @return true if it has subclass
     */
    public static boolean hasSubClass(Class<?> clazz) {
        if(!isEntity(clazz)) return false;
        //if(hasSubClass(clazz.getSuperclass())) return true;
        return hasSubClass(clazz.getName());
    }

    /**
     * whether the class has subclass
     * @param className the class name
     * @return true if it has subclass
     */
    public static boolean hasSubClass(String className) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(className);
        return hasSubClass(dObj);
    }

    /**
     * whether the class has subclass
     * @param dObj the domain object
     * @return true if it has subclass
     */
    public static boolean hasSubClass(DomainObject dObj) {
        String subClassType = dObj.getSubClassType();
        return subClassType!=null&&!subClassType.isEmpty();
    }

    /**
     * get the class of the entity by its class name
     * @param className the class name of the entity
     * @param <E> the entity
     * @param <S> the id of the entity
     * @return the class of the entity
     */
    public static <E extends Entity<S>, S extends Serializable> Class<E> getClassOfEntity(String className) {
        Class<?> clazz = BeanUtils.getClazz(className);
        if(!Entity.class.isAssignableFrom(clazz))
            throw new DddException("The class is not an entity: [%s]", className);
        return (Class<E>)clazz;
    }

    public static <E extends Entity<S>, S extends Serializable> Class<E> getClass(E entity) {
        return (Class<E>) entity.getClass();
    }

    public static <E extends Entity<S>, S extends Serializable> Class<E> getSuperclass(E entity) {
        return (Class<E>) entity.getClass().getSuperclass();
    }

    public static <E extends Entity<S>, S extends Serializable> Class<E> getSuperclass(Class<E> clazz) {
        return (Class<E>)clazz.getSuperclass();
    }

    /**
     * compare two collections their difference,
     * then get 3 collections: inserted, updated, deleted
     * and call fallback method to do something.
     * @param source the source collection
     * @param target the target collection
     * @param callback the callback method
     * @param <E> the entity
     * @param <S> the id of the entity
     */
    public static <E extends Entity<S>, S extends Serializable>
            void compareListOrSetOfEntity(Collection<E> source, Collection<E> target, ComparedCollectionCallback<E> callback) {
        compareListOrSetOfEntity(source, target, (sourceEntity, targetEntity)-> targetEntity.getId().equals(sourceEntity.getId()), callback);
    }

    /**
     * compare two collections their difference,
     * then get 3 collections: inserted, updated, deleted
     * and call fallback method to do something.
     * @param source the source collection
     * @param target the target collection
     * @param matchKeyCallback the callback method whether their match the primary key
     * @param callback the callback method
     * @param <E> the entity
     * @param <S> the id of the entity
     */
    public static <E extends Entity<S>, S extends Serializable>
            void compareListOrSetOfEntity(Collection<E> source, Collection<E> target,
                                          MatchKeyCallback<E> matchKeyCallback, ComparedCollectionCallback<E> callback) {
        if(source==null) source = new ArrayList<>();
        if(target==null) target = new ArrayList<>();

        List<E> inserted = new ArrayList<>();
        List<E> updated = new ArrayList<>();
        List<E> deleted = new ArrayList<>(source);
        for(E targetEntity : target) {
            boolean isMatched = false;
            for (E sourceEntity : source) {
                if(matchKeyCallback.isKeyMatch(sourceEntity, targetEntity)) {
                    isMatched = true;
                    if(!targetEntity.equals(sourceEntity))
                        updated.add(targetEntity);
                    break;
                }
            }
            if(!isMatched) inserted.add(targetEntity);
            for (int i=0; i<deleted.size(); i++)
                if(matchKeyCallback.isKeyMatch(deleted.get(i), targetEntity)) {
                    deleted.remove(i);
                    break;
                }
        }
        callback.apply(inserted, updated, deleted);
    }

    @FunctionalInterface
    public interface MatchKeyCallback<E> {
        boolean isKeyMatch(E sourceEntity, E targetEntity);
    }

    @FunctionalInterface
    public interface ComparedCollectionCallback <E> {
        void apply(List<E> inserted, List<E> updated, List<E> deleted);
    }
}
