/*
 * Created by 2020-12-04 12:41:48 
 */
package com.edev.support.dao.impl.utils;

import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.dsl.Property;
import com.edev.support.entity.Entity;
import com.edev.support.exception.OrmException;
import com.edev.support.utils.BeanUtils;

import java.io.Serializable;
import java.util.*;

/**
 * the entity that help the dao to access the database.
 * @author fangang
 */
public class DaoEntity {
	private static final String KEY = "key";
	private static final String VALUE = "value";
	private String tableName;
	//colMap: a list of conditions like: [{key:"col0",opt:'=',value:"val0"},{key:"col1",opt:'=',value:"val1"}]
	private List<Map<Object, Object>> colMap = new ArrayList<>();
	private List<Map<Object, Object>> pkMap = new ArrayList<>();
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the colMap
	 */
	public List<Map<Object, Object>> getColMap() {
		return colMap;
	}
	/**
	 * @param colMap the colMap to set
	 */
	public void setColMap(List<Map<Object, Object>> colMap) {
		this.colMap = colMap;
	}

	/**
	 * @param colMap the colMap to add
	 */
	public void addColMap(Map<Object, Object> colMap) { this.colMap.add(colMap); }
	/**
	 * @return the pkMap
	 */
	public List<Map<Object, Object>> getPkMap() {
		return pkMap;
	}
	/**
	 * @param pkMap the pkMap to set
	 */
	public void setPkMap(List<Map<Object, Object>> pkMap) {
		this.pkMap = pkMap;
	}

	/**
	 * @param pkMap the pkMap to add
	 */
	public void addPkMap(Map<Object, Object> pkMap) { this.pkMap.add(pkMap); }

	/**
	 * read and decode the data from entity and build the daoEntity
	 * @param entity the entity
	 * @return daoEntity
	 */
	public static DaoEntity readDataFromEntity(Entity<?> entity) {
		DomainObject dObj = DomainObjectFactory.getDomainObject(entity.getClass());
		return readDataFromEntity(entity, dObj);
	}

	/**
	 * read the data from entity and decode the data from domain object, then build the daoEntity
	 * @param entity the entity
	 * @param dObj the domain object
	 * @return daoEntity
	 */
	public static DaoEntity readDataFromEntity(Entity<?> entity, DomainObject dObj) {
		DaoEntity daoEntity = new DaoEntity();
		daoEntity.setTableName(dObj.getTable());
		setProperties(daoEntity, entity, dObj.getProperties());
		return daoEntity;
	}

	public static void setProperties(DaoEntity daoEntity, Entity<?> entity, List<Property> properties) {
		for(Property property : properties) {
			String name = property.getName();
			String column = property.getColumn();
			Object value = entity.getValue(name);

			if(value==null) continue;
			Map<Object, Object> map = new HashMap<>();
			map.put(KEY, column);
			map.put(VALUE, value);
			daoEntity.addColMap(map);

			if(property.isPrimaryKey()) daoEntity.addPkMap(map);
		}
	}

	/**
	 * prepare for list operations.
	 * @param ids list of ids
	 * @param clazz the class of the entity
	 * @return daoEntity
	 */
	public static <S extends Serializable, T extends Entity<S>>
			DaoEntity prepareForList(Collection<S> ids, Class<T> clazz) {
		if(clazz==null) throw new OrmException("illegal parameters!");
		if(ids==null||ids.isEmpty()) return null;
		T template = BeanUtils.createBean(clazz);

		List<DaoEntity> listOfDaoEntity = getListOfDaoEntities(ids, template);
		Map<Object, List<Object>> mapOfValues = getMapOfValues(listOfDaoEntity);
		return buildDaoEntity(mapOfValues, template);
	}

	/**
	 * get list of DaoEntity for each of id
	 * @param ids collection of ids
	 * @param template the template of the class
	 * @param <S> id
	 * @param <T> Entity
	 * @return list of DaoEntities
	 */
	private static <S extends Serializable, T extends Entity<S>>
			List<DaoEntity> getListOfDaoEntities(Collection<S> ids, T template) {
		List<DaoEntity> listOfDaoEntity = new ArrayList<>();
		ids.forEach(id->{
			T entity = (T) template.clone();
			entity.setId(id);
			DaoEntity daoEntity = readDataFromEntity(entity);
			listOfDaoEntity.add(daoEntity);
		});
		return listOfDaoEntity;
	}

	/**
	 * get map like this: { pk0:listOfValues0, pk1:listOfValues1 }
	 * @param listOfDaoEntity list of DaoEntity
	 * @return map of values
	 */
	private static Map<Object, List<Object>> getMapOfValues(List<DaoEntity> listOfDaoEntity) {
		Map<Object, List<Object>> mapOfValues = new HashMap<>();
		listOfDaoEntity.forEach(daoEntity ->
				daoEntity.getPkMap().forEach(map -> {
					Object key = map.get(KEY);
					Object value = map.get(VALUE);
					mapOfValues.computeIfAbsent(key, k -> new ArrayList<Object>());
					mapOfValues.get(key).add(value);
				}));
		return mapOfValues;
	}

	/**
	 * build the final daoEntity with the pkMap like: { pk0:listOfValues0, pk1:listOfValues1 }
	 * @param mapOfValues map of values
	 * @param template the template of the class
	 * @param <S> id
	 * @param <T> entity
	 * @return the final daoEntity
	 */
	private static <S extends Serializable, T extends Entity<S>>
			DaoEntity buildDaoEntity(Map<Object, List<Object>> mapOfValues, T template) {
		DaoEntity daoEntity = readDataFromEntity(template);
		List<Map<Object, Object>> pkMap = new ArrayList<>();
		for(Map.Entry<Object, List<Object>> entry: mapOfValues.entrySet()) {
			Map<Object, Object> map = new HashMap<>();
			map.put(KEY, entry.getKey());
			map.put(VALUE, entry.getValue());
			pkMap.add(map);
		}
		daoEntity.setPkMap(pkMap);
		return daoEntity;
	}
}
