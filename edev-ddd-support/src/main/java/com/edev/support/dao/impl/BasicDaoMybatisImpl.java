package com.edev.support.dao.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.dao.impl.utils.DaoEntityBuilder;
import com.edev.support.dao.impl.utils.DaoExecutor;
import com.edev.support.ddd.DddFactory;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BasicDaoMybatisImpl implements BasicDao {
    @Autowired
    private DddFactory dddFactory;
    @Autowired
    private DaoExecutor daoExecutor;
    @Override
    public <E extends Entity<S>, S extends Serializable> S insert(@NotNull E entity) {
        DaoEntity daoEntity = DaoEntityBuilder.build(entity);
        return daoExecutor.insert(daoEntity, entity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void update(@NotNull E entity) {
        DaoEntity daoEntity = DaoEntityBuilder.build(entity);
        daoExecutor.update(daoEntity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(@NotNull E entity) {
        try {
            insert(entity);
        } catch (DataAccessException e) {
            if(e.getCause() instanceof SQLIntegrityConstraintViolationException)
                update(entity);
            else throw e;
        }
        return entity.getId();
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void delete(@NotNull E entity) {
        DaoEntity daoEntity = DaoEntityBuilder.build(entity);
        daoExecutor.delete(daoEntity);
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
    public <E extends Entity<S>, S extends Serializable>
            void delete(@NotNull S id, @NotNull Class<E> clazz) {
        E template = EntityBuilder.build(clazz);
        template.setId(id);
        delete(template);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            E load(@NotNull S id, @NotNull Class<E> clazz) {
        E template = EntityBuilder.build(clazz);
        template.setId(id);
        DaoEntity daoEntity = DaoEntityBuilder.build(template);
        List<E> list = daoExecutor.find(daoEntity, clazz);
        if(list==null||list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable>
            void deleteForList(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        if(ids.isEmpty()) return;
        DaoEntity daoEntity = DaoEntityBuilder.buildForList(ids, clazz);
        daoExecutor.deleteForList(daoEntity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadForList(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        if(ids.isEmpty()) return new ArrayList<>();
        DaoEntity daoEntity = DaoEntityBuilder.buildForList(ids, clazz);
        return daoExecutor.load(daoEntity, clazz);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadAll(@NotNull E template) {
        DaoEntity daoEntity = DaoEntityBuilder.build(template);
        return daoExecutor.find(daoEntity, EntityUtils.getClass(template));
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadAll(List<Map<Object, Object>> colMap, Class<E> clazz) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
        String tableName = dObj.getTable();
        DaoEntity daoEntity = new DaoEntity();
        daoEntity.setTableName(tableName);
        daoEntity.setColMap(colMap);
        return daoExecutor.load(daoEntity, clazz);
    }
}
