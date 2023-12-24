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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class AutofillQueryServiceImpl extends QueryServiceImpl {
    @Autowired
    private SpringHelper springHelper;
    @Autowired
    private DddFactory dddFactory;
    private BasicDao autofillDao;
    public AutofillQueryServiceImpl() {}
    public AutofillQueryServiceImpl(QueryDao queryDao, BasicDao autofillDao) {
        super(queryDao);
        this.autofillDao = autofillDao;
    }

    @Override
    protected ResultSet afterQuery(Map<String, Object> params, ResultSet resultSet) {
        //if no result, do nothing.
        Collection<?> data = resultSet.getData();
        if(data==null||data.isEmpty())
            return super.afterQuery(params, resultSet);

        Object first = data.iterator().next();
        if(!dddFactory.isEntity(first.getClass()))
            return super.afterQuery(params, resultSet);
        Collection<Entity<Serializable>> entities = (Collection<Entity<Serializable>>) data;
        DomainObject dObj = DomainObjectFactory.getDomainObject(first.getClass());
        if(dObj.getJoins()!=null)
            dObj.getJoins().forEach(join -> autofillJoin(entities));
        if(dObj.getRefs()!=null)
            dObj.getRefs().forEach(ref -> autofillRef(entities));

        return super.afterQuery(params, resultSet);
    }

    private <E extends Entity<S>, S extends Serializable> void autofillJoin(Collection<E> list) {
        (new JoinHelper<E,S>(autofillDao)).setJoinForList(list);
    }

    private <E extends Entity<S>, S extends Serializable> void autofillRef(Collection<E> list) {
        (new RefHelper<E,S>(springHelper)).setRefForList(list);
    }
}
