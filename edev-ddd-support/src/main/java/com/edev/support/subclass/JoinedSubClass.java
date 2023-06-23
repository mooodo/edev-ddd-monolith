package com.edev.support.subclass;

import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.dao.impl.utils.DaoEntityBuilder;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.dsl.SubClass;
import com.edev.support.entity.Entity;
import com.edev.support.subclass.utils.DaoEntityForSubClassUtils;
import com.edev.support.subclass.utils.SubClassUtils;
import com.edev.support.utils.DowncastHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Component
public class JoinedSubClass extends AbstractSubClass implements SubClassDao {
    @Autowired
    private DowncastHelper downcastHelper;
    @Override
    public boolean available(@NotNull DomainObject dObj) {
        return dObj.getSubClassType().equalsIgnoreCase("joined");
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> S insert(@NotNull E entity) {
        E childEntity = SubClassUtils.isParent(entity) ? SubClassUtils.createSubClassByParent(entity) : entity;
        DaoEntity parent = DaoEntityForSubClassUtils.buildForParent(childEntity);
        daoExecutor.insert(parent, childEntity);
        DaoEntity child = DaoEntityBuilder.build(childEntity);
        daoExecutor.insert(child, childEntity);
        return entity.getId();
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void update(@NotNull E entity) {
        E childEntity = SubClassUtils.isParent(entity) ? SubClassUtils.createSubClassByParent(entity) : entity;
        //update child table firstly
        updateForChild(childEntity);
        //update parent table lastly
        DaoEntity parent = DaoEntityForSubClassUtils.buildForParent(childEntity);
        daoExecutor.update(parent);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable> void delete(@NotNull E entity) {
        //delete the parent table
        DaoEntity parent = SubClassUtils.isParent(entity) ? DaoEntityBuilder.build(entity) :
                DaoEntityForSubClassUtils.buildForParent(entity);
        daoExecutor.delete(parent);
        //delete the child tables
        deleteChildTable(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable>
            void delete(@NotNull S id, @NotNull Class<E> clazz) {
        E entity = EntityBuilder.build(clazz);
        entity.setId(id);
        delete(entity);
    }

    @Override
    @Transactional
    public <E extends Entity<S>, S extends Serializable>
            void deleteForList(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        DaoEntity daoEntity = DaoEntityBuilder.buildForList(ids,
                SubClassUtils.isParent(clazz) ? clazz : EntityUtils.getSuperclass(clazz)
                );
        //delete parent table
        daoExecutor.deleteForList(daoEntity);
        deleteChildTableForList(ids, clazz);
    }

    private <E extends Entity<S>, S extends Serializable>
            Set<Object> distinctDiscriminators(@NotNull Collection<E> parents) {
        Map<Object, Object> discriminatorMap = new HashMap<>();
        //distinct discriminators by keys of the map
        parents.forEach(entity->discriminatorMap.put(SubClassUtils.getValueOfDiscriminator(entity),null));
        return discriminatorMap.keySet();
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadForList(@NotNull Collection<S> ids, @NotNull Class<E> clazz) {
        //load for parent table
        Collection<E> parents = super.loadForList(ids,
                SubClassUtils.isParent(clazz) ? clazz : EntityUtils.getSuperclass(clazz));
        if(parents==null||parents.isEmpty()) return new ArrayList<>();

        //load for child tables
        List<E> entities = new ArrayList<>();
        if(SubClassUtils.isParent(clazz))
            distinctDiscriminators(parents).forEach(discriminator->{
                DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
                SubClass subClass = SubClassUtils.getSubClassByValue(dObj, discriminator);
                Class<E> subClazz = EntityUtils.getClassOfEntity(subClass.getClazz());
                Collection<E> children = super.loadForList(ids, subClazz);
                entities.addAll(assembleForList(parents, children));
            });
        else {
            Collection<E> children = super.loadForList(ids, clazz);
            entities.addAll(assembleForList(parents, children));
        }
        return entities;
    }

    private <E extends Entity<S>, S extends Serializable>
            E assemble(E parent, E child) {
        child.merge(parent);
        return child;
    }

    private <E extends Entity<S>, S extends Serializable>
            Collection<E> assembleForList(@NotNull Collection<E> parents, @NotNull Collection<E> children) {
        Map<S,E> parentMap = new HashMap<>();
        parents.forEach(parent->parentMap.put(parent.getId(), parent));
        Map<S,E> childMap = new HashMap<>();
        children.forEach(child->childMap.put(child.getId(), child));
        Collection<E> entities = new ArrayList<>();
        parentMap.forEach((id,parent) -> {
            E child = childMap.get(id);
            if(child==null) return;
            entities.add(assemble(parent, child));
        });
        return entities;
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadAll(@NotNull E template) {
        if(SubClassUtils.isParent(template)) {
            Object value = SubClassUtils.getValueOfDiscriminator(template);
            if(value!=null) {//the template is a parent with discriminator
                E childTemplate = SubClassUtils.createSubClassByParent(template);
                return loadAllForChild(childTemplate);
            } else {//the template is a parent without discriminator
                return loadAllForParent(template);
            }
        }
        //the template is a child
        return loadAllForChild(template);
    }

    private <E extends Entity<S>, S extends Serializable>
            Collection<E> loadAllForParent(@NotNull E template) {
        //load data from parent table.
        Collection<E> parents = super.loadAll(template);
        //load data from child tables.
        List<E> entities = new ArrayList<>();
        distinctDiscriminators(parents).forEach(discriminator->{
            DomainObject dObj = DomainObjectFactory.getDomainObject(template.getClass());
            SubClass subClass = SubClassUtils.getSubClassByValue(dObj, discriminator);
            E childTemplate = (new EntityBuilder<E,S>(subClass.getClazz())).createEntity();
            childTemplate.merge(template);
            Collection<E> children = super.loadAll(childTemplate);
            if(children==null||children.isEmpty()) return;
            entities.addAll(assembleForList(parents, children));
        });
        return entities;
    }

    private <E extends Entity<S>, S extends Serializable>
            Collection<E> loadAllForChild(@NotNull E template) {
        return super.loadAll(template);
    }

    @Override
    public <E extends Entity<S>, S extends Serializable>
            Collection<E> loadAll(@NotNull List<Map<Object, Object>> colMap, @NotNull Class<E> clazz) {
        if(SubClassUtils.isParent(clazz)) {
            Collection<E> parents = super.loadAll(colMap, clazz);
            if(parents==null||parents.isEmpty()) return new ArrayList<>();
            Collection<E> entities = new ArrayList<>();
            distinctDiscriminators(parents).forEach(discriminator->{
                DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
                SubClass subClass = SubClassUtils.getSubClassByValue(dObj, discriminator);
                Class<E> subClazz = EntityUtils.getClassOfEntity(subClass.getClazz());
                Collection<E> children = super.loadAll(colMap, subClazz);
                entities.addAll(assembleForList(parents, children));
            });
            return entities;
        } else {
            return super.loadAll(colMap, clazz);
        }
    }
}
