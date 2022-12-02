/* 
 * Created by 2019年1月25日
 */
package com.edev.support.dao;

import java.util.Collection;
import java.util.Map;

/**
 * The generate query dao
 * @author fangang
 */
public interface QueryDao {
	/**
	 * execute query
	 * @param params the parameters the query need
	 * @return the result set of the query
	 */
	Collection<?> query(Map<String, Object> params);
	/**
	 * get count of the query
	 * @param params the parameters the query need
	 * @return the count
	 */
	long count(Map<String, Object> params);
	/**
	 * execute aggregate such as sum, count, average, etc.
	 * put a 'aggregation' key into the parameters
	 * and its value is a map, like this:
	 * <pre>
	 *     key: aggregation
	 *     value: map{"order_key":"count", "amount":"sum"},
	 * </pre>
	 * @param params the parameters the query need
	 * @return the result of the aggregate
	 */
	Map<String, Object> aggregate(Map<String, Object> params);
}
