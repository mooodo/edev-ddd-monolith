/* 
 * Created by 2019年4月17日
 */
package com.edev.support.dao.impl;

import com.edev.support.dao.QueryDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;

/**
 * The implements of the QueryDao with mybatis.
 * @author fangang
 */
public class QueryDaoMybatisImpl implements QueryDao {
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	private String sqlMapper;

	public QueryDaoMybatisImpl() {}
	public QueryDaoMybatisImpl(String sqlMapper) {
		this.sqlMapper = sqlMapper;
	}

	/**
	 * @return the sqlMapper
	 */
	public String getSqlMapper() {
		return sqlMapper;
	}

	/**
	 * @param sqlMapper the sqlMapper to set
	 */
	public void setSqlMapper(String sqlMapper) {
		this.sqlMapper = sqlMapper;
	}

	@Override
	public Collection<?> query(Map<String, Object> params) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
			return sqlSession.selectList(sqlMapper + ".query", params);
		}
	}

	@Override
	public long count(Map<String, Object> params) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
			return sqlSession.selectOne(sqlMapper + ".count", params);
		}
	}

	@Override
	public Map<String, Object> aggregate(Map<String, Object> params) {
		Map<String, String> aggregation = (Map<String, String>)params.get("aggregation");
		if(aggregation==null||aggregation.isEmpty()) return null;
		
		StringBuilder buffer = new StringBuilder();
		for(Map.Entry<String, String> entry: aggregation.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if(!"".contentEquals(buffer)) buffer.append(", ");
			if("sum".equalsIgnoreCase(value)||"avg".equalsIgnoreCase(value))
				buffer.append(value).append("(").append(key).append(") ").append(key);
			else if("count".equalsIgnoreCase(value))
				buffer.append(value).append("(*) ").append(key);
			else
				buffer.append("'").append(value).append("' as ").append(key);
		}
		params.put("aggregation", buffer);

		try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
			return sqlSession.selectOne(sqlMapper + ".aggregate", params);
		}
	}
}
