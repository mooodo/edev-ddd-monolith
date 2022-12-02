package com.edev.support.subclass;

import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.ddd.NullEntityException;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.entity.Entity;
import com.edev.support.subclass.exception.SubClassException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component()
public class UnionSubClass extends AbstractSubClass implements SubClassDao {
    @Override
    public boolean available(DomainObject dObj) {
        return dObj.getSubClassType().equalsIgnoreCase("union");
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insert(E entity) {
        if(entity==null) throw new NullEntityException();
        if(isParent(entity)) entity = createSubClassByParent(entity);
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(entity);
        try {
            doInsert(daoEntity);
        } catch (DataAccessException e) {
            throw new SubClassException("error when insert entity: [%s]", e, entity);
        }
        return entity.getId();
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void update(E entity) {
        if(entity==null) throw new NullEntityException();
        if(isParent(entity)) entity = createSubClassByParent(entity);
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(entity);
        try {
            doUpdate(daoEntity);
        } catch (DataAccessException e) {
            throw new SubClassException("error when update entity[%s]", e, entity);
        }
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(E entity) {
        if(entity==null) throw new NullEntityException();
        if(isParent(entity)) entity = createSubClassByParent(entity);
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(entity);
        try {
            doInsertOrUpdate(daoEntity);
        } catch (DataAccessException e) {
            throw new SubClassException("error when insert or update entity: [%s]", e, entity);
        }
        return entity.getId();
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void delete(E entity) {
        if(entity==null) throw new NullEntityException();
        if(isParent(entity)) entity = createSubClassByParent(entity);
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(entity);
        try {
            doDelete(daoEntity);
        } catch (DataAccessException e) {
            throw new SubClassException("error when delete entity[%s]", e, entity);
        }
    }

    @Override
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>> void insertOrUpdateForList(C list) {
        if(list==null) throw new SubClassException("The list is null!");
        list.forEach(this::insertOrUpdate);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>> void deleteForList(C list) {
        if(list==null) throw new SubClassException("The list is null!");
        list.forEach(this::delete);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void delete(S id, Class<E> clazz) {
        E entity = load(id, clazz);
        if(entity==null) return;
        delete(entity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> E load(S id, Class<E> clazz) {
        if(id==null||clazz==null) throw new SubClassException("The id or class is null!");
        if(isParent(clazz))
            throw new SubClassException("Not support this because of low efficient. Please use a child class or change to another subclass type!");
        E template = EntityBuilder.build(clazz);
        template.setId(id);
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(template);
        List<Map<String, Object>> list = dao.find(daoEntity.getTableName(), daoEntity.getPkMap());
        if(list==null||list.isEmpty()) return null;
        return dddFactory.createEntityByRow(clazz, list.get(0));
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void deleteForList(Collection<S> ids, Class<E> clazz) {
        if(ids==null||clazz==null) throw new SubClassException("The id or class is null!");
        if(isParent(clazz))
            throw new SubClassException("Not support this because of low efficient. Please use a child class or change to another subclass type!");
        DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
        DaoEntity daoEntity = DaoEntity.prepareForList(ids, clazz);
        dao.deleteForList(daoEntity.getTableName(), daoEntity.getPkMap());
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadForList(Collection<S> ids, Class<E> clazz) {
        if(ids==null||clazz==null) throw new SubClassException("The id or class is null!");
        if(isParent(clazz))
            throw new SubClassException("Not support this because of low efficient. Please use a child class or change to another subclass type!");
        DaoEntity daoEntity = DaoEntity.prepareForList(ids, clazz);
        List<Map<String, Object>> listOfRow =  dao.load(daoEntity.getTableName(), daoEntity.getPkMap());
        List<E> entities = new ArrayList<>();
        listOfRow.forEach(row->{
            E entity = createEntityByRow(clazz, row);
            entities.add(entity);
        });
        return entities;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(E template) {
        if(template==null) throw new NullEntityException();
        if(isParent(template))
            throw new SubClassException("Not support this because of low efficient. Please use a child class or change to another subclass type!");
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(template);
        List<Map<String, Object>> listOfMap = dao.find(daoEntity.getTableName(), daoEntity.getColMap());
        List<E> entities = new ArrayList<>();
        listOfMap.forEach(row->{
            E entity = createEntityByRow((Class<E>)template.getClass(), row);
            entities.add(entity);
        });
        return entities;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(List<Map<Object, Object>> colMap, Class<E> clazz) {
        if(colMap==null||clazz==null) throw new SubClassException("The colMap or class is null");
        if(isParent(clazz))
            throw new SubClassException("Not support this because of low efficient. Please use a child class or change to another subclass type!");
        DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
        String tableName = dObj.getTable();
        List<Map<String, Object>> list = dao.find(tableName, colMap);
        List<E> entities = new ArrayList<>();
        list.forEach(row->{
            E entity = createEntityByRow(clazz, row);
            entities.add(entity);
        });
        return entities;
    }
}
