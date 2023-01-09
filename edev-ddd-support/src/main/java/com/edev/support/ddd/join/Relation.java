package com.edev.support.ddd.join;

import com.edev.support.entity.Entity;

import java.io.Serializable;
import java.util.Collection;

/**
 * Deal with the relation between all kinds of entities, such as:
 * 1) insert or update an entity and its aggregate-related entities in a transaction;
 * 2) delete both of an entity and its aggregate-related entities in a transaction;
 * 3) assemble an entity and its related entities to a complete entity.
 * @param <E> the entity
 * @param <S> the id of the entity
 */
public interface Relation<E extends Entity<S>, S extends Serializable> {
    /**
     * insert all of its related entities which has aggregated relation
     * when insert the entity at same time
     * @param entity the entity
     */
    void insertValue(E entity);

    /**
     * update all of its related entities which has aggregated relation
     * when update the entity at same time
     * @param entity the entity
     */
    void updateValue(E entity);

    /**
     * delete all of its related entities which has aggregated relation
     * when delete the entity at same time
     * @param entity the entity
     */
    void deleteValue(E entity);

    /**
     * assemble all of its related entities into the entity
     * when load the entity at same time
     * @param entity the entity
     */
    void setValue(E entity);

    /**
     * assemble all of its related entities into each of the entities
     * when load the list of entities at same time
     * @param list the list of entities
     */
    void setValueForList(Collection<E> list);
}
