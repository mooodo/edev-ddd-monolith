package com.edev.support.dao.utils;

import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.dao.impl.utils.DaoEntityBuilder;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.trade.order.entity.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class DaoEntityBuilderTest {
    private DomainObjectFactory factory;
    @Before
    public void setUp() throws Exception {
        factory = new DomainObjectFactory();
    }
    @Test
    public void testBuildDaoEntity() {
        factory.initFactory("classpath:entity/product.xml");
        Product product = new Product(1L,"Book",50D,"unit",null,null);
        DaoEntity daoEntity = DaoEntityBuilder.build(product);
        assertThat(daoEntity.getTableName(), equalTo("t_product"));
        assertThat(daoEntity.getColMap(), hasItems(
                getItem("id", 1L), getItem("name", "Book"),
                getItem("price", 50D), getItem("unit", "unit")
        ));
        assertThat(daoEntity.getPkMap(), hasItems(getItem("id", 1L)));
    }

    private Map<Object, Object> getItem(String column, Object value) {
        Map<Object, Object> colMap = new HashMap<>();
        colMap.put(DaoEntityBuilder.KEY, column);
        colMap.put(DaoEntityBuilder.VALUE, value);
        return colMap;
    }
    @Test
    public void testBuildForList() {
        factory.initFactory("classpath:entity/product.xml");
        List<Long> ids = Arrays.asList(1L,2L,3L);
        DaoEntity daoEntity = DaoEntityBuilder.buildForList(ids, Product.class);
        assertThat(daoEntity.getTableName(), equalTo("t_product"));
        assertThat(daoEntity.getColMap(), hasItems(getItem("id", ids)));
    }
}
