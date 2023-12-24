package com.edev.support.ddd.join;

import com.edev.support.ddd.DddException;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.dsl.Ref;
import com.edev.support.entity.Entity;
import com.edev.support.utils.BeanUtils;
import com.edev.support.utils.SpringHelper;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

public class RefHelper<E extends Entity<S>, S extends Serializable> {
    private final SpringHelper springHelper;
    public RefHelper(SpringHelper springHelper) { this.springHelper = springHelper; }
    private void doWithRefs(@NotNull E entity, @NotNull Callback callback) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(entity.getClass());
        dObj.getRefs().forEach(callback::apply);
    }
    @FunctionalInterface
    private interface Callback {
        void apply(Ref ref);
    }

    /**
     * set values for each ref of the entity, if it has.
     * @param entity the entity
     */
    public void setRefs(E entity) {
        doWithRefs(entity, ref -> {
            String bean = ref.getBean();
            Object service = springHelper.getService(bean);
            String methodName = ref.getMethod();
            Method method = BeanUtils.getMethod(service, methodName);
            S id;
            if("oneToOne".equals(ref.getRefType()))
                id = entity.getId();
            else if("manyToOne".equals(ref.getRefType()))
                id = (S)entity.getValue(ref.getRefKey());
            else if("oneToMany".equals(ref.getRefType()))
                id = entity.getId();
            else
                throw new DddException("Not support this ref type: "+ref.getRefType());
            if(id==null) return;
            Object value = BeanUtils.invoke(service, method, id);
            entity.setValue(ref.getName(), value);
        });
    }

    public void doWithRefsForList(Collection<E> list, Callback callback) {
        if(list==null||list.isEmpty()) return;
        E entity = list.iterator().next();
        DomainObject dObj = DomainObjectFactory.getDomainObject(entity.getClass());
        dObj.getRefs().forEach(callback::apply);
    }

    /**
     * set values for each ref of every entity in a list.
     * @param list the list of entities
     */
    public void setRefForList(Collection<E> list) {
        doWithRefsForList(list, ref -> {
            String bean = ref.getBean();
            Object service = springHelper.getService(bean);
            String methodName = ref.getListMethod();
            Method method = BeanUtils.getMethod(service, methodName);
            if("oneToOne".equals(ref.getRefType())) {
                List<S> ids = new ArrayList<>();
                list.forEach(entity->ids.add(entity.getId()));
                Collection<Entity<S>> listOfEntities = (Collection<Entity<S>>) BeanUtils.invoke(service, method, ids);
                Map<S,Entity<S>> mapOfEntities = new HashMap<>();
                listOfEntities.forEach(entity->mapOfEntities.put(entity.getId(), entity));
                list.forEach(entity->{
                    Object value = mapOfEntities.get(entity.getId());
                    entity.setValue(ref.getName(), value);
                });
            } else if("manyToOne".equals(ref.getRefType())) {
                List<S> ids = new ArrayList<>();
                list.forEach(entity->ids.add((S)entity.getValue(ref.getRefKey())));
                Collection<Entity<S>> listOfEntities = (Collection<Entity<S>>) BeanUtils.invoke(service, method, ids);
                Map<S,Entity<S>> mapOfEntities = new HashMap<>();
                listOfEntities.forEach(entity->mapOfEntities.put(entity.getId(), entity));
                list.forEach(entity->{
                    Object value = mapOfEntities.get(entity.getValue(ref.getRefKey()));
                    entity.setValue(ref.getName(), value);
                });
            } else if("oneToMany".equals(ref.getRefType())) {
                List<S> ids = new ArrayList<>();
                list.forEach(entity->ids.add(entity.getId()));
                Collection<Entity<S>> listOfEntities = (Collection<Entity<S>>) BeanUtils.invoke(service, method, ids);
                Map<S,List<Entity<S>>> mapOfValues = new HashMap<>();
                listOfEntities.forEach(entity->{
                    S key = (S)entity.getValue(ref.getRefKey());
                    List<Entity<S>> value = mapOfValues.get(key);
                    if(value==null) mapOfValues.put(key,new ArrayList<>());
                    mapOfValues.get(key).add(entity);
                });
                list.forEach(entity->{
                    Object value = mapOfValues.get(entity.getId());
                    entity.setValue(ref.getName(), value);
                });
            }
        });
    }
}
