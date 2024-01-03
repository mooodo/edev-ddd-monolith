package com.edev.support.ddd;

import com.edev.support.dao.QueryDao;
import com.edev.support.dao.impl.QueryDaoMybatisImpl;
import com.edev.support.entity.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class QueryDaoMybastisImplForDdd extends QueryDaoMybatisImpl implements QueryDao {
    @Autowired
    private DddFactory dddFactory;
    @Getter @Setter
    private String entityClass;
    public QueryDaoMybastisImplForDdd(String entityClass, String sqlMapper) {
        super(sqlMapper);
        this.entityClass = entityClass;
    }
    @Override
    public Collection<? extends Entity<? extends Serializable>> query(Map<String, Object> params) {
        Collection<Map<String, Object>> collection = (Collection<Map<String, Object>>)super.query(params);
        return dddFactory.createEntityByRowForList(entityClass, collection);
    }
}
