/* 
 * create by 2020年1月30日 上午11:28:38
 */
package com.edev.support.cache;

import com.edev.support.entity.Entity;

import java.io.Serializable;
import java.util.Collection;

/**
 * The basic cache used by ddd repository.
 * @see com.edev.support.ddd.Repository
 * @author fangang
 */
public interface BasicCache {
	/**
	 * set the entity to cache
	 * @param entity the entity
	 * @param <E> the entity
	 * @param <S> the id of the entity
	 */
	public <E extends Entity<S>, S extends Serializable> void set(E entity);

	/**
	 * get the entity from cache by id
	 * @param id the id of the entity
	 * @param clazz the class of the entity
	 * @param <E> the entity
	 * @param <S> the id of the entity
	 * @return the entity
	 */
	public <E extends Entity<S>, S extends Serializable> E get(S id, Class<E> clazz);

	/**
	 * delete the entity by id
	 * @param id the id of the entity
	 * @param clazz the class of the entity
	 * @param <E> the entity
	 * @param <S> the id of the entity
	 */
	public <E extends Entity<S>, S extends Serializable> void delete(S id, Class<E> clazz);

	/**
	 * set a list of entities to cache with batch
	 * @param entities list of entities
	 * @param <E> the entity
	 * @param <S> the id of the entity
	 */
	public <E extends Entity<S>, S extends Serializable> void setForList(Collection<E> entities);

	/**
	 * get a list of entities from cache with batch
	 * @param ids the list of ids of entities
	 * @param clazz the class of the entity
	 * @param <E> the entity
	 * @param <S> the id of the entity
	 * @return a list of entities
	 */
	public <E extends Entity<S>, S extends Serializable> Collection<E> getForList(Collection<S> ids, Class<E> clazz);

	/**
	 * delete a list of entities with batch
	 * @param ids the list of ids of entities
	 * @param clazz the class of the entity
	 * @param <E> the entity
	 * @param <S> the id of the entity
	 */
	public <E extends Entity<S>, S extends Serializable> void deleteForList(Collection<S> ids, Class<E> clazz);

	/**
	 * set a value to cache, that the value is a list.
	 * @param template the template of the entity
	 * @param list the list that need to cache
	 * @param <E> the entity
	 * @param <S> the id of the entity
	 */
	public <E extends Entity<S>, S extends Serializable> void setList(E template, Collection<E> list);

	/**
	 * get a value from cache, that the value is a list
	 * @param template just an empty object to know which entity
	 * @param <E> the entity
	 * @param <S> the id of the entity
	 * @return the list of entities
	 */
	public <E extends Entity<S>, S extends Serializable> Collection<E> getList(E template);
	/**
	 * delete a value from cache, that the value is a list.
	 * @param template just an empty object to know which entity
	 * @param <E> the entity
	 * @param <S> the id of the entity
	 */
	public <E extends Entity<S>, S extends Serializable> void deleteList(E template);
}
