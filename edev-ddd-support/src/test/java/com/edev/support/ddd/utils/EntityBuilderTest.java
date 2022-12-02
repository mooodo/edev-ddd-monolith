package com.edev.support.ddd.utils;

import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.entity.Entity;
import com.edev.trade.customer.entity.Customer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class EntityBuilderTest {
    @BeforeClass
    public static void beforeClass() throws Exception {
        new DomainObjectFactory("classpath:entity/*.xml");
    }

    @Test
    public void testCreateSimpleEntity() {
        EntityBuilder<Entity<Long>, Long> builder =
                new EntityBuilder<>("com.edev.trade.customer.entity.Customer");
        Entity<Long> entity = builder.createEntity();
        assertThat(entity.getClass(), equalTo(Customer.class));
    }
}
