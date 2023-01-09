package com.edev.support.dao;

import com.edev.support.dao.impl.BasicDaoMybatisImpl;
import com.edev.support.utils.DateUtils;
import com.edev.trade.TradeApplication;
import com.edev.trade.order.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class BasicDaoTest {
    @Autowired
    private BasicDaoMybatisImpl dao;
    @Test
    public void testSaveAndDelete() {
        Long id = 1L;
        Order order = new Order(id,10001L,1000100L,5000D);
        dao.delete(id, Order.class);
        dao.insert(order);
        assertThat(dao.load(id, Order.class), equalTo(order));

        order.setCustomerId(10002L);
        order.setAddressId(1000200L);
        order.setAmount(6000D);
        dao.update(order);
        assertThat(dao.load(id, Order.class), equalTo(order));

        dao.delete(id, Order.class);
        assertNull(dao.load(id, Order.class));
    }
    @Test
    public void testInsertOrUpdate() {
        Long id = 1L;
        Order order = new Order(id,10001L,1000100L,5000D);
        dao.delete(order);
        dao.insertOrUpdate(order);
        assertThat(dao.load(id, Order.class), equalTo(order));

        order.setCustomerId(10002L);
        order.setAddressId(1000200L);
        order.setAmount(6000D);
        dao.insertOrUpdate(order);
        assertThat(dao.load(id, Order.class), equalTo(order));

        Order template = new Order();
        template.setId(id);
        dao.delete(template);
        assertNull(dao.load(id, Order.class));
    }
    @Test
    public void testSaveAndDeleteForList() {
        Date orderTime = DateUtils.getDate("2020-01-01","yyyy-MM-dd");
        Long id0 = 1L;
        Order order0 = new Order(id0,10001L,1000100L,5000D, orderTime,"CREATE");
        Long id1 = 2L;
        Order order1 = new Order(id1,10002L,1000200L,6000D, orderTime,"CREATE");
        List<Long> ids = new ArrayList<>();
        ids.add(id0);
        ids.add(id1);
        List<Order> orders = new ArrayList<>();
        orders.add(order0);
        orders.add(order1);

        dao.deleteForList(ids, Order.class);
        dao.insertOrUpdateForList(orders);
        assertThat(dao.loadForList(ids, Order.class), hasItems(order0, order1));
        dao.deleteForList(ids, Order.class);
        assertTrue(dao.loadForList(ids, Order.class).isEmpty());
    }
}
