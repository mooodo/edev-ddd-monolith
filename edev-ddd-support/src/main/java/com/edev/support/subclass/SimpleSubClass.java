package com.edev.support.subclass;

import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.dao.impl.utils.DaoEntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.DomainObject;
import com.edev.support.entity.Entity;
import com.edev.support.subclass.utils.DaoEntityForSubClassUtils;
import com.edev.support.subclass.utils.SubClassUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component()
public class SimpleSubClass extends AbstractSubClass implements SubClassDao {
    @Override
    public boolean available(@NotNull DomainObject dObj) {
        return dObj.getSubClassType().equalsIgnoreCase("simple");
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insert(@NotNull E entity) {
        E child = SubClassUtils.isParent(entity) ? SubClassUtils.createSubClassByParent(entity) : entity;
        DaoEntity daoEntity = DaoEntityForSubClassUtils.buildWithEntityAndItsSubClass(child);
        return daoExecutor.insert(daoEntity, entity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void update(@NotNull E entity) {
        E child = SubClassUtils.isParent(entity) ? SubClassUtils.createSubClassByParent(entity) : entity;
        DaoEntity daoEntity = DaoEntityForSubClassUtils.buildWithEntityAndItsSubClass(child);
        daoExecutor.update(daoEntity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void delete(@NotNull E entity) {
        DaoEntity daoEntity = DaoEntityBuilder.build(entity);
        daoExecutor.delete(daoEntity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            void deleteForList(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        Class<E> parentClass = SubClassUtils.isParent(clazz) ? clazz : EntityUtils.getSuperclass(clazz);
        DaoEntity daoEntity = DaoEntityBuilder.buildForList(ids, parentClass);
        daoExecutor.deleteForList(daoEntity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadForList(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        Class<E> parentClass = SubClassUtils.isParent(clazz) ? clazz : EntityUtils.getSuperclass(clazz);
        return super.loadForList(ids, parentClass);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
        Collection<E> loadAll(@NotNull List<Map<Object, Object>> colMap, @NotNull Class<E> clazz) {
        Class<E> parentClass = SubClassUtils.isParent(clazz) ? clazz : EntityUtils.getSuperclass(clazz);
        return super.loadAll(colMap, parentClass);
    }
}
