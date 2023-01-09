/*
 * Created by 2020-12-04 12:41:48 
 */
package com.edev.support.dao.impl.utils;

import com.edev.support.dsl.Property;
import com.edev.support.entity.Entity;

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
}
