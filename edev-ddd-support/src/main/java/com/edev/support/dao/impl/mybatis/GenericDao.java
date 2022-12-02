/* 
 * Created by 2019年4月17日
 */
package com.edev.support.dao.impl.mybatis;

import com.edev.support.entity.Entity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * The generic dao which persist data with mybatis.
 * @author fangang
 */
@Mapper
public interface GenericDao {
	/**
	 * insert into values to the table
	 * @param tableName the table name
	 * @param columns the columns of the table and its value
	 * @throws DataAccessException throw if duplicate primary key or else
	 */
	public void insert(String tableName, List<Map<Object, Object>> columns);
	/**
	 * insert into values to the table
	 * @param tableName the table name
	 * @param columns the columns of the table and its value
	 * @param entity the entity that set the id into
	 * @throws DataAccessException throw if duplicate primary key or else
	 */
	public void insertGenerateKeys(String tableName, List<Map<Object, Object>> columns, Entity<?> entity);
	/**
	 * update values for the table
	 * @param tableName the table name
	 * @param columns the columns of the table and its value
	 * @param pks the map of the primary keys and its values
	 * @throws DataAccessException throw if duplicate primary key or else
	 */
	public void update(String tableName, List<Map<Object, Object>> columns, List<Map<Object, Object>> pks);
	/**
	 * delete by the primary keys
	 * @param tableName the table name
	 * @param pks the map of the keys and its values
	 * @throws DataAccessException throw if duplicate primary key or else
	 */
	public void delete(String tableName, List<Map<Object, Object>> pks);
	/**
	 * delete with in clause
	 * @param tableName the table name
	 * @param pks the map of the primary keys and its values
	 * @throws DataAccessException throw if delete fails or else
	 */
	public void deleteForList(String tableName, List<Map<Object, Object>> pks);
	/**
	 * load a record by the primary keys
	 * @param tableName the table name
	 * @param pks the map of the primary keys and its values
	 * @return result set
	 * @throws DataAccessException throw if delete fails or else
	 */
	public List<Map<String, Object>> load(String tableName, List<Map<Object, Object>> pks);
	/**
	 * query by the primary keys
	 * @param tableName the table name
	 * @param pks the map of the primary keys and its values
	 * @return result set
	 * @throws DataAccessException throw if query fails or else
	 */
	public List<Map<String, Object>> find(String tableName, List<Map<Object, Object>> pks);
}
