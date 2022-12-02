package com.edev.support.ddd.join;

import com.edev.support.dao.BasicDao;
import com.edev.support.ddd.NullEntityException;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.Join;
import com.edev.support.entity.Entity;
import com.edev.support.utils.NameUtils;

import java.io.Serializable;
import java.util.*;

public class OneToManyForJoin<E extends Entity<S>, S extends Serializable> extends AbstractAssembler<E,S> implements Assembler<E,S> {
    public OneToManyForJoin(Join join, BasicDao dao) {
        super(join, dao);
    }

    private void validJoinKey(Collection<Entity<S>> collection, S id) {
        String joinKey = join.getJoinKey();
        collection.forEach(entity -> {
            Object value = entity.getValue(joinKey);
            if(value==null) entity.setValue(joinKey, id);
        });
    }
    @Override
    public void insertValue(E entity) {
        if(entity==null) throw new NullEntityException();
        S id = entity.getId();
        String name = join.getName();
        Collection<Entity<S>> collection = (Collection<Entity<S>>)entity.getValue(name);
        if(collection==null||collection.isEmpty()) return;
        validJoinKey(collection, id);
        dao.insertOrUpdateForList(collection);
    }

    @Override
    public void updateValue(E entity) {
        if(entity==null) throw new NullEntityException();
        S id = entity.getId();
        String name = join.getName();
        Collection<Entity<S>> collection = (Collection<Entity<S>>)entity.getValue(name);
        if(collection==null||collection.isEmpty()) return;
        validJoinKey(collection, id);

        String joinKey = join.getJoinKey();
        Entity<S> template = getTemplate();
        template.setValue(joinKey, id);
        Collection<Entity<S>> source = dao.loadAll(template);

        EntityUtils.compareListOrSetOfEntity(source, collection,
                (inserted, updated, deleted) -> {
                    inserted.forEach(dao::insert);
                    updated.forEach(dao::update);
                    deleted.forEach(dao::delete);
                });
    }

    @Override
    public void deleteValue(E entity) {
        if(entity==null) throw new NullEntityException();
        S id = entity.getId();
        String joinKey = join.getJoinKey();
        Entity<S> template = getTemplate();
        template.setValue(joinKey, id);
        dao.delete(template);
    }

    @Override
    public void setValue(E entity) {
        if(entity==null) throw new NullEntityException();
        S id = entity.getId();
        String joinKey = join.getJoinKey();
        Entity<S> template = getTemplate();
        template.setValue(joinKey, id);
        Collection<Entity<S>> value = dao.loadAll(template);
        if(value==null||value.isEmpty()) return;
        entity.setValue(join.getName(), value);
    }

    @Override
    public void setValueForList(Collection<E> list) {
        if(list==null||list.isEmpty()) return;
        String joinKey = join.getJoinKey();
        List<S> ids = new ArrayList<>();
        for(Entity<S> entity : list) ids.add(entity.getId());

        Map<Object, Object> map = new HashMap<>();
        map.put("key", NameUtils.convertToUnderline(joinKey));
        map.put("value", ids);
        map.put("opt", "IN");
        List<Map<Object, Object>> colMap = new ArrayList<>();
        colMap.add(map);

        Collection<Entity<S>> valueList = dao.loadAll(colMap, getClazz());
        if(valueList==null||valueList.isEmpty()) return;
        Map<S, List<Entity<S>>> sortedEntities = sortEntitiesByJoinKey(valueList);
        list.forEach(entity->{
            S id = entity.getId();
            List<Entity<S>> values = sortedEntities.get(id);
            if(values==null||values.isEmpty()) return;
            entity.setValue(join.getName(), values);
        });
    }

    private Map<S, List<Entity<S>>> sortEntitiesByJoinKey(Collection<Entity<S>> valueList) {
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
