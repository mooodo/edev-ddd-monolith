/* 
 * create by 2020年1月30日 下午12:12:14
 */
package com.edev.support.cache;

import com.edev.support.dao.impl.DaoException;
import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.dao.impl.utils.DaoEntityBuilder;
import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.entity.Entity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

/**
 * The cache implement for redis.
 * @author fangang
 */
@Slf4j
@Component("redisCache")
public class RedisCache implements BasicCache {
	private static final String SPLITTER = "#";
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public <E extends Entity<S>, S extends Serializable> void set(E entity) {
		if(entity==null) return;
		String key = generateKey(entity.getId(), EntityUtils.getClass(entity));
		log.debug("set a value object to cache: {key: "+key+", value: "+entity+"}");
		redisTemplate.opsForValue().set(key, entity);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> E get(S id, Class<E> clazz) {
		if(id==null||clazz==null) return null;
		String key = generateKey(id, clazz);
		Object entity = redisTemplate.opsForValue().get(key);
		if(entity==null) return null;
		log.debug("get a value object from cache: {key: "+key+", value: "+entity+"}");
		return object2Entity(entity, clazz);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void delete(S id, Class<E> clazz) {
		if(id==null||clazz==null) return;
		String key = generateKey(id, clazz);
		log.debug("delete a value object from cache: {key: "+key+"}");
		redisTemplate.delete(key);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void setForList(Collection<E> entities) {
		if(entities==null||entities.isEmpty()) return;
		Map<String, Entity<S>> map = new HashMap<>();
		entities.forEach(entity->{
			String key = generateKey(entity.getId(), EntityUtils.getClass(entity));
			map.put(key, entity);
		});
		log.debug("set a list of value objects to cache: {values: "+entities+"}");
		redisTemplate.opsForValue().multiSet(map);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> List<E> getForList(Collection<S> ids, Class<E> clazz) {
		if(ids==null||clazz==null) return new ArrayList<>();
		List<String> keys = new ArrayList<>();
		ids.forEach(id->{
			String key = generateKey(id, clazz);
			keys.add(key);
		});
		List<Object> values = redisTemplate.opsForValue().multiGet(keys);
		if(values==null) return new ArrayList<>();
		List<E> entities = new ArrayList<>();
		values.forEach(value->{
			E entity = object2Entity(value, clazz);
			entities.add(entity);
		});
		log.debug("get a list of value objects from cache: {keys: "+keys+", values: "+entities+"}");
		return entities;
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void deleteForList(Collection<S> ids, Class<E> clazz) {
		if(ids==null||clazz==null) return;
		List<String> keys = new ArrayList<>();
		ids.forEach(id->{
			String key = generateKey(id, clazz);
			keys.add(key);
		});
		log.debug("delete a list of value objects from cache: {keys: "+keys+"}");
		redisTemplate.delete(keys);
	}
	
	/**
	 * convert an object to entity
	 * @param obj the object which need to convert
	 * @param clazz the class of the entity
	 * @return convert an object to entity
	 */
	private <E extends Entity<S>, S extends Serializable> E object2Entity(Object obj, Class<E> clazz) {
		if(obj==null||clazz==null) return null;
		if(!clazz.equals(obj.getClass()))
			throw new DaoException("the object must be an entity[%s]",obj.getClass());
		return (E) obj;
	}
	
	/**
	 * @param id the id of the entity
	 * @param clazz the class of the entity
	 * @return generate the key with "className#id" rule.
	 */
	private <E extends Entity<S>, S extends Serializable> String generateKey(S id, Class<E> clazz) {
		return clazz + SPLITTER + id;
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void setList(E template, Collection<E> collection) {
		collection.forEach(entity->{
			String cacheKey = generateKey(template);
			redisTemplate.opsForList().leftPush(cacheKey, entity);
			log.debug("left push value to cache: {key: "+cacheKey+"}");
		});
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> Collection<E> getList(E template) {
		String cacheKey = generateKey(template);
		List<E> list = new ArrayList<>();
		Long size = redisTemplate.opsForList().size(cacheKey);
		if(size==null||size==0) return list;
		List<Object> values = redisTemplate.opsForList().range(cacheKey, 0, size);
		log.debug("read list of values from cache: {key: "+cacheKey+", size: "+size+"}");
		if(values==null||values.isEmpty()) return list;
		values.forEach(value->list.add((E) value));
		return list;
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void deleteList(E template) {
		String cacheKey = generateKey(template);
		redisTemplate.delete(cacheKey);
		log.debug("delete the list of values from cache: {key: "+cacheKey+"}");
	}
	
	/**
	 * @param entity the entity
	 * @return generate the key with "className#value[0]#value[1]#..." rule.
	 */
	private <E extends Entity<S>, S extends Serializable> String generateKey(E entity) {
		String clazz = entity.getClass().getName();
		DaoEntity daoEntity = DaoEntityBuilder.build(entity);
		StringBuilder buffer = new StringBuilder(clazz);
		daoEntity.getColMap().forEach(map -> buffer.append(SPLITTER).append(map.get("value")));
		return buffer.toString();
	}
}
