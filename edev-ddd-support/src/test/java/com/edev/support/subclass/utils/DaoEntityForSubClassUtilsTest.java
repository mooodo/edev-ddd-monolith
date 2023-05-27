package com.edev.support.subclass.utils;

import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.dao.impl.utils.DaoEntityBuilder;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.trade.customer.entity.GoldenVip;
import com.edev.trade.customer.entity.Vip;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class DaoEntityForSubClassUtilsTest {
    private DomainObjectFactory factory;
    @Before
    public void setUp() throws Exception {
        factory = new DomainObjectFactory();
    }
    @Test
    public void testBuildWithEntityAndItsSubClass() {
        factory.initFactory("classpath:entity/customer.xml");
        Vip vip = GoldenVip.build().setValues(1L,true,600L,100D);
        DaoEntity daoEntity = DaoEntityForSubClassUtils.buildWithEntityAndItsSubClass(vip);
        assertThat(daoEntity.getTableName(), equalTo("t_vip"));
        assertThat(daoEntity.getColMap(), hasItems(
                getItem("id",1L), getItem("available", "1"),
                getItem("coin",600L), getItem("vip_type", "golden"),
                getItem("cashback",100D)
        ));
        assertThat(daoEntity.getPkMap(), hasItems(getItem("id",1L)));
    }

    private Map<Object, Object> getItem(String column, Object value) {
        Map<Object, Object> colMap = new HashMap<>();
        colMap.put(DaoEntityBuilder.KEY, column);
        colMap.put(DaoEntityBuilder.VALUE, value);
        return colMap;
    }
}
