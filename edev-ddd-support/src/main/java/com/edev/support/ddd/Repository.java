package com.edev.support.ddd;

import com.edev.support.dao.BasicDao;
import com.edev.support.dao.impl.DecoratorDao;
import com.edev.support.ddd.join.JoinHelper;
import com.edev.support.ddd.join.RefHelper;
import com.edev.support.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Repository extends DecoratorDao implements BasicDao {
    @Autowired
    private ApplicationContext context;

    public Repository() {
        super();
    }
    public Repository(BasicDao dao) {
        super(dao);
    }
    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> S insert(E entity) {
        S id = super.insert(entity);
        JoinHelper<E,S> joinHelper = new JoinHelper<>(getDao());
        if(joinHelper.hasJoinAndAggregation(entity))
            joinHelper.insertJoins(entity);
        return id;
    }

    private <E extends Entity<S>, S extends Serializable> void updateWithJoin(E entity) {
        JoinHelper<E,S> joinHelper = new JoinHelper<>(getDao());
        if(joinHelper.hasJoinAndAggregation(entity))
            joinHelper.updateJoins(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void update(E entity) {
        super.update(entity);
        updateWithJoin(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(E entity) {
        S id = super.insertOrUpdate(entity);
        updateWithJoin(entity);
        return id;
    }

    private <E extends Entity<S>, S extends Serializable> void deleteWithJoin(E entity) {
        JoinHelper<E,S> joinHelper = new JoinHelper<>(getDao());
        if(joinHelper.hasJoinAndAggregation(entity))
            joinHelper.deleteJoins(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void delete(E entity) {
        super.delete(entity);
        deleteWithJoin(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>> void insertOrUpdateForList(C list) {
        list.forEach(this::insertOrUpdate);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>> void deleteForList(C list) {
        list.forEach(this::delete);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void deleteForList(Collection<S> ids, Class<E> clazz) {
        ids.forEach(id->delete(id,clazz));
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void delete(S id, Class<E> clazz) {
        JoinHelper<E,S> joinHelper = new JoinHelper<>(getDao());
        if(joinHelper.hasJoinAndAggregation(clazz)) {
            E entity = this.load(id, clazz);
            if(entity!=null) delete(entity);
        } else super.delete(id, clazz);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> E load(S id, Class<E> clazz) {
        E entity = super.load(id, clazz);
        if(entity==null) return null;
        (new JoinHelper<E,S>(this)).setJoins(entity);
        (new RefHelper<E,S>(context)).setRefs(entity);
        return entity;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadForList(Collection<S> ids, Class<E> clazz) {
        Collection<E> collection = super.loadForList(ids, clazz);
        (new JoinHelper<E,S>(this)).setJoinForList(collection);
        (new RefHelper<E,S>(context)).setRefForList(collection);
        return collection;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(E template) {
        Collection<E> collection = super.loadAll(template);
        (new JoinHelper<E,S>(this)).setJoinForList(collection);
        (new RefHelper<E,S>(context)).setRefForList(collection);
        return collection;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(List<Map<Object, Object>> colMap, Class<E> clazz) {
        Collection<E> collection = super.loadAll(colMap, clazz);
        (new JoinHelper<E,S>(this)).setJoinForList(collection);
        (new RefHelper<E,S>(context)).setRefForList(collection);
        return collection;
    }
}
