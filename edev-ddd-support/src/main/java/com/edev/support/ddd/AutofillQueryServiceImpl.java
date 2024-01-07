package com.edev.support.ddd;

import com.edev.support.dao.BasicDao;
import com.edev.support.dao.QueryDao;
import com.edev.support.ddd.join.JoinHelper;
import com.edev.support.ddd.join.RefHelper;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.entity.Entity;
import com.edev.support.entity.ResultSet;
import com.edev.support.query.impl.QueryServiceImpl;
import com.edev.support.utils.SpringHelper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;

@Slf4j
public class AutofillQueryServiceImpl extends QueryServiceImpl {
    @Autowired
    private SpringHelper springHelper;
    @Autowired
    private DddFactory dddFactory;
    private final BasicDao autofillDao;
    public AutofillQueryServiceImpl(@NonNull QueryDao queryDao, @NonNull BasicDao autofillDao) {
        super(queryDao);
        this.autofillDao = autofillDao;
    }

    @Override
    protected ResultSet afterQuery(Map<String, Object> params, @NonNull ResultSet resultSet) {
        //if no result, do nothing.
        Collection<?> data = resultSet.getData();
        if(data==null||data.isEmpty())
            return super.afterQuery(params, resultSet);
        Map<Class<Entity<Serializable>>, List<Entity<Serializable>>> groupMap = groutBy(data);
        groupMap.forEach(this::fillData);
        return super.afterQuery(params, resultSet);
    }

    /**
     * group the data by its class
     * @param collection the data
     * @return the map that the key is the class and the value is list of the entities that they are the class
     */
    protected Map<Class<Entity<Serializable>>, List<Entity<Serializable>>>
            groutBy(@NonNull Collection<?> collection) {
        Map<Class<Entity<Serializable>>, List<Entity<Serializable>>> map = new HashMap<>();
        collection.forEach(row -> {
            if(!dddFactory.isEntity(row.getClass()))
                throw new DddException("cannot complete fill because the result set are not entities!");
            Entity<Serializable> entity = (Entity<Serializable>) row;
            List<Entity<Serializable>> list = map.computeIfAbsent((Class<Entity<Serializable>>) entity.getClass(), k -> new ArrayList<>());
            list.add(entity);
        });
        return map;
    }

    /**
     * fill the data for these entities that they are the class
     * @param clazz the class
     * @param entities these entities that they are the class
     */
    protected void fillData(Class<Entity<Serializable>> clazz, List<Entity<Serializable>> entities) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(clazz);
        if(dObj.getJoins()!=null)
            dObj.getJoins().forEach(join -> autofillJoin(entities));
        if(dObj.getRefs()!=null)
            dObj.getRefs().forEach(ref -> autofillRef(entities));
    }

    private <E extends Entity<S>, S extends Serializable> void autofillJoin(Collection<E> list) {
        (new JoinHelper<E,S>(autofillDao)).setJoinForList(list);
    }

    private <E extends Entity<S>, S extends Serializable> void autofillRef(Collection<E> list) {
        (new RefHelper<E,S>(springHelper)).setRefForList(list);
    }
}
