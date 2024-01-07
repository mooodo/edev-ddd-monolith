package com.edev.support.ddd.join;

import com.edev.support.dao.BasicDao;
import com.edev.support.ddd.DddException;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.dsl.Join;
import com.edev.support.entity.Entity;
import com.edev.support.utils.BeanUtils;
import lombok.NonNull;

import java.io.Serializable;
import java.util.*;

public abstract class AbstractRelation<E extends Entity<S>, S extends Serializable> implements Relation<E,S> {
    protected Join join;
    protected BasicDao dao;

    protected AbstractRelation(@NonNull Join join, @NonNull BasicDao dao) {
        this.join = join;
        this.dao = dao;
    }

    protected Entity<S> getTemplate() {
        return (new EntityBuilder<Entity<S>, S>(join.getClazz())).createEntity();
    }

    protected Class<Entity<S>> getClazz() {
        Class<?> clazz = BeanUtils.getClazz(join.getClazz());
        if(!Entity.class.isAssignableFrom(clazz))
            throw new DddException("The class is not an entity: ["+join.getClazz()+"]");
        return (Class<Entity<S>>) clazz;
    }
    protected Map<S, List<Entity<S>>> sortEntitiesByJoinKey(@NonNull Collection<Entity<S>> valueList) {
        String joinKey = join.getJoinKey();
        Map<S, List<Entity<S>>> sortEntities = new HashMap<>();
        valueList.forEach(entity -> {
            S id = (S)entity.getValue(joinKey);
            if(sortEntities.get(id)==null) sortEntities.put(id, new ArrayList<>());
            sortEntities.get(id).add(entity);
        });
        return sortEntities;
    }
}
