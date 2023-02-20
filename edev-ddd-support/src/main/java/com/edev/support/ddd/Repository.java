package com.edev.support.ddd;

import com.edev.support.dao.BasicDao;
import com.edev.support.dao.impl.DecoratorDao;
import com.edev.support.ddd.join.JoinHelper;
import com.edev.support.ddd.join.RefHelper;
import com.edev.support.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Repository extends DecoratorDao implements BasicDao {
    @Autowired
    private ApplicationContext context;

    public Repository(BasicDao dao) {
        super(dao);
    }
    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> S insert(@NotNull E entity) {
        S id = super.insert(entity);
        JoinHelper<E,S> joinHelper = new JoinHelper<>(getDao());
        if(joinHelper.hasJoinAndAggregation(entity))
            joinHelper.insertJoins(entity);
        return id;
    }

    private <E extends Entity<S>, S extends Serializable> void updateWithJoin(@NotNull E entity) {
        JoinHelper<E,S> joinHelper = new JoinHelper<>(getDao());
        if(joinHelper.hasJoinAndAggregation(entity))
            joinHelper.updateJoins(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void update(@NotNull E entity) {
        super.update(entity);
        updateWithJoin(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(@NotNull E entity) {
        S id = super.insertOrUpdate(entity);
        updateWithJoin(entity);
        return id;
    }

    private <E extends Entity<S>, S extends Serializable> void deleteWithJoin(@NotNull E entity) {
        JoinHelper<E,S> joinHelper = new JoinHelper<>(getDao());
        if(joinHelper.hasJoinAndAggregation(entity))
            joinHelper.deleteJoins(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void delete(@NotNull E entity) {
        super.delete(entity);
        deleteWithJoin(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>>
            void insertOrUpdateForList(@NotNull C list) {
        list.forEach(this::insertOrUpdate);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>>
        void deleteForList(@NotNull C list) {
        list.forEach(this::delete);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable>
        void deleteForList(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        ids.forEach(id->delete(id,clazz));
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable>
            void delete(@NotNull S id, @NotNull Class<E> clazz) {
        JoinHelper<E,S> joinHelper = new JoinHelper<>(getDao());
        if(joinHelper.hasJoinAndAggregation(clazz)) {
            E entity = this.load(id, clazz);
            if(entity!=null) deleteWithJoin(entity);
        }
        super.delete(id, clazz);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            E load(@NotNull S id, @NotNull Class<E> clazz) {
        E entity = super.load(id, clazz);
        if(entity==null||Repository.isNotJoin()) return entity;
        (new JoinHelper<E,S>(this)).setJoins(entity);
        (new RefHelper<E,S>(context)).setRefs(entity);
        return entity;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadForList(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        Collection<E> collection = super.loadForList(ids, clazz);
        if(collection==null||collection.isEmpty()||Repository.isNotJoin())
            return collection;
        (new JoinHelper<E,S>(this)).setJoinForList(collection);
        (new RefHelper<E,S>(context)).setRefForList(collection);
        return collection;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(@NotNull E template) {
        Collection<E> collection = super.loadAll(template);
        if(collection==null||collection.isEmpty()||Repository.isNotJoin())
            return collection;
        (new JoinHelper<E,S>(this)).setJoinForList(collection);
        (new RefHelper<E,S>(context)).setRefForList(collection);
        return collection;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadAll(@NotNull List<Map<Object, Object>> colMap, @NotNull Class<E> clazz) {
        Collection<E> collection = super.loadAll(colMap, clazz);
        if(collection==null||collection.isEmpty()||Repository.isNotJoin())
            return collection;
        (new JoinHelper<E,S>(this)).setJoinForList(collection);
        (new RefHelper<E,S>(context)).setRefForList(collection);
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
