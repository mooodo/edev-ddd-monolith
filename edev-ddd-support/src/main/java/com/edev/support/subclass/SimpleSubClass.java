package com.edev.support.subclass;

import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.dao.impl.utils.DaoEntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.DomainObject;
import com.edev.support.entity.Entity;
import com.edev.support.subclass.utils.DaoEntityForSubClassUtils;
import com.edev.support.subclass.utils.SubClassUtils;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component()
public class SimpleSubClass extends AbstractSubClass implements SubClassDao {
    @Override
    public boolean available(@NonNull DomainObject dObj) {
        return dObj.getSubClassType().equalsIgnoreCase("simple");
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insert(@NonNull E entity) {
        E child = SubClassUtils.isParent(entity) ? SubClassUtils.createSubClassByParent(entity) : entity;
        DaoEntity daoEntity = DaoEntityForSubClassUtils.buildWithEntityAndItsSubClass(child);
        return daoExecutor.insert(daoEntity, entity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void update(@NonNull E entity) {
        E child = SubClassUtils.isParent(entity) ? SubClassUtils.createSubClassByParent(entity) : entity;
        DaoEntity daoEntity = DaoEntityForSubClassUtils.buildWithEntityAndItsSubClass(child);
        daoExecutor.update(daoEntity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void delete(@NonNull E template) {
        DaoEntity daoEntity = DaoEntityBuilder.build(template);
        daoExecutor.delete(daoEntity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            void deleteForList(@NonNull Collection<S> ids, @NonNull Class<E> clazz) {
        Class<E> parentClass = SubClassUtils.isParent(clazz) ? clazz : EntityUtils.getSuperclass(clazz);
        DaoEntity daoEntity = DaoEntityBuilder.buildForList(ids, parentClass);
        daoExecutor.deleteForList(daoEntity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadForList(@NonNull Collection<S> ids, @NonNull Class<E> clazz) {
        Class<E> parentClass = SubClassUtils.isParent(clazz) ? clazz : EntityUtils.getSuperclass(clazz);
        return super.loadForList(ids, parentClass);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
        Collection<E> loadAll(@NonNull List<Map<Object, Object>> colMap, @NonNull Class<E> clazz) {
        Class<E> parentClass = SubClassUtils.isParent(clazz) ? clazz : EntityUtils.getSuperclass(clazz);
        return super.loadAll(colMap, parentClass);
    }
}
