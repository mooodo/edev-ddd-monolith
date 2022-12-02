package com.edev.support.ddd.join;

import com.edev.support.dao.BasicDao;
import com.edev.support.ddd.DddException;
import com.edev.support.ddd.NullEntityException;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.dsl.Join;
import com.edev.support.entity.Entity;

import java.io.Serializable;
import java.util.Collection;

public class JoinHelper<E extends Entity<S>, S extends Serializable> {
    private final BasicDao dao;

    public JoinHelper(BasicDao dao) {
        this.dao = dao;
    }

    private void doWithJoins(E entity, Callback callback) {
        if(entity==null) throw new NullEntityException();
        DomainObject dObj = DomainObjectFactory.getDomainObject(entity.getClass());
        dObj.getJoins().forEach(callback::apply);
    }
    @FunctionalInterface
    public interface Callback {
        void apply(Join join);
    }

    private Assembler<E,S> getAssembler(Join join) {
        String joinType = join.getJoinType();
        switch (joinType) {
            case "oneToOne":
                return new OneToOneForJoin<>(join, dao);
            case "manyToOne":
                return new ManyToOneForJoin<>(join, dao);
            case "oneToMany":
                return new OneToManyForJoin<>(join, dao);
            case "manyToMany":
                throw new DddException("Don't support the many to many relation now! " +
                        "You can transform the many-to-many relation to two many-to-one relations.");
            default:
                throw new DddException("Unknown the join type: %s", joinType);
        }
    }

    /**
     * insert values for each join of the entity, if it has.
     * @param entity the entity
     */
    public void insertJoins(E entity) {
        doWithJoins(entity, join -> {
            if(!join.isAggregation()) return;
            Assembler<E,S> assembler = getAssembler(join);
            assembler.insertValue(entity);
        });
    }

    /**
     * update values for each join of the entity, if it has.
     * @param entity the entity
     */
    public void updateJoins(E entity) {
        doWithJoins(entity, join -> {
            if(!join.isAggregation()) return;
            Assembler<E,S> assembler = getAssembler(join);
            assembler.updateValue(entity);
        });
    }

    /**
     * delete values for each join of the entity, if it has.
     * @param entity the entity
     */
    public void deleteJoins(E entity) {
        doWithJoins(entity, join -> {
            if(!join.isAggregation()) return;
            Assembler<E,S> assembler = getAssembler(join);
            assembler.deleteValue(entity);
        });
    }

    /**
     * set values for each join of the entity, if it has.
     * @param entity the entity
     */
    public void setJoins(E entity) {
        doWithJoins(entity, join -> {
            Assembler<E,S> assembler = getAssembler(join);
            assembler.setValue(entity);
        });
    }

    /**
     * whether the entity has a join which is aggregation.
     * @param clazz the class of entity
     * @return true, if the entity has a join which is aggregation
     */
    public boolean hasJoinAndAggregation(Class<E> clazz) {
        if(clazz==null) throw new NullEntityException();
        DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
        for(Join join : dObj.getJoins()) if(join.isAggregation()) return true;
        return false;
    }

    /**
     * whether the entity has a join which is aggregation.
     * @param entity the entity
     * @return true, if the entity has a join which is aggregation
     */
    public boolean hasJoinAndAggregation(E entity) {
        if(entity==null) throw new NullEntityException();
        return hasJoinAndAggregation((Class<E>)entity.getClass());
    }

    private void doWithJoinsForList(Collection<E> list, Callback callback) {
        if(list==null||list.isEmpty()) return;
        E entity = list.iterator().next();
        DomainObject dObj = DomainObjectFactory.getDomainObject(entity.getClass());
        dObj.getJoins().forEach(callback::apply);
    }

    /**
     * set values for each join of every entity in a list.
     * @param list the list of entities
     */
    public void setJoinForList(Collection<E> list) {
        if(list==null||list.isEmpty()) return;
        doWithJoinsForList(list, join -> {
            Assembler<E,S> assembler = getAssembler(join);
            assembler.setValueForList(list);
        });
    }
}
