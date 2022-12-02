package com.edev.support.ddd;

import com.edev.support.dao.QueryDao;
import com.edev.support.dao.impl.QueryDaoMybatisImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;

public class QueryDaoMybastisImplForDdd extends QueryDaoMybatisImpl implements QueryDao {
    @Autowired
    private DddFactory dddFactory;
    private String entityClass;
    public QueryDaoMybastisImplForDdd() {super();}
    public QueryDaoMybastisImplForDdd(String entityClass, String sqlMapper) {
        super(sqlMapper);
        this.entityClass = entityClass;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Collection<?> query(Map<String, Object> params) {
        Collection<?> collection = super.query(params);
        return dddFactory.createEntityByRowForList(entityClass, convert(collection));
    }

    private Collection<Map<String, Object>> convert(Collection<?> collection) {
        //TODO need fix
        return (Collection<Map<String, Object>>) collection;
    }
}
