package com.edev.support.ddd.join;

import com.edev.support.dao.BasicDao;
import com.edev.support.ddd.DddException;
import com.edev.support.dsl.Join;
import com.edev.support.entity.Entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

public class ManyToOneForJoin<E extends Entity<S>, S extends Serializable> extends AbstractRelation<E,S> implements Relation<E,S> {
    public ManyToOneForJoin(Join join, BasicDao dao) {
        super(join, dao);
    }

    @Override
    public void insertValue(@NotNull E entity) {
        if(!join.isAggregation()) return;
        throw new DddException("no aggregation for many-to-one relation! Check your design: %s", entity);
    }

    @Override
    public void updateValue(@NotNull E entity) {
        if(!join.isAggregation()) return;
        throw new DddException("no aggregation for many-to-one relation! Check your design: %s", entity);
    }

    @Override
    public void deleteValue(@NotNull E entity) {
        if(!join.isAggregation()) return;
        throw new DddException("no aggregation for many-to-one relation! Check your design: %s", entity);
    }

    @Override
    public void setValue(@NotNull E entity) {
        String joinKey = join.getJoinKey();
        S id = (S) entity.getValue(joinKey);
        if(id==null) return;
        Entity<S> value = dao.load(id, getClazz());
        if(value==null) return;
        entity.setValue(join.getName(), value);
    }

    @Override
    public void setValueForList(Collection<E> list) {
        if(list==null||list.isEmpty()) return;
        String joinKey = join.getJoinKey();
        List<S> ids = new ArrayList<>();
        list.forEach(entity->{
            S value = (S)entity.getValue(joinKey);
            if(value!=null) ids.add(value);
        });
        if(ids.isEmpty()) return;
        Collection<Entity<S>> valueList = dao.loadForList(ids, getClazz());
        if(valueList==null||valueList.isEmpty()) return;
        setListOfValuesToEachEntity(list, valueList);
    }

    private void setListOfValuesToEachEntity(Collection<E> list, Collection<Entity<S>> valueList) {
        if(list==null||valueList==null) return;
        Map<S,Entity<S>> mapOfEntitiesNeedJoin = new HashMap<>();
        valueList.forEach(value->mapOfEntitiesNeedJoin.put(value.getId(), value));
        list.forEach(entity->{
            String joinKey = join.getJoinKey();
            Entity<S> value = mapOfEntitiesNeedJoin.get(entity.getValue(joinKey));
            if(value==null) return;
            entity.setValue(join.getName(), value);
        });
    }
}
