package com.edev.support.subclass;

import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.ddd.NullEntityException;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.dsl.Property;
import com.edev.support.dsl.SubClass;
import com.edev.support.entity.Entity;
import com.edev.support.subclass.exception.SubClassException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component()
public class SimpleSubClass extends AbstractSubClass implements SubClassDao {
    @Override
    public boolean available(DomainObject dObj) {
        return dObj.getSubClassType().equalsIgnoreCase("simple");
    }
    /**
     * read and decode the data from entity and its subclass, then build the daoEntity
     * @param entity the entity
     * @return daoEntity
     */
    private DaoEntity readDataFromEntity(Entity<?> entity) {
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(entity);
        //set the columns of subclass to daoEntity
        DomainObject dObj = DomainObjectFactory.getDomainObject(entity.getClass());
        SubClass subClass = getSubClass(dObj, entity.getClass());
        List<Property> properties = subClass.getProperties();
        DaoEntity.setProperties(daoEntity, entity, properties);
        return daoEntity;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insert(E entity) {
        if(entity==null) throw new NullEntityException();
        DaoEntity daoEntity = readDataFromEntity(entity);
        try {
            doInsert(daoEntity);
            return entity.getId();
        } catch (DataAccessException e) {
            throw new SubClassException("error when insert entity: [%s]", e, entity);
        }
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void update(E entity) {
        if(entity==null) throw new NullEntityException();
        DaoEntity daoEntity = readDataFromEntity(entity);
        try {
            doUpdate(daoEntity);
        } catch (DataAccessException e) {
            throw new SubClassException("error when update entity[%s]", e, entity);
        }
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(E entity) {
        if(entity==null) throw new NullEntityException();
        DaoEntity daoEntity = readDataFromEntity(entity);
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
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(entity);
        try {
            doDelete(daoEntity);
        } catch (DataAccessException e) {
            throw new SubClassException("error when delete entity[%s]", e, entity);
        }
    }

    @Override
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>>
            void insertOrUpdateForList(C list) {
        if(list==null) throw new SubClassException("The list is null!");
        list.forEach(this::insertOrUpdate);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>>
            void deleteForList(C list) {
        if(list==null) throw new SubClassException("The list is null!");
        list.forEach(this::delete);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void delete(S id, Class<E> clazz) {
        if(id==null||clazz==null) throw new SubClassException("The id or class is null!");
        E template = EntityBuilder.build(clazz);
        template.setId(id);
        delete(template);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> E load(S id, Class<E> clazz) {
        if(id==null||clazz==null) throw new SubClassException("The id or class is null!");
        E template = EntityBuilder.build(clazz);
        template.setId(id);
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(template);
        List<Map<String, Object>> list = dao.find(daoEntity.getTableName(), daoEntity.getPkMap());
        if(list==null||list.isEmpty()) return null;
        return createEntityByRow(clazz, list.get(0));
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void deleteForList(Collection<S> ids, Class<E> clazz) {
        if(ids==null||clazz==null) throw new SubClassException("The ids or class is null!");
        DaoEntity daoEntity = DaoEntity.prepareForList(ids, clazz);
        dao.deleteForList(daoEntity.getTableName(), daoEntity.getPkMap());
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadForList(Collection<S> ids, Class<E> clazz) {
        if(ids==null||clazz==null) throw new SubClassException("The ids or class is null!");
        DaoEntity daoEntity = DaoEntity.prepareForList(ids, clazz);
        List<Map<String, Object>> listOfMap = dao.load(daoEntity.getTableName(), daoEntity.getPkMap());
        return dddFactory.createEntityByRowForList(clazz, listOfMap);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(E template) {
        if(template==null) throw new SubClassException("The template is null!");
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(template);
        List<Map<String, Object>> list = dao.find(daoEntity.getTableName(), daoEntity.getColMap());
        return dddFactory.createEntityByRowForList((Class<E>) template.getClass(), list);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(List<Map<Object, Object>> colMap, Class<E> clazz) {
        if(colMap==null||clazz==null) throw new SubClassException("The colMap or class is null");
        if(!isParent(clazz)) {
            throw new SubClassException("The class must be a parent class:[%s]",clazz);
        }
        DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
        String tableName = dObj.getTable();
        List<Map<String, Object>> list = dao.find(tableName, colMap);
        return dddFactory.createEntityByRowForList(clazz, list);
    }
}
