package com.edev.support.ddd.join;

import com.edev.support.dao.BasicDao;
import com.edev.support.dsl.Join;
import com.edev.support.entity.Entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

public class OneToOneForJoin<E extends Entity<S>, S extends Serializable> extends AbstractRelation<E,S> implements Relation<E,S> {
    public OneToOneForJoin(Join join, BasicDao dao) {
        super(join, dao);
    }

    @Override
    public void insertValue(@NotNull E entity) {
        if(!join.isAggregation()) return;
        String name = join.getName();
        Entity<?> value = (Entity<?>) entity.getValue(name);
        if(value==null) return;
        dao.insert(value);
    }

    @Override
    public void updateValue(@NotNull E entity) {
        if(!join.isAggregation()) return;
        String name = join.getName();
        Entity<?> value = (Entity<?>) entity.getValue(name);
        if(value==null) deleteValue(entity);
        else dao.insertOrUpdate(value);
    }

    @Override
    public void deleteValue(@NotNull E entity) {
        if(!join.isAggregation()) return;
        S id = entity.getId();
        dao.delete(id, getClazz());
    }

    @Override
    public void setValue(@NotNull E entity) {
        S id = entity.getId();
        Entity<S> value = dao.load(id, getClazz());
        if(value==null) return;
        entity.setValue(join.getName(), value);
    }

    @Override
    public void setValueForList(Collection<E> list) {
        if(list==null||list.isEmpty()) return;
        List<S> ids = new ArrayList<>();
        for(E entity : list) ids.add(entity.getId());
        Collection<Entity<S>> valueList = dao.loadForList(ids, getClazz());
        setListOfValuesToEachEntity(list, valueList);
    }

    private void setListOfValuesToEachEntity(Collection<E> list, Collection<Entity<S>> valueList) {
        if(list==null||valueList==null) return;
        Map<S,Entity<S>> mapOfEntitiesNeedJoin = new HashMap<>();
        valueList.forEach(value->mapOfEntitiesNeedJoin.put(value.getId(), value));
        list.forEach(entity->{
            Entity<S> value = mapOfEntitiesNeedJoin.get(entity.getId());
            if(value==null) return;
            entity.setValueByMethod(join.getName(), value);
        });
    }
}
