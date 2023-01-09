package com.edev.support.subclass;

import com.edev.support.entity.Entity;
import com.edev.trade.TradeApplication;
import com.edev.trade.order.entity.Discount;
import com.edev.trade.order.entity.ProductDiscount;
import com.edev.trade.order.entity.VipDiscount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class UnionSubClassTest {
    @Autowired
    private UnionSubClass dao;
    @Test
    public void testCreateUnionSubClass() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", 1L);
        json.put("discountType", "vipDiscount");
        Entity<Long> entity = dao.createEntityByJson(Discount.class, json);
        assertThat(entity.getClass(), equalTo(VipDiscount.class));
    }
    @Test
    public void testSaveAndDeleteForParent() {
        Long id = 1L;
        Discount discount = new Discount(id,"The Golden Vip Discount",null,null,0.75D,"vipDiscount");
        dao.delete(id, Discount.class);
        dao.insert(discount);
        assertThat(dao.load(id, Discount.class), equalTo(
                new VipDiscount(id,"The Golden Vip Discount",null,null,0.75D,null)
        ));

        discount.setDiscount(0.8D);
        dao.update(discount);
        assertThat(dao.load(id, Discount.class), equalTo(
                new VipDiscount(id,"The Golden Vip Discount",null,null,0.8D,null)
        ));

        discount.setName("The Product Discount");
        discount.setDiscountType("productDiscount");
        dao.update(discount);
        assertThat(dao.load(id, Discount.class), equalTo(
                new ProductDiscount(id,"The Product Discount",null,null,0.8D,null)
        ));

        Discount template = new Discount();
        template.setId(id);
        template.setDiscountType("productDiscount");
        dao.delete(template);
        assertNull(dao.load(id, Discount.class));
    }
    @Test
    public void testSaveAndDeleteForChild() {
        Long id = 1L;
        Discount discount = new VipDiscount(id,"The Golden Vip Discount",null,null,0.75D,"golden");
        dao.delete(id, Discount.class);
        dao.insert(discount);
        assertThat(dao.load(id, VipDiscount.class), equalTo(
                new VipDiscount(id,"The Golden Vip Discount",null,null,0.75D,"golden")
        ));

        discount.setDiscount(0.8D);
        dao.update(discount);
        assertThat(dao.load(id, VipDiscount.class), equalTo(
                new VipDiscount(id,"The Golden Vip Discount",null,null,0.8D,"golden")
        ));

        discount = new ProductDiscount(id,"The Apple iPhone Discount",null,null,0.9D,30001L);
        dao.update(discount);
        assertThat(dao.load(id, Discount.class), equalTo(
                new ProductDiscount(id,"The Apple iPhone Discount",null,null,0.9D,30001L)
        ));

        dao.delete(id, ProductDiscount.class);
        assertNull(dao.load(id, ProductDiscount.class));
    }
    @Test
    public void testSaveAndDeleteForListParent() {
        Long id0 = 1L;
        Discount discount0 = new Discount(id0,"The Golden Vip Discount",null,null,0.75D,"vipDiscount");
        Long id1 = 2L;
        Discount discount1 = new Discount(id1,"The Silver Vip Discount",null,null,0.9D,"vipDiscount");
        Long id2 = 3L;
        Discount discount2 = new Discount(id2,"The Product Discount",null,null,0.75D,"productDiscount");
        List<Long> ids = Arrays.asList(id0, id1, id2, 4L);
        List<Discount> discounts = Arrays.asList(discount0, discount1, discount2);

        dao.deleteForList(ids, Discount.class);
        dao.insertOrUpdateForList(discounts);
        assertThat(dao.loadForList(ids, Discount.class), hasItems(
                new VipDiscount(id0,"The Golden Vip Discount",null,null,0.75D,null),
                new VipDiscount(id1,"The Silver Vip Discount",null,null,0.9D,null),
                new ProductDiscount(id2,"The Product Discount",null,null,0.75D,null)
        ));

        discount0.setDiscount(0.8D);
        discount1.setName("The Apple iPad Discount");
        discount1.setDiscountType("productDiscount");
        Long id3 = 4L;
        Discount discount3 = new Discount(id3,"The Smart Phone Discount",null,null,0.75D,"productDiscount");
        ids = Arrays.asList(id0, id1, id3);
        discounts = Arrays.asList(discount0, discount1, discount3);
        dao.insertOrUpdateForList(discounts);
        assertThat(dao.loadForList(ids, Discount.class), hasItems(
                new VipDiscount(id0,"The Golden Vip Discount",null,null,0.8D,null),
                new ProductDiscount(id1,"The Apple iPad Discount",null,null,0.9D,null),
                new ProductDiscount(id3,"The Smart Phone Discount",null,null,0.75D,null)
        ));

        ids = Arrays.asList(id0, id1, id2, id3);
        dao.deleteForList(ids, Discount.class);
        assertTrue(dao.loadForList(ids, Discount.class).isEmpty());
    }
    @Test
    public void testSaveAndDeleteForListChild() {
        Long id0 = 1L;
        Discount discount0 = new VipDiscount(id0,"The Golden Vip Discount",null,null,0.75D,"golden");
        Long id1 = 2L;
        Discount discount1 = new VipDiscount(id1,"The Silver Vip Discount",null,null,0.9D,"silver");
        Long id2 = 3L;
        Discount discount2 = new ProductDiscount(id2,"The Kindle Discount",null,null,0.75D,30004L);
        List<Long> ids = Arrays.asList(id0, id1, id2, 4L);
        List<Discount> discounts = Arrays.asList(discount0, discount1, discount2);

        dao.deleteForList(ids, Discount.class);
        dao.insertOrUpdateForList(discounts);
        assertThat(dao.loadForList(ids, Discount.class), hasItems(
                new VipDiscount(id0,"The Golden Vip Discount",null,null,0.75D,"golden"),
                new VipDiscount(id1,"The Silver Vip Discount",null,null,0.9D,"silver"),
                new ProductDiscount(id2,"The Kindle Discount",null,null,0.75D,30004L)
        ));

        discount0.setDiscount(0.8D);
        discount1.setName("The Apple iPad Discount");
        discount1.setDiscountType("productDiscount");
        discount1 = new ProductDiscount(id1,"The Apple iPad Discount",null,null,0.9D,30002L);
        Long id3 = 4L;
        Discount discount3 = new ProductDiscount(id3,"The Smart Phone Discount",null,null,0.75D,30007L);
        ids = Arrays.asList(id0, id1, id3);
        discounts = Arrays.asList(discount0, discount1, discount3);
        dao.insertOrUpdateForList(discounts);
        assertThat(dao.loadForList(ids, Discount.class), hasItems(
                new VipDiscount(id0,"The Golden Vip Discount",null,null,0.8D,"golden"),
                new ProductDiscount(id1,"The Apple iPad Discount",null,null,0.9D,30002L),
                new ProductDiscount(id3,"The Smart Phone Discount",null,null,0.75D,30007L)
        ));

        ids = Arrays.asList(id0, id1, id2, id3);
        dao.deleteForList(ids, Discount.class);
        assertTrue(dao.loadForList(ids, Discount.class).isEmpty());
    }
}
