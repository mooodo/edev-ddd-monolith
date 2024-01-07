package com.edev.support.ddd;

import com.edev.support.dao.BasicDao;
import com.edev.support.dao.impl.DecoratorDao;
import com.edev.support.ddd.join.JoinHelper;
import com.edev.support.ddd.join.RefHelper;
import com.edev.support.entity.Entity;
import com.edev.support.utils.SpringHelper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Repository extends DecoratorDao implements BasicDao {
    @Autowired
    private SpringHelper springHelper;

    public Repository(@NonNull BasicDao dao) {
        super(dao);
    }
    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> S insert(@NonNull E entity) {
        S id = super.insert(entity);
        JoinHelper<E,S> joinHelper = new JoinHelper<>(getDao());
        if(joinHelper.hasJoinAndAggregation(entity))
            joinHelper.insertJoins(entity);
        return id;
    }

    private <E extends Entity<S>, S extends Serializable> void updateWithJoin(@NonNull E entity) {
        JoinHelper<E,S> joinHelper = new JoinHelper<>(getDao());
        if(joinHelper.hasJoinAndAggregation(entity))
            joinHelper.updateJoins(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void update(@NonNull E entity) {
        super.update(entity);
        updateWithJoin(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(@NonNull E entity) {
        S id = super.insertOrUpdate(entity);
        updateWithJoin(entity);
        return id;
    }

    private <E extends Entity<S>, S extends Serializable> void deleteWithJoin(@NonNull E entity) {
        JoinHelper<E,S> joinHelper = new JoinHelper<>(getDao());
        if(joinHelper.hasJoinAndAggregation(entity))
            joinHelper.deleteJoins(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void delete(@NonNull E template) {
        Collection<E> entities = super.loadAll(template);
        entities.forEach(this::deleteWithJoin);
        super.delete(template);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>>
            void insertOrUpdateForList(@NonNull C list) {
        list.forEach(this::insertOrUpdate);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>>
        void deleteForList(@NonNull C list) {
        list.forEach(this::delete);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable>
        void deleteForList(@NonNull Collection<S> ids, @NonNull Class<E> clazz) {
        ids.forEach(id->delete(id,clazz));
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable>
            void delete(@NonNull S id, @NonNull Class<E> clazz) {
        JoinHelper<E,S> joinHelper = new JoinHelper<>(getDao());
        if(joinHelper.hasJoinAndAggregation(clazz)) {
            E entity = this.load(id, clazz);
            if(entity!=null) deleteWithJoin(entity);
        }
        super.delete(id, clazz);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            E load(@NonNull S id, @NonNull Class<E> clazz) {
        E entity = super.load(id, clazz);
        if(entity==null||Repository.isNotJoin()) return entity;
        (new JoinHelper<E,S>(this)).setJoins(entity);
        (new RefHelper<E,S>(springHelper)).setRefs(entity);
        return entity;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadForList(@NonNull Collection<S> ids, @NonNull Class<E> clazz) {
        Collection<E> collection = super.loadForList(ids, clazz);
        if(collection==null||collection.isEmpty()||Repository.isNotJoin())
            return collection;
        (new JoinHelper<E,S>(this)).setJoinForList(collection);
        (new RefHelper<E,S>(springHelper)).setRefForList(collection);
        return collection;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(@NonNull E template) {
        Collection<E> collection = super.loadAll(template);
        if(collection==null||collection.isEmpty()||Repository.isNotJoin())
            return collection;
        (new JoinHelper<E,S>(this)).setJoinForList(collection);
        (new RefHelper<E,S>(springHelper)).setRefForList(collection);
        return collection;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadAll(@NonNull List<Map<Object, Object>> colMap, @NonNull Class<E> clazz) {
        Collection<E> collection = super.loadAll(colMap, clazz);
        if(collection==null||collection.isEmpty()||Repository.isNotJoin())
            return collection;
        (new JoinHelper<E,S>(this)).setJoinForList(collection);
        (new RefHelper<E,S>(springHelper)).setRefForList(collection);
        return collection;
    }

    private static final ThreadLocal<Boolean> isNotJoin = new ThreadLocal<>();

    public static void setNotJoin(Boolean value) {
        isNotJoin.set(value);
    }

    public static boolean isNotJoin() {
        if(isNotJoin.get()==null) isNotJoin.set(false);
        return isNotJoin.get();
    }

    public static void removeNotJoin() {
        isNotJoin.remove();
    }
}
