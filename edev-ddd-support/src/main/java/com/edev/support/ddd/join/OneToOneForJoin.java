package com.edev.support.ddd.join;

import com.edev.support.dao.BasicDao;
import com.edev.support.ddd.NullEntityException;
import com.edev.support.dsl.Join;
import com.edev.support.entity.Entity;

import java.io.Serializable;
import java.util.*;

public class OneToOneForJoin<E extends Entity<S>, S extends Serializable> extends AbstractAssembler<E,S> implements Assembler<E,S> {
    public OneToOneForJoin(Join join, BasicDao dao) {
        super(join, dao);
    }

    @Override
    public void insertValue(E entity) {
        if(entity==null) throw new NullEntityException();
        String name = join.getName();
        Entity<?> value = (Entity<?>) entity.getValue(name);
        if(value==null) return;
        dao.insert(value);
    }

    @Override
    public void updateValue(E entity) {
        if(entity==null) throw new NullEntityException();
        String name = join.getName();
        Entity<?> value = (Entity<?>) entity.getValue(name);
        if(value==null) deleteValue(entity);
        else dao.insertOrUpdate(value);
    }

    @Override
    public void deleteValue(E entity) {
        if(entity==null) throw new NullEntityException();
        S id = entity.getId();
        dao.delete(id, getClazz());
    }

    @Override
    public void setValue(E entity) {
        if(entity==null) throw new NullEntityException();
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
