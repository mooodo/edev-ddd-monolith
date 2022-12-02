package com.edev.support.subclass;

import com.edev.support.dao.impl.mybatis.GenericDao;
import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.ddd.DddException;
import com.edev.support.ddd.DddFactory;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.dsl.Property;
import com.edev.support.dsl.SubClass;
import com.edev.support.entity.Entity;
import com.edev.support.subclass.exception.NoDiscriminatorException;
import com.edev.support.subclass.exception.SubClassNotExistsException;
import com.edev.support.utils.BeanUtils;
import com.edev.support.utils.NameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

public abstract class AbstractSubClass implements SubClassDao {
    @Autowired
    protected GenericDao dao;
    @Autowired
    protected DddFactory dddFactory;

    /**
     * create an entity by the json
     * @param clazz the parent class
     * @param json the json come from frontend
     * @param <E> the entity
     * @param <S> the id
     * @return the created entity
     */
    @Override
    public <E extends Entity<S>, S extends Serializable> E createEntityByJson(Class<E> clazz, Map<String, Object> json) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
        Property discriminator = getDiscriminator(dObj);
        Object value = json.get(discriminator.getName());
        SubClass subClass = getSubClassByValue(dObj, value);
        Class<E> subClazz = getClazz(subClass.getClazz());
        return dddFactory.createSimpleEntityByJson(subClazz, json);
    }

    /**
     * create an entity by the row
     * @param clazz the parent class
     * @param row the row come from backend query
     * @param <E> the entity
     * @param <S> the id
     * @return the created entity
     */
    @Override
    public <E extends Entity<S>, S extends Serializable> E createEntityByRow(Class<E> clazz, Map<String, Object> row) {
        if(isParent(clazz)) {
            DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
            Property discriminator = getDiscriminator(dObj);
            Object value = row.get(NameUtils.convertToUnderline(discriminator.getName()));
            SubClass subClass = getSubClassByValue(dObj, value);
            Class<E> subClazz = getClazz(subClass.getClazz());
            return dddFactory.createSimpleEntityByRow(subClazz, row);
        } else
            return dddFactory.createSimpleEntityByRow(clazz, row);
    }

    /**
     * get dsl of the subclass
     * @param dObj the dsl of the parent class
     * @param subClazz the class of the subclass
     * @return the dsl of the subclass
     */
    protected SubClass getSubClass(DomainObject dObj, Class<?> subClazz) {
        List<SubClass> subClasses = dObj.getSubClasses();
        for(SubClass subClass : subClasses)
            if(subClass.getClazz().equals(subClazz.getName()))
                return subClass;
        throw new SubClassNotExistsException(subClazz);
    }

    /**
     * get dsl of the subclass by the value of the discriminator
     * @param dObj the dsl of the parent class
     * @param value the value of the discriminator
     * @return the dsl of the subclass
     */
    protected SubClass getSubClassByValue(DomainObject dObj, Object value) {
        List<SubClass> subClasses = dObj.getSubClasses();
        for(SubClass subClass : subClasses)
            if(subClass.getValue().equals(value))
                return subClass;
        throw new SubClassNotExistsException(dObj.getClazz(), value);
    }

    protected <E extends Entity<S>, S extends Serializable> E createSubClassByParent(E parent) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(parent.getClass());
        Property discriminator = getDiscriminator(dObj);
        Object value = parent.getValue(discriminator.getName());
        SubClass subClass = getSubClassByValue(dObj, value);
        Class<E> classOfChild = getClazz(subClass.getClazz());
        E child =  EntityBuilder.build(classOfChild);
        child.merge(parent);
        return child;
    }

    /**
     * get the discriminator of the subclass
     * @param dObj the dsl of the parent class
     * @return the discriminator
     */
    protected Property getDiscriminator(DomainObject dObj) {
        for(Property property : dObj.getProperties())
            if(property.isDiscriminator()) return property;
        throw new NoDiscriminatorException(dObj.getClazz());
    }

    private<E extends Entity<S>, S extends Serializable> Class<E> getClazz(String className) {
        Class<?> clazz = BeanUtils.getClazz(className);
        if(!Entity.class.isAssignableFrom(clazz))
            throw new DddException("The class is not an entity: ["+className+"]");
        return (Class<E>) clazz;
    }

    /**
     * whether the entity is a parent class
     * @param entity the entity
     * @param <E> the entity
     * @param <S> the id
     * @return true if the entity is a parent class
     */
    protected <E extends Entity<S>, S extends Serializable> boolean isParent(E entity) {
        return isParent((Class<E>)entity.getClass());
    }

    /**
     * whether the entity is a parent class
     * @param clazz the class of the entity
     * @param <E> the entity
     * @param <S> the id
     * @return true if the entity is a parent class
     */
    protected <E extends Entity<S>, S extends Serializable> boolean isParent(Class<E> clazz) {
        return (clazz.getSuperclass().equals(Entity.class));
    }

    protected void doInsert(DaoEntity daoEntity) throws DataAccessException {
        dao.insert(daoEntity.getTableName(), daoEntity.getColMap());
    }

    protected void doUpdate(DaoEntity daoEntity) throws DataAccessException {
        dao.update(daoEntity.getTableName(), daoEntity.getColMap(), daoEntity.getPkMap());
    }

    protected void doInsertOrUpdate(DaoEntity daoEntity) throws DataAccessException {
        try {
            doInsert(daoEntity);
        } catch (DataAccessException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException)
                doUpdate(daoEntity);
            else throw e;
        }
    }

    protected void doDelete(DaoEntity daoEntity) throws DataAccessException {
        dao.delete(daoEntity.getTableName(), daoEntity.getPkMap());
    }
}
