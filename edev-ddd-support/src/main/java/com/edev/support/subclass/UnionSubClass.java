package com.edev.support.subclass;

import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.dao.impl.utils.DaoEntityBuilder;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.DomainObject;
import com.edev.support.entity.Entity;
import com.edev.support.subclass.utils.SubClassUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component()
public class UnionSubClass extends AbstractSubClass implements SubClassDao {
    @Override
    public boolean available(@NotNull DomainObject dObj) {
        return dObj.getSubClassType().equalsIgnoreCase("union");
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insert(@NotNull E entity) {
        E childEntity = SubClassUtils.isParent(entity) ? SubClassUtils.createSubClassByParent(entity) : entity;
        DaoEntity daoEntity = DaoEntityBuilder.build(childEntity);
        return daoExecutor.insert(daoEntity, entity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void update(@NotNull E entity) {
        E childEntity = SubClassUtils.isParent(entity) ? SubClassUtils.createSubClassByParent(entity) : entity;
        updateForChild(childEntity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(@NotNull E entity) {
        E child = SubClassUtils.isParent(entity) ? SubClassUtils.createSubClassByParent(entity) : entity;
        E old = child.getId()==null ? null : load(child.getId(), EntityUtils.getSuperclass(child));
        if(old == null) insert(child);
        else update(child);
        return child.getId();
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void delete(@NotNull E entity) {
        deleteChildTable(entity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            void deleteForList(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        deleteChildTableForList(ids, clazz);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadForList(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        if(SubClassUtils.isParent(clazz)) {
            Collection<E> collection = new ArrayList<>();
            SubClassUtils.doForEachSubClass(clazz, subClass -> collection.addAll(super.loadForList(ids, subClass)));
            return collection;
        }
        return super.loadForList(ids, clazz);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadAll(@NotNull E template) {
        if(SubClassUtils.isParent(template)) {
            Object value = SubClassUtils.getValueOfDiscriminator(template);
            if(value!=null) {
                E child = SubClassUtils.createSubClassByParent(template);
                return super.loadAll(child);
            } else {
                Collection<E> entities = new ArrayList<>();
                SubClassUtils.doForEachSubClass(EntityUtils.getClass(template),subClass -> {
                    E child = EntityBuilder.build(subClass);
                    child.merge(template);
                    entities.addAll(super.loadAll(child));
                });
                return entities;
            }
        }
        return super.loadAll(template);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadAll(@NotNull List<Map<Object, Object>> colMap, @NotNull Class<E> clazz) {
        if(SubClassUtils.isParent(clazz)) {
            Collection<E> entities = new ArrayList<>();
            SubClassUtils.doForEachSubClass(clazz,subClass ->
                    entities.addAll(super.loadAll(colMap, subClass)));
            return entities;
        }
        else return super.loadAll(colMap, clazz);
    }
}
