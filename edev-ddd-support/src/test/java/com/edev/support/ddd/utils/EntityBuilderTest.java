package com.edev.support.ddd.utils;

import com.edev.support.entity.Entity;
import com.edev.trade.TradeApplication;
import com.edev.trade.customer.entity.Customer;
import com.edev.trade.customer.entity.GoldenVip;
import com.edev.trade.customer.entity.Vip;
import com.edev.trade.order.entity.Discount;
import com.edev.trade.order.entity.VipDiscount;
import com.edev.trade.product.entity.Distributor;
import com.edev.trade.product.entity.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class EntityBuilderTest {
    @Test
    public void testCreateEntityByName() {
        String className = "com.edev.trade.customer.entity.Customer";
        Entity<Long> entity = (new EntityBuilder<Entity<Long>, Long>(className)).createEntity();
        assertThat(entity.getClass(), equalTo(Customer.class));
    }
    @Test
    public void testCreateEntityByClass() {
        Entity<?> entity = EntityBuilder.build(Customer.class);
        assertThat(entity.getClass(), equalTo(Customer.class));
    }
    @Test
    public void testCreateSimpleSubClass() {
        Entity<?> parent = EntityBuilder.build(Vip.class);
        assertThat(parent.getClass(), equalTo(Vip.class));

        Entity<?> child = EntityBuilder.build(GoldenVip.class);
        assertThat(child.getClass(), equalTo(GoldenVip.class));
        assertThat(child.getValue("vipType"), equalTo("golden"));
    }
    @Test
    public void testCreateUnionSubClass() {
        Entity<?> parent = EntityBuilder.build(Discount.class);
        assertThat(parent.getClass(), equalTo(Discount.class));

        Entity<?> child = EntityBuilder.build(VipDiscount.class);
        assertThat(child.getClass(), equalTo(VipDiscount.class));
        assertThat(child.getValue("discountType"), equalTo("vipDiscount"));
    }
    @Test
    public void testCreateJoinedSubClass() {
        Entity<?> parent = EntityBuilder.build(Supplier.class);
        assertThat(parent.getClass(), equalTo(Supplier.class));

        Entity<?> child = EntityBuilder.build(Distributor.class);
        assertThat(child.getClass(), equalTo(Distributor.class));
        assertThat(child.getValue("supplierType"), equalTo("distributor"));
    }
}
