/*
 * Created by 2020-12-31 20:05:21 
 */
package com.edev.support.cache;

import com.edev.support.dao.BasicDao;
import com.edev.support.dao.impl.DecoratorDao;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.entity.Entity;

import java.io.Serializable;
import java.util.*;

/**
 * The Dao using cache: 
 * 1) if load an entity, load from cache first.
 * 2) if not in the cache, set to cache after query from database.
 * 3) if update or delete an entity, delete from cache.
 * @author fangang
 */
public class CacheEntityDao extends DecoratorDao implements BasicDao {
	private BasicCache cache;

	public CacheEntityDao() {
		super();
	}
	public CacheEntityDao(BasicDao dao, BasicCache cache) {
		super(dao);
		this.cache = cache;
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void update(E entity) {
		super.update(entity);
		deleteCache(entity);
		deleteCacheOfJoin(entity);
	}

	/**
	 * delete the value in the cache.
	 * @param entity the entity
	 */
	private <E extends Entity<S>, S extends Serializable> void deleteCache(E entity) {
		cache.delete(entity.getId(), EntityUtils.getClass(entity));
	}
	
	private <E extends Entity<S>, S extends Serializable> void deleteCacheOfJoin(E entity) {
		if(entity==null) throw new CacheException("The entity is null!");
		String className = entity.getClass().getName();
		DomainObject dObj = DomainObjectFactory.getDomainObject(className);
		dObj.getJoins().forEach(join -> {
			String clazz = join.getClazz();
			Entity<S> template = (new EntityBuilder<Entity<S>,S>(clazz)).createEntity();
			String joinKey = join.getJoinKey();
			if(join.getJoinType().equals("oneToMany")) {
				template.setValue(joinKey, entity.getId());
				cache.deleteList(template);
			} else if(join.getJoinType().equals("oneToOne")) {
				S id = entity.getId();
				cache.delete(id, EntityUtils.getClass(template));
			} else {
				S id = (S)entity.getValue(joinKey);
				cache.delete(id, EntityUtils.getClass(template));
			}
		});
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(E entity) {
		super.insertOrUpdate(entity);
		deleteCache(entity);
		deleteCacheOfJoin(entity);
		return entity.getId();
	}

	@Override
	public <E extends Entity<S>, S extends Serializable, C extends Collection<E>> void insertOrUpdateForList(C list) {
		super.insertOrUpdateForList(list);
		if(list==null||list.isEmpty()) return;
		deleteCacheForList(list);
		deleteCacheOfJoinForList(list);
	}
	
	/**
	 * delete a list of entities from cache.
	 * @param list the list of entities
	 */
	private <S extends Serializable, T extends Entity<S>> void deleteCacheForList(Collection<T> list) {
		if(list==null||list.isEmpty()) return;
		List<S> ids = new ArrayList<>();
		list.forEach(entity->ids.add(entity.getId()));
		T template = list.iterator().next();
		cache.deleteForList(ids, EntityUtils.getClass(template));
	}
	
	/**
	 * delete the joins of a list of entities from cache.
	 * @param list the list of entities
	 */
	private <E extends Entity<S>, S extends Serializable> void deleteCacheOfJoinForList(Collection<E> list) {
		list.forEach(this::deleteCacheOfJoin);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void delete(S id, Class<E> clazz) {
		if(id==null||clazz==null) throw new CacheException("The id or class is null!");
		super.delete(id, clazz);
		cache.delete(id, clazz);
		E template = EntityBuilder.build(clazz);
		template.setId(id);
		deleteCacheOfJoin(template);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void delete(E entity) {
		super.delete(entity);
		deleteCache(entity);
		deleteCacheOfJoin(entity);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void deleteForList(Collection<S> ids, Class<E> clazz) {
		super.deleteForList(ids, clazz);
		cache.deleteForList(ids, clazz);
		List<E> list = new ArrayList<>();
		ids.forEach(id -> {
			E template = EntityBuilder.build(clazz);
			template.setId(id);
			list.add(template);
		});
		deleteCacheOfJoinForList(list);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable, C extends Collection<E>> void deleteForList(C list) {
		super.deleteForList(list);
		if(list==null||list.isEmpty()) return;
		deleteCacheForList(list);
		deleteCacheOfJoinForList(list);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> E load(S id, Class<E> clazz) {
		E entity = cache.get(id, clazz);
		if(entity!=null) return entity;
		entity = super.load(id, clazz);
		cache.set(entity);
		return entity;
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> Collection<E> loadForList(Collection<S> ids, Class<E> clazz) {
		if(ids==null||clazz==null) throw new CacheException("The ids or class is null!");
		
		Collection<E> entities = cache.getForList(ids, clazz);
		entities.removeIf(Objects::isNull);
		Collection<S> otherIds = getIdsNotInCache(ids, entities);
		if(otherIds.isEmpty()) return entities; //get all of entities.
		
		Collection<E> collection = super.loadForList(otherIds, clazz);
		cache.setForList(collection);
		if(otherIds.size()==ids.size()) return collection; //all of the entities query for database.
		return fillOtherEntitiesIn(entities, collection); //fill the entity query for db in the list of entities get in cache.
	}
	
	/**
	 * @param ids the list of ids of entities
	 * @param entities the list of entities
	 * @return all list of ids, that not in cache
	 */
	private <E extends Entity<S>, S extends Serializable> Collection<S> getIdsNotInCache(Collection<S> ids, Collection<E> entities) {
		Map<S, E> map = new HashMap<>();
		entities.forEach(entity->map.put(entity.getId(), entity));
		List<S> otherIds = new ArrayList<>();
		ids.forEach(id->{if(map.get(id)==null) otherIds.add(id);});
		return otherIds;
	}
	
	/**
	 * fill the entities, which load from other source, in the list of entities load from cache.
	 * @param entities the list of entities load from cache
	 * @param otherEntities the other entities load from other source
	 * @return the list of entities
	 */
	private <E extends Entity<S>, S extends Serializable>
			Collection<E> fillOtherEntitiesIn(Collection<E> entities, Collection<E> otherEntities) {
		entities.addAll(otherEntities);
		return entities;
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(E template) {
		Collection<E> collection = cache.getList(template);
		if(collection!=null&&!collection.isEmpty()) return collection; //get values from cache directly

		collection = super.loadAll(template); //get values from databases.
		if(collection!=null&&!collection.isEmpty()) cache.setList(template, collection);//push to cache.
		return collection;
	}
}
