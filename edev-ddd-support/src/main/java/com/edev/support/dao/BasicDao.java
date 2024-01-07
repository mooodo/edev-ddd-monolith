/* 
 * Created by 2019年4月17日
 */
package com.edev.support.dao;

import com.edev.support.entity.Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The basic dao for generic insert, update, delete, or else operations.
 * @author fangang
 */
public interface BasicDao {
	/**
	 * insert an entity into table.
	 * @param entity the entity
	 */
	<E extends Entity<S>, S extends Serializable> S insert(E entity);
	/**
	 * update an entity.
	 * @param entity the entity
	 */
	<E extends Entity<S>, S extends Serializable> void update(E entity);
	/**
	 * if not exists, then insert, else update.
	 * @param entity the entity
	 */
	<E extends Entity<S>, S extends Serializable> S insertOrUpdate(E entity);
	/**
	 * delete some entities by a template.
	 * Usage: firstly create a template of the entity,
	 * then set conditions into the template.
	 * At last, do delete by the template, like this:
	 * <pre>
	 * User template = new User();
	 * user.setId("C0001");
	 * dao.delete(template);
	 * </pre>
	 * @param template the template of entity
	 */
	<E extends Entity<S>, S extends Serializable> void delete(E template);
	/**
	 * insert a list of entities, and if exists, then update.
	 * @param list the list of entities
	 */
	<E extends Entity<S>, S extends Serializable, C extends Collection<E>> void insertOrUpdateForList(C list);
	/**
	 * delete a list of entities.
	 * @param list the entity
	 */
	<E extends Entity<S>, S extends Serializable, C extends Collection<E>> void deleteForList(C list);
	/**
	 * delete an entity by id.
	 * @param id the id of the entity
	 * @param clazz the class of the entity
	 */
	<E extends Entity<S>, S extends Serializable> void delete(S id, Class<E> clazz);
	/**
	 * load an entity by id.
	 * @param id the id of the entity
	 * @param clazz the class of the entity
	 * @return entity
	 */
	<E extends Entity<S>, S extends Serializable> E load(S id, Class<E> clazz);
	/**
	 * @param ids the list of ids of entities
	 * @param clazz the class of the entity
	 */
	<E extends Entity<S>, S extends Serializable> void deleteForList(Collection<S> ids, Class<E> clazz);
	/**
	 * load a list of entity by their ids.
	 * @param ids the list of id
	 * @param clazz the class of the entity
	 * @return list of entity
	 */
	<E extends Entity<S>, S extends Serializable> Collection<E> loadForList(Collection<S> ids, Class<E> clazz);
	/**
	 * load all entities according to a condition, which the condition come from the template. 
	 * for example: I want to load all items of an order, then the template is the orderItem
	 * and set the orderId to the 'orderId' column in the template. 
	 * Then it will load all the items of this order according to conditions in the template.
	 * @param template just an empty object to know which entity, and set the condition to it.
	 * @return list of entities.
	 */
	<E extends Entity<S>, S extends Serializable> Collection<E> loadAll(E template);
	/**
	 * load all entities according to a condition, which the condition come from the list<map>, 
	 * such as: [{key:"col0",opt:'=',value:"val0"},{key:"col1",opt:'=',value:"val1"}]
	 * @param colMap the map of columns and its values
	 * @param clazz the class of the entity
	 * @return list of entities.
	 */
	<E extends Entity<S>, S extends Serializable> Collection<E> loadAll(List<Map<Object, Object>> colMap, Class<E> clazz);
}
