package com.edev.support.ddd.utils;

import com.edev.support.entity.Entity;
import com.edev.support.subclass.JoinedSubClass;
import com.edev.support.subclass.SimpleSubClass;
import com.edev.support.subclass.UnionSubClass;
import com.edev.trade.TradeApplication;
import com.edev.trade.authority.entity.User;
import com.edev.trade.customer.entity.Customer;
import com.edev.trade.customer.entity.GoldenVip;
import com.edev.trade.customer.entity.Vip;
import com.edev.trade.product.entity.Supplier;
import com.edev.trade.product.entity.Vendor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class EntityBuilderTest {
    @Autowired
    private SimpleSubClass simpleSubClass;
    @Autowired
    private UnionSubClass unionSubClass;
    @Autowired
    private JoinedSubClass joinedSubClass;
    @Test
    public void testCreateSimpleEntity() {
        EntityBuilder<Entity<Long>, Long> builder =
                new EntityBuilder<>("com.edev.trade.customer.entity.Customer");
        Entity<Long> entity = builder.createEntity();
        assertThat(entity.getClass(), equalTo(Customer.class));
    }
    @Test
    public void testCreateSimpleSubClass() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", 1L);
        json.put("vipType", "golden");
        Entity<Long> entity = simpleSubClass.createEntityByJson(Vip.class, json);
        assertThat(entity.getClass(), equalTo(GoldenVip.class));
    }
    @Test
    public void testCreateUnionSubClass() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", 1L);
        json.put("supplierType", "vendor");
        Entity<Long> entity = unionSubClass.createEntityByJson(Supplier.class, json);
        assertThat(entity.getClass(), equalTo(Vendor.class));
    }
    @Test
    public void testCreateJoinedSubClass() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", 1L);
        json.put("name","Romeo");
        json.put("userType","customer");
        Entity<Long> entity = joinedSubClass.createEntityByJson(User.class, json);
        assertThat(entity.getClass(), equalTo(com.edev.trade.authority.entity.Customer.class));
    }
}
