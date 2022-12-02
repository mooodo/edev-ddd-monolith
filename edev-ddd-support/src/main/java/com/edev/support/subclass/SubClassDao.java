package com.edev.support.subclass;

import com.edev.support.dao.BasicDao;
import com.edev.support.dsl.DomainObject;
import com.edev.support.entity.Entity;

import java.io.Serializable;
import java.util.Map;

public interface SubClassDao extends BasicDao {
    boolean available(DomainObject dObj);
    <E extends Entity<S>, S extends Serializable> E createEntityByJson(Class<E> clazz, Map<String, Object> json);
    <E extends Entity<S>, S extends Serializable> E createEntityByRow(Class<E> clazz, Map<String, Object> row);
}
