package com.edev.support.ddd.join;

import com.edev.support.dao.BasicDao;
import com.edev.support.dao.impl.utils.DaoEntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.Join;
import com.edev.support.entity.Entity;
import com.edev.support.utils.NameUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

public class OneToManyForJoin<E extends Entity<S>, S extends Serializable> extends AbstractRelation<E,S> implements Relation<E,S> {
    public OneToManyForJoin(Join join, BasicDao dao) {
        super(join, dao);
    }

    /**
     * set the join key's value for each member of the collection
     * @param collection the collection of entities
     * @param value the join key's value
     */
    private void setJoinKeyForEach(Collection<Entity<S>> collection, S value) {
        String joinKey = join.getJoinKey();
        collection.forEach(entity -> {
            if(entity.getValue(joinKey)==null) entity.setValue(joinKey, value);
        });
    }
    @Override
    @Transactional
    public void insertValue(@NotNull E entity) {
        if(!join.isAggregation()) return;
        S id = entity.getId();
        String name = join.getName();
        Collection<Entity<S>> collection = (Collection<Entity<S>>)entity.getValue(name);
        if(collection==null||collection.isEmpty()) return;
        setJoinKeyForEach(collection, id);
        collection.forEach(dao::insert);
    }

    @Override
    @Transactional
    public void updateValue(@NotNull E entity) {
        if(!join.isAggregation()) return;
        S id = entity.getId();
        String name = join.getName();
        Collection<Entity<S>> collection = (Collection<Entity<S>>)entity.getValue(name);
        if(collection==null||collection.isEmpty()) return;
        setJoinKeyForEach(collection, id);

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
    public void deleteValue(@NotNull E entity) {
        if(!join.isAggregation()) return;
        S id = entity.getId();
        String joinKey = join.getJoinKey();
        Entity<S> template = getTemplate();
        template.setValue(joinKey, id);
        dao.delete(template);
    }

    @Override
    public void setValue(@NotNull E entity) {
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
        map.put(DaoEntityBuilder.KEY, NameUtils.convertToUnderline(joinKey));
        map.put(DaoEntityBuilder.VALUE, ids);
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
}
