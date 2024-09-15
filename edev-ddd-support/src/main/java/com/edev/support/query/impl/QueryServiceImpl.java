/* 
 * Created by 2019年1月24日
 */
package com.edev.support.query.impl;

import com.edev.support.dao.QueryDao;
import com.edev.support.entity.ResultSet;
import com.edev.support.query.QueryService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;

/**
 * The implement of the generic query service.
 * @author fangang
 */
@Getter
@Setter
public class QueryServiceImpl implements QueryService {
	private QueryDao queryDao;
	public QueryServiceImpl() {}
	public QueryServiceImpl(@NonNull QueryDao queryDao) {
		this.queryDao = queryDao;
	}
	@Override
	public ResultSet query(Map<String, Object> params) {
		ResultSet resultSet = new ResultSet();
		page(params, resultSet);
		aggregate(params, resultSet);
		
		beforeQuery(params);
		Collection<?> result = queryDao.query(params);
		resultSet.setData(result);
		resultSet = afterQuery(params, resultSet);
		return resultSet;
	}

	/**
	 * do something before query. 
	 * It just a hook that override the function in subclass if we need do something before query.
	 * @param params the parameters the query need
	 */
	protected void beforeQuery(Map<String, Object> params) {
		//just a hook
	}
	
	/**
	 * do something after query.
	 * It just a hook that override the function in subclass if we need do something after query.
	 * @param params the parameters the query need
	 * @param resultSet the result set before do something.
	 * @return the result set after do something.
	 */
	protected ResultSet afterQuery(Map<String, Object> params, ResultSet resultSet) {
		//just a hook
		return resultSet;
	}
	
	/**
	 * @param params the parameters
	 * @param resultSet the result set
	 */
	private void page(Map<String, Object> params, ResultSet resultSet) {
		if(params==null||params.isEmpty()) return;
		Object page = params.get("page");
		Object size = params.get("size");
		Object count = params.get("count");
		
		long cnt = (count==null) ? queryDao.count(params) : Long.parseLong(count.toString());
		resultSet.setCount(cnt);
		
		if( size==null ) return;
		int p = (page==null)? 0 : Integer.parseInt(page.toString());
		int s = Integer.parseInt(size.toString());
		int firstRow = p * s;
		params.put("page", p);
		params.put("size", s);
		params.put("firstRow", firstRow);
		resultSet.setPage(p);
		resultSet.setSize(s);
	}
	
	/**
	 * do sum, count, or other operations, and then insert the last row
	 * @param params the parameters
	 * @param resultSet the result set
	 */
	private void aggregate(Map<String, Object> params, ResultSet resultSet) {
		if(params==null||params.isEmpty()) return;
		@SuppressWarnings("unchecked")
		Map<String, String> aggregation = (Map<String, String>)params.get("aggregation");
		if(aggregation==null||aggregation.isEmpty()) return;
		
		@SuppressWarnings("unchecked")
		Map<String, Object> aggValue = (Map<String, Object>)params.get("aggValue");
		if(aggValue==null) aggValue = queryDao.aggregate(params);
		resultSet.setAggregation(aggValue);
	}
}
