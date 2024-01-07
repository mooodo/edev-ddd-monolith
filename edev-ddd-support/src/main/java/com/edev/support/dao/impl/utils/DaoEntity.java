/*
 * Created by 2020-12-04 12:41:48 
 */
package com.edev.support.dao.impl.utils;

import com.edev.support.dsl.Property;
import com.edev.support.entity.Entity;
import lombok.Data;

import java.util.*;

/**
 * the entity that help the dao to access the database.
 * @author fangang
 */
@Data
public class DaoEntity {
	private static final String KEY = "key";
	private static final String VALUE = "value";
	private String tableName;
	//colMap: a list of conditions like: [{key:"col0",opt:'=',value:"val0"},{key:"col1",opt:'=',value:"val1"}]
	private List<Map<Object, Object>> colMap = new ArrayList<>();
	private List<Map<Object, Object>> pkMap = new ArrayList<>();
	public void addColMap(Map<Object, Object> colMap) { this.colMap.add(colMap); }
	public void addPkMap(Map<Object, Object> pkMap) { this.pkMap.add(pkMap); }
	public void setProperties(Entity<?> entity, List<Property> properties) {
		for(Property property : properties) {
			String name = property.getName();
			String column = property.getColumn();
			Object value = entity.getValue(name);

			if(value==null) continue;
			Map<Object, Object> map = new HashMap<>();
			map.put(KEY, column);
			map.put(VALUE, value);
			this.addColMap(map);

			if(property.isPrimaryKey()) this.addPkMap(map);
		}
	}
}
