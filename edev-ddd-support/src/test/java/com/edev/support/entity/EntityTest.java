package com.edev.support.entity;

import com.edev.support.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class EntityTest {
    @Test
    public void testSimpleEntity() {
        @EqualsAndHashCode(callSuper = true)
        @Data
        class Customer extends Entity<Long> {
            private Long id;
            private String name;
            private Date birthdate;
            private String available;
            public Customer(Long id, String name, Date birthdate, Boolean available) {
                this.id = id;
                this.name = name;
                this.birthdate = birthdate;
                setAvailable(available);
            }
            public Boolean getAvailable() {
                return available.equals("true");
            }
            public void setAvailable(Boolean available) {
                this.available = available ? "true":"false";
            }
        }
        Date date = DateUtils.getDate("2020-01-01", "yyyy-MM-dd");
        Customer customer0 = new Customer(1L, "John", date, true);
        Customer customer1 = new Customer(1L, "John", date, true);
        //test equals and toString
        assertThat(customer0, equalTo(customer1));
        //assertThat(customer0.toString(), equalTo("{id:1, name:John, birthdate:Wed Jan 01 00:00:00 CST 2020, available:true}"));

        //test getValue
        assertThat(customer0.getValue("id"), equalTo(1L));
        assertThat(customer0.getValue("name"), equalTo("John"));
        assertThat(customer0.getValue("birthdate"), equalTo(date));
        assertThat(customer0.getValue("available"), equalTo("true"));

        //test getValueByMethod
        assertThat(customer0.getValueByMethod("id"), equalTo(1L));
        assertThat(customer0.getValueByMethod("name"), equalTo("John"));
        assertThat(customer0.getValueByMethod("birthdate"), equalTo(date));
        assertThat(customer0.getValueByMethod("available"),equalTo(true));

        //test setValue
        customer0.setValue("id", 2L);
        assertThat(customer0.getValue("id"), equalTo(2L));

        //test setValue
        customer0.setValueByMethod("id", 3L);
        assertThat(customer0.getValue("id"), equalTo(3L));
        customer0.setValueByMethod("available", false);
        assertThat(customer0.getValue("available"), equalTo("false"));

        //test clone
        Object clone = customer0.clone();
        assertThat(clone, equalTo(customer0));

        //test getFields
        Field[] fields = customer0.findAllFields();
        assertThat(fields[0].getName(), equalTo("id"));
        assertThat(fields[1].getName(), equalTo("name"));
        assertThat(fields[2].getName(), equalTo("birthdate"));
        assertThat(fields[3].getName(), equalTo("available"));
    }

    @Test
    public void testSubClass() {
        @Data
        @EqualsAndHashCode(callSuper = true)
        class Customer extends Entity<Long> {
            private Long id;
            private String name;
            private Date birthdate;
            public Customer() {}
            public Customer(Long id, String name, Date birthdate) {
                this.id = id;
                this.name = name;
                this.birthdate = birthdate;
            }
        }
        @EqualsAndHashCode(callSuper = true)
        @Data
        class Vip extends Customer {
            private Integer coin;
            public Vip(Long id, String name, Date birthdate, Integer coin) {
                super(id, name, birthdate);
                this.coin = coin;
            }
        }
        Date date = DateUtils.getDate("2020-01-01", "yyyy-MM-dd");
        Vip vip0 = new Vip(1L, "John", date, 100);
        Vip vip1 = new Vip(1L, "John", date, 100);
        //test equals and toString
        assertThat(vip0, equalTo(vip1));
        //assertThat(vip0.toString(), equalTo("{id:1, name:John, birthdate:Wed Jan 01 00:00:00 CST 2020, coin:100}"));

        //test getValue
        assertThat(vip0.getValue("id"), equalTo(1L));
        assertThat(vip0.getValue("name"), equalTo("John"));
        assertThat(vip0.getValue("birthdate"), equalTo(date));

        //test getValueByMethod
        assertThat(vip0.getValueByMethod("id"), equalTo(1L));
        assertThat(vip0.getValueByMethod("name"), equalTo("John"));
        assertThat(vip0.getValueByMethod("birthdate"), equalTo(date));

        //test setValue
        vip0.setValue("id", 2L);
        assertThat(vip0.getValue("id"), equalTo(2L));
        vip0.setValue("coin", 200);
        assertThat(vip0.getValue("coin"), equalTo(200));

        //test setValueByMethod
        //test setValue
        vip0.setValueByMethod("id", 3L);
        assertThat(vip0.getValue("id"), equalTo(3L));
        vip0.setValueByMethod("coin", 300);
        assertThat(vip0.getValue("coin"), equalTo(300));

        //test clone
        Object clone = vip0.clone();
        assertThat(clone, equalTo(vip0));

        //test getFields
        Field[] fields = vip0.findAllFields();
        assertThat(fields[0].getName(), equalTo("id"));
        assertThat(fields[1].getName(), equalTo("name"));
        assertThat(fields[2].getName(), equalTo("birthdate"));
        assertThat(fields[3].getName(), equalTo("coin"));
    }
}
