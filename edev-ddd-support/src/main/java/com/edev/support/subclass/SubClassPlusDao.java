package com.edev.support.subclass;

import com.edev.support.dao.BasicDao;
import com.edev.support.dao.impl.DecoratorDao;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SubClassPlusDao extends DecoratorDao implements BasicDao {
    @Autowired
    private SubClassFactory subClassFactory;

    public SubClassPlusDao() {
        super();
    }
    public SubClassPlusDao(BasicDao dao) {
        super(dao);
    }
    public <S extends Serializable> boolean hasSubClass(@NotNull Entity<S> entity) {
        return hasSubClass(entity.getClass());
    }

    public boolean hasSubClass(Class<?> clazz) {
        return EntityUtils.hasSubClass(clazz)||EntityUtils.hasSubClass(clazz.getSuperclass());
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insert(E entity) {
        if(!hasSubClass(entity)) return super.insert(entity);
        return subClassFactory.chooseSubClass(entity.getClass()).insert(entity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void update(E entity) {
        if(!hasSubClass(entity)) super.update(entity);
        else subClassFactory.chooseSubClass(entity.getClass()).update(entity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(E entity) {
        if(!hasSubClass(entity)) return super.insertOrUpdate(entity);
        return subClassFactory.chooseSubClass(entity.getClass()).insertOrUpdate(entity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>> void insertOrUpdateForList(C list) {
        if(list==null||list.isEmpty()) return;
        E entity = list.iterator().next();
        if(!hasSubClass(entity)) super.insertOrUpdateForList(list);
        else subClassFactory.chooseSubClass(entity.getClass()).insertOrUpdateForList(list);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void delete(E entity) {
        if(!hasSubClass(entity)) super.delete(entity);
        else subClassFactory.chooseSubClass(entity.getClass()).delete(entity);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable, C extends Collection<E>> void deleteForList(C list) {
        if(list==null||list.isEmpty()) return;
        E entity = list.iterator().next();
        if(!hasSubClass(entity)) super.deleteForList(list);
        else subClassFactory.chooseSubClass(entity.getClass()).deleteForList(list);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void deleteForList(Collection<S> ids, Class<E> clazz) {
        if(!hasSubClass(clazz)) super.deleteForList(ids, clazz);
        else subClassFactory.chooseSubClass(clazz).deleteForList(ids, clazz);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> E load(S id, Class<E> clazz) {
        if(!hasSubClass(clazz)) return super.load(id, clazz);
        return subClassFactory.chooseSubClass(clazz).load(id, clazz);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadForList(Collection<S> ids, Class<E> clazz) {
        if(!hasSubClass(clazz)) return super.loadForList(ids, clazz);
        return subClassFactory.chooseSubClass(clazz).loadForList(ids, clazz);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(E template) {
        if(!hasSubClass(template)) return super.loadAll(template);
        return subClassFactory.chooseSubClass(template.getClass()).loadAll(template);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(List<Map<Object, Object>> colMap, Class<E> clazz) {
        if(!hasSubClass(clazz)) return super.loadAll(colMap, clazz);
        return subClassFactory.chooseSubClass(clazz).loadAll(colMap, clazz);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable> void delete(S id, Class<E> clazz) {
        if(!hasSubClass(clazz)) super.delete(id, clazz);
        else subClassFactory.chooseSubClass(clazz).delete(id, clazz);
    }
}
