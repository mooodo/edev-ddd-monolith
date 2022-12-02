package com.edev.support.subclass;

import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.ddd.NullEntityException;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.entity.Entity;
import com.edev.support.subclass.exception.SubClassException;
import com.edev.support.utils.DowncastHelper;
import com.edev.support.utils.NameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@Component()
public class JoinedSubClass extends AbstractSubClass implements SubClassDao {
    @Autowired
    private DowncastHelper downcastHelper;
    @Override
    public boolean available(DomainObject dObj) {
        return dObj.getSubClassType().equalsIgnoreCase("joined");
    }

    /**
     * read the data from entity and decode from its parent, then build the daoEntity.
     * @param entity the entity
     * @return daoEntity
     */
    private DaoEntity readDataFromEntityForParent(Entity<?> entity) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(entity.getClass().getSuperclass());
        return DaoEntity.readDataFromEntity(entity, dObj);
    }

    /**
     * read the data from entity and decode from itself, then build the daoEntity.
     * @param entity the entity
     * @return daoEntity
     */
    private DaoEntity readDataFromEntityForChild(Entity<?> entity) {
        return DaoEntity.readDataFromEntity(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> S insert(E entity) {
        if(entity==null) throw new NullEntityException();
        if(isParent(entity)) entity = createSubClassByParent(entity);
        try {
            DaoEntity parent = readDataFromEntityForParent(entity);
            doInsert(parent);
            DaoEntity child = readDataFromEntityForChild(entity);
            doInsert(child);
        } catch (DataAccessException e) {
            throw new SubClassException("error when insert entity: [%s]", e, entity);
        }
        return entity.getId();
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void update(E entity) {
        if(entity==null) throw new NullEntityException();
        if(isParent(entity)) entity = createSubClassByParent(entity);
        E old = load(entity.getId(), (Class<E>)entity.getClass().getSuperclass());
        if(old==null)
            throw new SubClassException("No found the entity which need update: [%s]", entity);
        try {
            DaoEntity parent = readDataFromEntityForParent(entity);
            doUpdate(parent);
            if(old.getClass().equals(entity.getClass())) {
                DaoEntity child = readDataFromEntityForChild(entity);
                doUpdate(child);
            } else {
                //change to another subclass
                DaoEntity oldChild = readDataFromEntityForChild(old);
                doDelete(oldChild);
                DaoEntity newChild = readDataFromEntityForChild(entity);
                doInsert(newChild);
            }
        } catch (DataAccessException e) {
            throw new SubClassException("error when update entity: [%s]", e, entity);
        }
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(E entity) {
        if(entity==null) throw new NullEntityException();
        if(isParent(entity)) entity = createSubClassByParent(entity);
        try {
            DaoEntity parent = readDataFromEntityForParent(entity);
            doInsertOrUpdate(parent);
            DaoEntity child = readDataFromEntityForChild(entity);
            doInsertOrUpdate(child);
        } catch (DataAccessException e) {
            throw new SubClassException("error when insert or update entity: [%s]", e, entity);
        }
        return entity.getId();
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void delete(E entity) {
        if(entity==null) throw new NullEntityException();
        if(isParent(entity)) entity = createSubClassByParent(entity);
        try {
            DaoEntity parent = readDataFromEntityForParent(entity);
            doDelete(parent);
            DaoEntity child = readDataFromEntityForChild(entity);
            doDelete(child);
        } catch (DataAccessException e) {
            throw new SubClassException("error when delete entity: [%s]", e, entity);
        }
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>> void insertOrUpdateForList(C list) {
        if(list==null) throw new SubClassException("The list is null!");
        list.forEach(this::insertOrUpdate);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>> void deleteForList(C list) {
        if(list==null) throw new SubClassException("The list is null!");
        list.forEach(this::delete);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void delete(S id, Class<E> clazz) {
        E entity = load(id, clazz);
        if(entity==null) return;
        delete(entity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> E load(S id, Class<E> clazz) {
        if(id==null||clazz==null) throw new SubClassException("The id or class is null!");
        //load data from parent table firstly
        E entity = createEntityWithParentData(id, clazz);
        if(isParent(clazz)) entity = createEntityWithParentData(id, clazz);
        else entity = createEntityWithParentData(id, (Class<E>) clazz.getSuperclass());
        if (entity == null) return null;
        //load data from child table lastly
        fillEntityWithChildData(entity);
        return entity;
    }

    private <E extends Entity<S>, S extends Serializable> E createEntityWithParentData(S id, Class<E> parentClass) {
        E template = EntityBuilder.build(parentClass);
        template.setId(id);
        DaoEntity daoEntity = DaoEntity.readDataFromEntity(template);
        List<Map<String, Object>> list = dao.find(daoEntity.getTableName(), daoEntity.getPkMap());
        if(list==null||list.isEmpty()) return null;
        return createEntityByRow(parentClass, list.get(0));
    }

    private <E extends Entity<S>, S extends Serializable> void fillEntityWithChildData(E entity) {
        if(entity==null) throw new NullEntityException();
        DaoEntity daoEntity = readDataFromEntityForChild(entity);
        List<Map<String, Object>> list = dao.find(daoEntity.getTableName(), daoEntity.getPkMap());
        if(list==null||list.isEmpty()) return;
        Map<String, Object> child = list.get(0);
        child.forEach((column, value)->{
            if(value==null) return;
            String fieldName = NameUtils.convertToCamelCase(column);
            Type type = entity.getType(fieldName);
            if(type==null) return;
            entity.setValue(fieldName, downcastHelper.downcast(type, value));
        });
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void deleteForList(Collection<S> ids, Class<E> clazz) {
        if(ids==null||clazz==null) throw new SubClassException("The ids or class is null!");
        if(!isParent(clazz)) {
            throw new SubClassException("The class must be a parent class:[%s]",clazz);
        }
        DaoEntity daoEntity = DaoEntity.prepareForList(ids, clazz);
        //delete parent data
        dao.deleteForList(daoEntity.getTableName(), daoEntity.getPkMap());

        DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
        dObj.getSubClasses().forEach(subClass -> {
            DomainObject dObjOfChild = DomainObjectFactory.getDomainObject(subClass.getClazz());
            //delete child data
            dao.deleteForList(dObjOfChild.getTable(), daoEntity.getPkMap());
        });
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadForList(Collection<S> ids, Class<E> clazz) {
        if(ids==null||clazz==null) throw new SubClassException("The ids or class is null!");
        if(!isParent(clazz)) {
            throw new SubClassException("The class must be a parent class:[%s]",clazz);
        }
        DaoEntity daoEntity = DaoEntity.prepareForList(ids, clazz);
        List<Map<String, Object>> listOfRow = dao.load(daoEntity.getTableName(), daoEntity.getPkMap());
        if(listOfRow==null||listOfRow.isEmpty()) return new ArrayList<>();
        List<E> entities = new ArrayList<>();
        listOfRow.forEach(row->{
            E entity = createEntityByRow(clazz, row);
            fillEntityWithChildData(entity);
            entities.add(entity);
        });
        return entities;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(E template) {
        if(template==null) throw new NullEntityException();
        if(isParent(template)) {
            throw new SubClassException("The template must be a parent:[%s]",template);
        }
        try {
            DaoEntity parent = readDataFromEntityForParent(template);
            List<Map<String, Object>> listOfParents = dao.find(parent.getTableName(), parent.getColMap());
            List<E> entities = new ArrayList<>();
            listOfParents.forEach(row -> {
                E entity = createEntityByRow((Class<E>)template.getClass(), row);
                fillEntityWithChildData(entity);
                entities.add(entity);
            });
            return entities;
        } catch (DataAccessException e) {
            throw new SubClassException("error when load entities: [template:%s]", e, template);
        }
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
        List<E> entities = new ArrayList<>();
        list.forEach(row->{
            E entity = createEntityByRow(clazz, row);
            fillEntityWithChildData(entity);
            entities.add(entity);
        });
        return entities;
    }
}
