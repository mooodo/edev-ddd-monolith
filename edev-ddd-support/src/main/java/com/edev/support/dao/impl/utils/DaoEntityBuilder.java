package com.edev.support.dao.impl.utils;

import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.entity.Entity;
import com.edev.support.exception.OrmException;
import com.edev.support.utils.BeanUtils;

import java.io.Serializable;
import java.util.*;

public class DaoEntityBuilder {
    public static final String KEY = "key";
    public static final String VALUE = "value";
    private DaoEntityBuilder() {}
    /**
     * read and decode the data from entity and build the daoEntity
     * @param entity the entity
     * @return daoEntity
     */
    public static DaoEntity build(Entity<?> entity) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(entity.getClass());
        return build(entity, dObj);
    }

    /**
     * read the data from entity, decode the data from domain object, then build the daoEntity.
     * It used when the entity and the domain object come from different class.
     * @param entity the entity
     * @param dObj the domain object
     * @return daoEntity
     */
    public static DaoEntity build(Entity<?> entity, DomainObject dObj) {
        DaoEntity daoEntity = new DaoEntity();
        daoEntity.setTableName(dObj.getTable());
        daoEntity.setProperties(entity, dObj.getProperties());
        return daoEntity;
    }

    /**
     * build the daoEntity for list operations.
     * Finally, get the daoEntity with tableName and pkMap like: [{key:"id", value:[id0,id1,id2]}
     * @param ids list of ids
     * @param clazz the class of the entity
     * @return daoEntity
     */
    public static <S extends Serializable, T extends Entity<S>>
    DaoEntity buildForList(Collection<S> ids, Class<T> clazz) {
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
     * @param <E> Entity
     * @param <S> id
     * @return list of DaoEntities
     */
    private static <E extends Entity<S>, S extends Serializable>
    List<DaoEntity> getListOfDaoEntities(Collection<S> ids, E template) {
        List<DaoEntity> listOfDaoEntity = new ArrayList<>();
        ids.forEach(id->{
            E entity = (E) template.clone();
            entity.setId(id);
            DaoEntity daoEntity = build(entity);
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
                    mapOfValues.computeIfAbsent(key, k -> new ArrayList<>());
                    mapOfValues.get(key).add(value);
                }));
        return mapOfValues;
    }

    /**
     * build the final daoEntity with the colMap like: { pk0:listOfValues0, pk1:listOfValues1 }
     * @param mapOfValues map of values
     * @param template the template of the class
     * @return the final daoEntity
     */
    private static DaoEntity buildDaoEntity(Map<Object, List<Object>> mapOfValues, Entity<?> template) {
        DaoEntity daoEntity = build(template);
        List<Map<Object, Object>> colMap = new ArrayList<>();
        for(Map.Entry<Object, List<Object>> entry: mapOfValues.entrySet()) {
            Map<Object, Object> map = new HashMap<>();
            map.put(KEY, entry.getKey());
            map.put(VALUE, entry.getValue());
            colMap.add(map);
        }
        daoEntity.setColMap(colMap);
        return daoEntity;
    }
}
