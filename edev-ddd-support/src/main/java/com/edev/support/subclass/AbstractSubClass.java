package com.edev.support.subclass;

import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.dao.impl.utils.DaoEntityBuilder;
import com.edev.support.dao.impl.utils.DaoExecutor;
import com.edev.support.ddd.DddFactory;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.dsl.Property;
import com.edev.support.dsl.SubClass;
import com.edev.support.entity.Entity;
import com.edev.support.subclass.utils.SubClassUtils;
import com.edev.support.utils.NameUtils;
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

public abstract class AbstractSubClass implements SubClassDao {
    @Autowired
    protected DaoExecutor daoExecutor;
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
        Property discriminator = SubClassUtils.getDiscriminator(dObj);
        Object value = json.get(discriminator.getName());
        SubClass subClass = SubClassUtils.getSubClassByValue(dObj, value);
        Class<E> subClazz = EntityUtils.getClassOfEntity(subClass.getClazz());
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
        if(SubClassUtils.isParent(clazz)) {
            DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
            Property discriminator = SubClassUtils.getDiscriminator(dObj);
            Object value = row.get(NameUtils.convertToUnderline(discriminator.getName()));
            SubClass subClass = SubClassUtils.getSubClassByValue(dObj, value);
            Class<E> subClazz = EntityUtils.getClassOfEntity(subClass.getClazz());
            return dddFactory.createSimpleEntityByRow(subClazz, row);
        } else
            return dddFactory.createSimpleEntityByRow(clazz, row);
    }

    protected <E extends Entity<S>, S extends Serializable>
            void updateForChild(E childEntity) {
        E oldEntity = load(childEntity.getId(), EntityUtils.getSuperclass(childEntity));
        if(oldEntity == null) return;
        if(oldEntity.getClass().equals(childEntity.getClass())) {
            //just update itself only
            DaoEntity child = DaoEntityBuilder.build(childEntity);
            daoExecutor.update(child);
        } else {
            //change this type of subclass to another type of subclass
            DaoEntity old = DaoEntityBuilder.build(oldEntity);
            daoExecutor.delete(old);
            DaoEntity child = DaoEntityBuilder.build(childEntity);
            daoExecutor.insert(child, childEntity);
        }
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(@NotNull E entity) {
        E child = SubClassUtils.isParent(entity) ? SubClassUtils.createSubClassByParent(entity) : entity;
        try {
            insert(child);
        } catch (DataAccessException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException)
                update(child);
            else throw e;
        }
        return child.getId();
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>>
        void insertOrUpdateForList(@NotNull C list) {
        list.forEach(this::insertOrUpdate);
    }

    protected <E extends Entity<S>, S extends Serializable> void deleteChildTable(@NotNull E entity) {
        if(SubClassUtils.isParent(entity)) {
            Object value = SubClassUtils.getValueOfDiscriminator(entity);
            if(value != null) { //delete the child table which has the discriminator
                E childEntity = SubClassUtils.createSubClassByParent(entity);
                deleteForChild(childEntity);
            } else { //delete each of child tables which has no discriminator
                deleteEachChildTables(entity);
            }
        } else deleteForChild(entity);
    }

    private <E extends Entity<S>, S extends Serializable> void deleteEachChildTables(@NotNull E entity) {
        SubClassUtils.doForEachSubClass(EntityUtils.getClass(entity), subClass->{
            E child = EntityBuilder.build(subClass);
            child.merge(entity);
            deleteForChild(child);
        });
    }

    private <E extends Entity<S>, S extends Serializable> void deleteForChild(@NotNull E childEntity) {
        DaoEntity child = DaoEntityBuilder.build(childEntity);
        daoExecutor.delete(child);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void delete(@NotNull S id, @NotNull Class<E> clazz) {
        E template = EntityBuilder.build(clazz);
        template.setId(id);
        delete(template);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>>
            void deleteForList(@NotNull C list) {
        list.forEach(this::delete);
    }

    protected  <E extends Entity<S>, S extends Serializable>
            void deleteChildTableForList(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        if(SubClassUtils.isParent(clazz))
            SubClassUtils.doForEachSubClass(clazz, (subClass -> deleteForListChild(ids, subClass)));
        else deleteForListChild(ids, clazz);
    }

    private <E extends Entity<S>, S extends Serializable>
            void deleteForListChild(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        DaoEntity daoEntity = DaoEntityBuilder.buildForList(ids, clazz);
        daoExecutor.deleteForList(daoEntity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> E load(@NotNull S id, @NotNull Class<E> clazz) {
        E template = EntityBuilder.build(clazz);
        template.setId(id);
        SubClassUtils.setDiscriminator(template);
        Collection<E> entities = loadAll(template);
        if(entities==null||entities.isEmpty()) return null;
        else return entities.iterator().next();
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadForList(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        if(ids.isEmpty()) return new ArrayList<>();
        DaoEntity daoEntity = DaoEntityBuilder.buildForList(ids, clazz);
        return daoExecutor.load(daoEntity, clazz);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(@NotNull E template) {
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
