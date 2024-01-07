package com.edev.support.ddd.join;

import com.edev.support.dao.BasicDao;
import com.edev.support.ddd.Repository;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.Join;
import com.edev.support.entity.Entity;
import com.edev.support.utils.NameUtils;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

public class ManyToManyForJoin<E extends Entity<S>, S extends Serializable> extends AbstractRelation<E,S> implements Relation<E,S> {
    public ManyToManyForJoin(@NonNull Join join, @NonNull BasicDao dao) {super(join, dao);}

    private Entity<S> createJoinObject() {
        return (new EntityBuilder<Entity<S>, S>(join.getJoinClass())).createEntity();
    }

    private Collection<Entity<S>> createJoinObjects(@NonNull E entity) {
        S id = entity.getId();
        String name = join.getName();
        Collection<Entity<S>> collection = (Collection<Entity<S>>)entity.getValue(name);
        if(collection==null||collection.isEmpty()) return new ArrayList<>();

        Collection<Entity<S>> joinObjects = new ArrayList<>();
        collection.forEach(e -> {
            Entity<S> joinObj = createJoinObject();
            joinObj.setValue(join.getJoinKey(), id);
            joinObj.setValue(join.getJoinClassKey(), e.getId());
            joinObjects.add(joinObj);
        });
        return joinObjects;
    }

    @Override
    @Transactional
    public void insertValue(E entity) {
        if(entity==null) return;
        Collection<Entity<S>> joinObjects = createJoinObjects(entity);
        joinObjects.forEach(dao::insert);
    }

    @Override
    @Transactional
    public void updateValue(E entity) {
        if(entity==null) return;
        Collection<Entity<S>> collection = createJoinObjects(entity);

        String joinKey = join.getJoinKey();
        String joinClassKey = join.getJoinClassKey();
        Entity<S> template = createJoinObject();
        template.setValue(joinKey, entity.getId());
        Collection<Entity<S>> source = dao.loadAll(template);
        EntityUtils.compareListOrSetOfEntity(source, collection, (sourceEntity, targetEntity)->
                targetEntity.getValue(joinKey).equals(sourceEntity.getValue(joinKey))
                &&targetEntity.getValue(joinClassKey).equals(sourceEntity.getValue(joinClassKey)),
                (inserted, updated, deleted) -> {
                    inserted.forEach(dao::insert);
                    deleted.forEach(dao::delete);
        });
    }

    @Override
    @Transactional
    public void deleteValue(E entity) {
        if(entity==null) return;
        S id = entity.getId();
        String joinKey = join.getJoinKey();
        Entity<S> template = createJoinObject();
        template.setValue(joinKey, id);
        Collection<Entity<S>> joinObjects = dao.loadAll(template);
        dao.deleteForList(joinObjects);
    }

    @Override
    public void setValue(E entity) {
        if(entity==null) return;
        S id = entity.getId();
        String joinKey = join.getJoinKey();
        Entity<S> template = createJoinObject();
        template.setValue(joinKey, id);
        Collection<Entity<S>> joinObjects = dao.loadAll(template);

        List<S> ids = new ArrayList<>();
        joinObjects.forEach(e-> ids.add((S)e.getValue(join.getJoinClassKey())));
        Repository.setNotJoin(true);
        Collection<Entity<S>> collection = dao.loadForList(ids, EntityUtils.getClassOfEntity(join.getClazz()));
        Repository.setNotJoin(false);
        entity.setValue(join.getName(), collection);
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
        Collection<Entity<S>> joinObjectList = dao.loadAll(colMap, EntityUtils.getClassOfEntity(join.getJoinClass()));
        if(joinObjectList==null||joinObjectList.isEmpty()) return;

        Map<S, List<Entity<S>>> sortedEntities = sortEntitiesByJoinKey(joinObjectList);
        list.forEach(entity->{
            S id = entity.getId();
            List<Entity<S>> joinObjects = sortedEntities.get(id);
            if(joinObjects==null||joinObjects.isEmpty()) return;

            List<S> idList = new ArrayList<>();
            joinObjects.forEach(e-> idList.add((S)e.getValue(join.getJoinClassKey())));
            Repository.setNotJoin(true);
            Collection<Entity<S>> collection = dao.loadForList(idList, EntityUtils.getClassOfEntity(join.getClazz()));
            Repository.setNotJoin(false);
            entity.setValue(join.getName(), collection);
        });
    }
}
