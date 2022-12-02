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
            if(clazz.getSuperclass()!=null&&DomainObjectFactory.isExists(clazz.getSuperclass().getName())) return true;
            if (DomainObjectFactory.isExists(clazz.getName())) return true;
            throw new DddException("The entity[%s] isn't register in domain objects register files.", clazz.getName());
        }
        return false;
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

    public static boolean hasSubClass(DomainObject dObj) {
        String subClassType = dObj.getSubClassType();
        return subClassType!=null&&!subClassType.equals("");
    }

    public static <E extends Entity<S>, S extends Serializable>
            Class<E> getClassOfEntity(String className) {
        Class<?> clazz = BeanUtils.getClazz(className);
        if(!Entity.class.isAssignableFrom(clazz))
            throw new DddException("The class is not an entity: [%s]", className);
        return (Class<E>)clazz;
    }

    public static <E extends Entity<S>, S extends Serializable>
            void compareListOrSetOfEntity(Collection<E> source, Collection<E> target, ComparedCollectionCallback<E> callback) {
        if(source==null) source = new ArrayList<>();
        if(target==null) target = new ArrayList<>();

        List<E> inserted = new ArrayList<>();
        List<E> updated = new ArrayList<>();
        List<E> deleted = new ArrayList<>(source);
        for(E targetEntity : target) {
            boolean isMatched = false;
            for (E sourceEntity : source) {
                if(targetEntity.getId().equals(sourceEntity.getId())) {
                    isMatched = true;
                    if(!targetEntity.equals(sourceEntity))
                        updated.add(targetEntity);
                    break;
                }
            }
            if(!isMatched) inserted.add(targetEntity);
            deleted.remove(targetEntity);
        }
        callback.apply(inserted, updated, deleted);
    }

    @FunctionalInterface
    public interface ComparedCollectionCallback <E> {
        void apply(List<E> inserted, List<E> updated, List<E> deleted);
    }
}
