package com.edev.support.dao.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.entity.Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The decorator for BasicDao, that others can extend all kinds of functions for BasicDao.
 * @author fangang
 * @see com.edev.support.cache.CacheEntityDao
 * @see com.edev.support.ddd.Repository
 * @see com.edev.support.subclass.SubClassPlusDao
 */
public abstract class DecoratorDao implements BasicDao {
	private BasicDao dao;
	protected DecoratorDao() {}
	protected DecoratorDao(BasicDao dao) {
		this.dao = dao;
	}

	public BasicDao getDao() {
		return dao;
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> S insert(E entity) {
		return dao.insert(entity);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void update(E entity) {
		dao.update(entity);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> S insertOrUpdate(E entity) {
		return dao.insertOrUpdate(entity);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void delete(E entity) {
		dao.delete(entity);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable, C extends Collection<E>> void insertOrUpdateForList(C list) {
		dao.insertOrUpdateForList(list);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable, C extends Collection<E>> void deleteForList(C list) {
		dao.deleteForList(list);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void delete(S id, Class<E> clazz) {
		dao.delete(id, clazz);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> E load(S id, Class<E> clazz) {
		return dao.load(id, clazz);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> void deleteForList(Collection<S> ids, Class<E> clazz) {
		dao.deleteForList(ids, clazz);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> Collection<E> loadForList(Collection<S> ids, Class<E> clazz) {
		return dao.loadForList(ids, clazz);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(E template) {
		return dao.loadAll(template);
	}

	@Override
	public <E extends Entity<S>, S extends Serializable> Collection<E> loadAll(List<Map<Object, Object>> colMap, Class<E> clazz) {
		return dao.loadAll(colMap, clazz);
	}
}