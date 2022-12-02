package com.edev.support.dao.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.dao.impl.mybatis.GenericDao;
import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.ddd.DddFactory;
import com.edev.support.ddd.NullEntityException;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BasicDaoMybatisImpl implements BasicDao {
    @Autowired
    private GenericDao dao;
    @Autowired
    private DddFactory dddFactory;

    @Override
    public <E extends Entity<S>, S extends Serializable> S insert(E entity) {
        if(entity==null) throw new NullEntityException();
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(entity);
        try {
            if(entity.getId()!=null)
                dao.insert(daoEntity.getTableName(), daoEntity.getColMap());
            else
                dao.insertGenerateKeys(daoEntity.getTableName(), daoEntity.getColMap(), entity);
            return entity.getId();
        } catch (DataAccessException e) {
            throw new DaoException("error when insert entity[%s]", e, entity);
        }
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void update(E entity) {
        if(entity==null) throw new NullEntityException();
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(entity);
        try {
            dao.update(daoEntity.getTableName(), daoEntity.getColMap(), daoEntity.getPkMap());
        } catch (DataAccessException e) {
            throw new DaoException("error when update entity[%s]", e, entity);
        }
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(E entity) {
        if(entity==null) throw new NullEntityException();
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(entity);
        try {
            if(entity.getId()!=null)
                dao.insert(daoEntity.getTableName(), daoEntity.getColMap());
            else
                dao.insertGenerateKeys(daoEntity.getTableName(), daoEntity.getColMap(), entity);
            return entity.getId();
        } catch (DataAccessException e) {
            if(e.getCause() instanceof SQLIntegrityConstraintViolationException)
                update(entity);
            else throw new DaoException("error when insert entity[%s]", e, entity);
        }
        return entity.getId();
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void delete(E entity) {
        if(entity==null) throw new NullEntityException();
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(entity);
        try {
            dao.delete(daoEntity.getTableName(), daoEntity.getColMap());
        } catch (DataAccessException e) {
            throw new DaoException("error when delete entity[%s]", e, entity);
        }
    }

    @Override
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>>
            void insertOrUpdateForList(C list) {
        if(list==null) throw new NullEntityException();
        list.forEach(this::insertOrUpdate);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>>
            void deleteForList(C list) {
        if(list==null) throw new NullEntityException();
        list.forEach(this::delete);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void delete(S id, Class<E> clazz) {
        if(id==null||clazz==null) throw new DaoException("The id or clazz is null");
        E template = EntityBuilder.build(clazz);
        template.setId(id);
        delete(template);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> E load(S id, Class<E> clazz) {
        if(id==null||clazz==null) throw new DaoException("The id or clazz is null");
        E template = EntityBuilder.build(clazz);
        template.setId(id);
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(template);
        List<Map<String, Object>> list = dao.find(daoEntity.getTableName(), daoEntity.getPkMap());
        if(list==null||list.isEmpty()) return null;
        return dddFactory.createEntityByRow(clazz, list.get(0));
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void deleteForList(Collection<S> ids, Class<E> clazz) {
        if(ids==null||clazz==null) throw new DaoException("The ids or clazz is null");
        if(ids.isEmpty()) return;
        DaoEntity daoEntity = DaoEntity.prepareForList(ids, clazz);
        dao.deleteForList(daoEntity.getTableName(), daoEntity.getPkMap());
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadForList(Collection<S> ids, Class<E> clazz) {
        if(ids==null||clazz==null) throw new DaoException("The ids or clazz is null");
        if(ids.isEmpty()) return new ArrayList<>();
        DaoEntity daoEntity = DaoEntity.prepareForList(ids, clazz);
        List<Map<String, Object>> listOfMap = dao.load(daoEntity.getTableName(), daoEntity.getPkMap());
        return dddFactory.createEntityByRowForList(clazz, listOfMap);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(E template) {
        if(template==null) throw new DaoException("The template is null!");
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(template);
        List<Map<String, Object>> list = dao.find(daoEntity.getTableName(), daoEntity.getColMap());
        return dddFactory.createEntityByRowForList((Class<E>) template.getClass(), list);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadAll(List<Map<Object, Object>> colMap, Class<E> clazz) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
        String tableName = dObj.getTable();
        List<Map<String, Object>> list = dao.load(tableName, colMap);
        return dddFactory.createEntityByRowForList(clazz, list);
    }
}
