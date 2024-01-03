package com.edev.support.dao;

import com.edev.support.dao.impl.QueryDaoMybatisImpl;
import com.edev.support.ddd.QueryDaoMybastisImplForDdd;
import com.edev.trade.TradeApplication;
import com.edev.trade.customer.entity.Customer;
import com.edev.trade.order.entity.Order;
import com.edev.trade.product.entity.Distributor;
import com.edev.trade.product.entity.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class QueryDaoTest {
    @Autowired @Qualifier("customerQry")
    private QueryDao customerQry;

    /**
     * 如果在mapper配置文件中，将resultType设置为某个实体对象，则查询返回该对象的集合
     */
    @Test
    public void testQueryCustomer() {
        Map<String, Object> params = new HashMap<>();
        Collection<?> result = customerQry.query(params);
        assertThat(result.iterator().next(), instanceOf(Customer.class));
    }
    @Autowired @Qualifier("orderQry")
    private QueryDao orderQry;

    /**
     * 如果在mapper配置文件中，将resultType设置为Map，则查询返回Map的集合
     */
    @Test
    public void testQueryOrder() {
        Map<String, Object> params = new HashMap<>();
        Collection<?> result = orderQry.query(params);
        assertThat(result.iterator().next(), instanceOf(Map.class));
    }
    @Autowired @Qualifier("orderQryForDdd")
    private QueryDao orderQryForDdd;

    /**
     * 在mapper配置文件中，将resultType设置为Map，
     * 但使用QueryDaoMybastisImplForDdd进行查询，并在entityClass中指定某个实体对象
     * 则查询返回该对象的集合，但未完成数据补填
     */
    @Test
    public void testQueryOrderForDdd() {
        Map<String, Object> params = new HashMap<>();
        Collection<?> result = orderQryForDdd.query(params);
        assertThat(result.iterator().next(), instanceOf(Order.class));
        Order order = (Order) result.iterator().next();
        assertNull(order.getCustomer());
        assertNull(order.getAddress());
        assertNull(order.getPayment());
        assertNull(order.getOrderItems());
    }
    @Autowired @Qualifier("distributorQry")
    private QueryDao distributorQry;
    @Test
    public void testQueryDistributor() {
        Map<String, Object> params = new HashMap<>();
        Collection<?> result = distributorQry.query(params);
        assertThat(result.iterator().next(), instanceOf(Distributor.class));
    }
    @Autowired @Qualifier("supplierQry")
    private QueryDao supplierQry;
    @Test
    public void testQuerySupplier() {
        Map<String, Object> params = new HashMap<>();
        Collection<?> result = supplierQry.query(params);
        assertThat(result.iterator().next(), instanceOf(Supplier.class));
    }
}

@Configuration
class QryConfig {
    @Bean
    public QueryDao customerQry() {
        String sqlMapper = "com.edev.trade.query.dao.CustomerMapper";
        return new QueryDaoMybatisImpl(sqlMapper);
    }
    @Bean
    public QueryDao orderQry() {
        String sqlMapper = "com.edev.trade.query.dao.OrderMapper";
        return new QueryDaoMybatisImpl(sqlMapper);
    }
    @Bean
    public QueryDao orderQryForDdd() {
        String entityClass = "com.edev.trade.order.entity.Order";
        String sqlMapper = "com.edev.trade.query.dao.OrderMapper";
        return new QueryDaoMybastisImplForDdd(entityClass, sqlMapper);
    }
    @Bean
    public QueryDao distributorQry() {
        String entityClass = "com.edev.trade.product.entity.Distributor";
        String sqlMapper = "com.edev.trade.query.dao.DistributorMapper";
        return new QueryDaoMybastisImplForDdd(entityClass, sqlMapper);
    }
    @Bean
    public QueryDao supplierQry() {
        String entityClass = "com.edev.trade.product.entity.Supplier";
        String sqlMapper = "com.edev.trade.query.dao.SupplierMapper";
        return new QueryDaoMybastisImplForDdd(entityClass, sqlMapper);
    }
}
