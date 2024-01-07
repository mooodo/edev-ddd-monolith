package com.edev.support.ddd.join;

import com.edev.support.dao.BasicDao;
import com.edev.support.ddd.DddException;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.dsl.Join;
import com.edev.support.entity.Entity;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Collection;

public class JoinHelper<E extends Entity<S>, S extends Serializable> {
    private final BasicDao dao;

    public JoinHelper(BasicDao dao) {
        this.dao = dao;
    }

    private void doWithJoins(@NonNull E entity, @NonNull Callback callback) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(entity.getClass());
        dObj.getJoins().forEach(callback::apply);
    }
    @FunctionalInterface
    public interface Callback {
        void apply(Join join);
    }

    private Relation<E,S> getRelation(@NonNull Join join) {
        String joinType = join.getJoinType();
        switch (joinType) {
            case "oneToOne":
                return new OneToOneForJoin<>(join, dao);
            case "manyToOne":
                return new ManyToOneForJoin<>(join, dao);
            case "oneToMany":
                return new OneToManyForJoin<>(join, dao);
            case "manyToMany":
                return new ManyToManyForJoin<>(join, dao);
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
            Relation<E,S> relation = getRelation(join);
            relation.insertValue(entity);
        });
    }

    /**
     * update values for each join of the entity, if it has.
     * @param entity the entity
     */
    public void updateJoins(E entity) {
        doWithJoins(entity, join -> {
            Relation<E,S> relation = getRelation(join);
            relation.updateValue(entity);
        });
    }

    /**
     * delete values for each join of the entity, if it has.
     * @param entity the entity
     */
    public void deleteJoins(E entity) {
        doWithJoins(entity, join -> {
            Relation<E,S> relation = getRelation(join);
            relation.deleteValue(entity);
        });
    }

    /**
     * set values for each join of the entity, if it has.
     * @param entity the entity
     */
    public void setJoins(E entity) {
        doWithJoins(entity, join -> {
            Relation<E,S> relation = getRelation(join);
            relation.setValue(entity);
        });
    }

    /**
     * whether the entity has a join which is aggregation.
     * @param clazz the class of entity
     * @return true, if the entity has a join which is aggregation
     */
    public boolean hasJoinAndAggregation(@NonNull Class<E> clazz) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
        for(Join join : dObj.getJoins())
            if(join.isAggregation()||join.getJoinType().equals("manyToMany")) return true;
        return false;
    }

    /**
     * whether the entity has a join which is aggregation.
     * @param entity the entity
     * @return true, if the entity has a join which is aggregation
     */
    public boolean hasJoinAndAggregation(@NonNull E entity) {
        return hasJoinAndAggregation(EntityUtils.getClass(entity));
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
            Relation<E,S> relation = getRelation(join);
            relation.setValueForList(list);
        });
    }
}
