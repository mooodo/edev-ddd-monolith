package com.edev.support.query;

import com.edev.support.dao.BasicDao;
import com.edev.support.dao.QueryDao;
import com.edev.support.ddd.AutofillQueryServiceImpl;
import com.edev.support.entity.ResultSet;
import com.edev.support.query.impl.QueryServiceImpl;
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

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class QueryServiceTest {
    @Autowired @Qualifier("customerQryService")
    private QueryService customerQryService;
    @Test
    public void testQueryCustomer() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("size", 20);
        ResultSet resultSet = customerQryService.query(params);
        assertThat(resultSet.getData().iterator().next(), instanceOf(Customer.class));
        assertThat(resultSet.getPage(), equalTo(0));
        assertThat(resultSet.getSize(), equalTo(20));

        Customer customer = (Customer) resultSet.getData().iterator().next();
        assertNull(customer.getAddresses());
    }
    @Autowired @Qualifier("orderQryService")
    private QueryService orderQryService;
    @Test
    public void testQueryOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("size", 20);
        ResultSet resultSet = orderQryService.query(params);
        assertThat(resultSet.getData().iterator().next(), instanceOf(Map.class));
        assertThat(resultSet.getPage(), equalTo(0));
        assertThat(resultSet.getSize(), equalTo(20));
    }
    @Autowired @Qualifier("customerAutofillQryService")
    private QueryService customerAutofillQryService;
    @Test
    public void testAutofillQueryCustomer() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("size", 20);
        ResultSet resultSet = customerAutofillQryService.query(params);
        assertThat(resultSet.getData().iterator().next(), instanceOf(Customer.class));
        assertThat(resultSet.getPage(), equalTo(0));
        assertThat(resultSet.getSize(), equalTo(20));

        Customer customer = (Customer) resultSet.getData().iterator().next();
        assertNotNull(customer.getAddresses());
    }
    @Autowired @Qualifier("orderQryServiceForDdd")
    private QueryService orderQryServiceForDdd;
    @Test
    public void testQueryOrderForDdd() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("size", 20);
        ResultSet resultSet = orderQryServiceForDdd.query(params);
        assertThat(resultSet.getData().iterator().next(), instanceOf(Order.class));
        assertThat(resultSet.getPage(), equalTo(0));
        assertThat(resultSet.getSize(), equalTo(20));

        Order order = (Order) resultSet.getData().iterator().next();
        assertNull(order.getCustomer());
        assertNull(order.getAddress());
        assertNull(order.getPayment());
        assertNull(order.getOrderItems());
    }
    @Autowired @Qualifier("orderAutofillQryServiceForDdd")
    private QueryService orderAutofillQryServiceForDdd;
    @Test
    public void testAutofillQueryOrderForDdd() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("size", 20);
        ResultSet resultSet = orderAutofillQryServiceForDdd.query(params);
        assertThat(resultSet.getData().iterator().next(), instanceOf(Order.class));
        assertThat(resultSet.getPage(), equalTo(0));
        assertThat(resultSet.getSize(), equalTo(20));

        Order order = (Order) resultSet.getData().iterator().next();
        assertNotNull(order.getCustomer());
        assertNotNull(order.getAddress());
        assertNotNull(order.getPayment());
        assertNotNull(order.getOrderItems());
    }
    @Autowired @Qualifier("distributorQryService")
    private QueryService distributorQryService;
    @Test
    public void testQueryDistributor() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("size", 20);
        ResultSet resultSet = distributorQryService.query(params);
        assertThat(resultSet.getData().iterator().next(), instanceOf(Distributor.class));
        assertThat(resultSet.getPage(), equalTo(0));
        assertThat(resultSet.getSize(), equalTo(20));

        Distributor distributor = (Distributor) resultSet.getData().iterator().next();
        assertNotNull(distributor.getVendors());
    }
    @Autowired @Qualifier("supplierQryService")
    private QueryService supplierQryService;
    @Test
    public void testQuerySupplier() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("size", 20);
        ResultSet resultSet = supplierQryService.query(params);
        assertThat(resultSet.getData().iterator().next(), instanceOf(Supplier.class));
        assertThat(resultSet.getPage(), equalTo(0));
        assertThat(resultSet.getSize(), equalTo(20));

        Distributor distributor = (Distributor) resultSet.getData().iterator().next();
        assertNotNull(distributor.getVendors());
    }
}

@Configuration
class QryServiceConfig {
    @Autowired @Qualifier("customerQry")
    private QueryDao customerQry;
    @Bean
    public QueryService customerQryService() {
        return new QueryServiceImpl(customerQry);
    }
    @Autowired @Qualifier("orderQry")
    private QueryDao orderQry;
    @Bean
    public QueryService orderQryService() {
        return new QueryServiceImpl(orderQry);
    }
    @Autowired @Qualifier("basicDao")
    private BasicDao dao;
    @Bean
    public QueryService customerAutofillQryService() {
        return new AutofillQueryServiceImpl(customerQry, dao);
    }
    @Autowired @Qualifier("orderQryForDdd")
    private QueryDao orderQryForDdd;
    @Bean
    public QueryService orderQryServiceForDdd() {
        return new QueryServiceImpl(orderQryForDdd);
    }
    @Bean
    public QueryService orderAutofillQryServiceForDdd() {
        return new AutofillQueryServiceImpl(orderQryForDdd, dao);
    }
    @Autowired @Qualifier("distributorQry")
    private QueryDao distributorQry;
    @Bean
    public QueryService distributorQryService() {
        return new AutofillQueryServiceImpl(distributorQry, dao);
    }
    @Autowired @Qualifier("supplierQry")
    private QueryDao supplierQry;
    @Bean
    public QueryService supplierQryService() {
        return new AutofillQueryServiceImpl(supplierQry, dao);
    }
}