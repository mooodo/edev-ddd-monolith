package com.edev.support.dao.impl.utils;

import com.edev.support.dao.impl.mybatis.GenericDao;
import com.edev.support.ddd.DddFactory;
import com.edev.support.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DaoExecutor {
    @Autowired
    private DddFactory dddFactory;
    @Autowired
    private GenericDao dao;

    public <E extends Entity<S>, S extends Serializable>
            S insert(DaoEntity daoEntity, E entity) {
        if(daoEntity.getTableName()==null || daoEntity.getTableName().isEmpty()) return entity.getId();
        if(entity.getId()!=null)
            dao.insert(daoEntity.getTableName(), daoEntity.getColMap());
        else
            dao.insertGenerateKeys(daoEntity.getTableName(), daoEntity.getColMap(), entity);
        return entity.getId();
    }

    public void update(DaoEntity daoEntity) {
        if(daoEntity.getTableName()==null || daoEntity.getTableName().isEmpty()) return;
        daoEntity.removePkFromColMap();
        dao.update(daoEntity.getTableName(), daoEntity.getColMap(), daoEntity.getPkMap());
    }

    public void delete(DaoEntity daoEntity) {
        if(daoEntity.getTableName()==null || daoEntity.getTableName().isEmpty()) return;
        dao.delete(daoEntity.getTableName(), daoEntity.getColMap());
    }

    public void deleteForList(DaoEntity daoEntity) {
        if(daoEntity.getTableName()==null || daoEntity.getTableName().isEmpty()) return;
        dao.deleteForList(daoEntity.getTableName(),
                (daoEntity.getPkMap().isEmpty())?daoEntity.getColMap():daoEntity.getPkMap());
    }

    public <E extends Entity<S>, S extends Serializable>
            List<E> load(DaoEntity daoEntity, Class<E> clazz) {
        if(daoEntity.getTableName()==null || daoEntity.getTableName().isEmpty()) return new ArrayList<>();
        List<Map<String, Object>> listOfMap = dao.load(daoEntity.getTableName(), daoEntity.getColMap());
        return dddFactory.createEntityByRowForList(clazz, listOfMap);
    }

    public <E extends Entity<S>, S extends Serializable>
            List<E> find(DaoEntity daoEntity, Class<E> clazz) {
        if(daoEntity.getTableName()==null || daoEntity.getTableName().isEmpty()) return new ArrayList<>();
        List<Map<String, Object>> list = dao.find(daoEntity.getTableName(), daoEntity.getColMap());
        return dddFactory.createEntityByRowForList(clazz, list);
    }
}
