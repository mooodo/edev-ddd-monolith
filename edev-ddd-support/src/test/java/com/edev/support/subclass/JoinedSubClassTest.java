package com.edev.support.subclass;

import com.edev.support.entity.Entity;
import com.edev.trade.TradeApplication;
import com.edev.trade.product.entity.Distributor;
import com.edev.trade.product.entity.SelfSupport;
import com.edev.trade.product.entity.Supplier;
import com.edev.trade.product.entity.Vendor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class JoinedSubClassTest {
    @Autowired
    private JoinedSubClass dao;
    @Test
    public void testCreateJoinedSubClass() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", 1L);
        json.put("supplierType", "vendor");
        Entity<Long> entity = dao.createEntityByJson(Supplier.class, json);
        assertThat(entity.getClass(), equalTo(Vendor.class));
    }
    @Test
    public void testSaveAndDeleteForParent() {
        Long id = 1L;
        Supplier supplier = Supplier.build()
                .setValues(id, "IBM", "distributor");
        dao.delete(id, Supplier.class);
        dao.insert(supplier);
        assertThat(dao.load(id, Supplier.class), equalTo(Distributor.build()
                .setValues(id,"IBM",null,null,null)));

        supplier.setName("Microsoft");
        dao.update(supplier);
        assertThat(dao.load(id, Supplier.class), equalTo(Distributor.build()
                .setValues(id,"Microsoft",null,null,null)));

        supplier.setSupplierType("vendor");
        dao.update(supplier);
        assertThat(dao.load(id, Supplier.class), equalTo(Vendor.build()
                .setValues(id,"Microsoft",null,null)));

        dao.delete(id, Supplier.class);
        assertNull(dao.load(id, Supplier.class));
    }
    @Test
    public void testSaveAndDeleteForChild() {
        Long id = 1L;
        Distributor distributor = Distributor.build()
                .setValues(id, "IBM", "International Business Machines Corporation", "USA", "IT Technology");
        dao.delete(id, Supplier.class);
        dao.insert(distributor);
        assertThat(dao.load(id, Supplier.class), equalTo(Distributor.build()
                .setValues(id,"IBM","International Business Machines Corporation", "USA", "IT Technology")));

        distributor.setName("Microsoft");
        distributor.setIntroduce("The Microsoft Technology Corporation");
        dao.update(distributor);
        assertThat(dao.load(id, Distributor.class), equalTo(Distributor.build()
                .setValues(id,"Microsoft","The Microsoft Technology Corporation", "USA", "IT Technology")));

        Vendor vendor = Vendor.build().setValues(id,"IBM Special Store(Beijing)",20001L,"Beijing");
        dao.update(vendor);
        assertThat(dao.load(id, Vendor.class), equalTo(Vendor.build()
                .setValues(id,"IBM Special Store(Beijing)",20001L,"Beijing")));

        dao.delete(id, Vendor.class);
        assertNull(dao.load(id, Vendor.class));
    }
    @Test
    public void testSaveAndDeleteForListParent() {
        Long id0 = 1L;
        Supplier supplier0 = Supplier.build()
                .setValues(id0, "IBM", "distributor");
        Long id1 = 2L;
        Supplier supplier1 = Supplier.build()
                .setValues(id1, "Microsoft", "distributor");
        Long id2 = 3L;
        Supplier supplier2 = Supplier.build()
                .setValues(id2, "IBM Special Store(Beijing)", "vendor");

        List<Long> ids = new ArrayList<>();
        ids.add(id0);
        ids.add(id1);
        ids.add(id2);

        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(supplier0);
        suppliers.add(supplier1);
        suppliers.add(supplier2);

        dao.deleteForList(ids, Supplier.class);
        dao.insertOrUpdateForList(suppliers);
        assertThat(dao.loadForList(ids, Supplier.class), hasItems(
                Distributor.build().setValues(id0,"IBM",null,null,null),
                Distributor.build().setValues(id1,"Microsoft",null,null,null),
                Vendor.build().setValues(id2,"IBM Special Store(Beijing)",null,null)
        ));

        supplier0.setName("IBM China");
        supplier1.setSupplierType("vendor");
        Long id3 = 4L;
        Supplier supplier3 = Supplier.build()
                .setValues(id3, "Microsoft Special Store(Beijing)", "vendor");

        ids = new ArrayList<>();
        ids.add(id0);
        ids.add(id1);
        ids.add(id3);

        suppliers = new ArrayList<>();
        suppliers.add(supplier0);
        suppliers.add(supplier1);
        suppliers.add(supplier3);

        dao.insertOrUpdateForList(suppliers);
        assertThat(dao.loadForList(ids, Supplier.class), hasItems(
                Distributor.build().setValues(id0,"IBM China",null,null,null),
                Vendor.build().setValues(id1,"Microsoft",null,null),
                Vendor.build().setValues(id3,"Microsoft Special Store(Beijing)",null,null)
        ));

        ids.add(id2);
        dao.deleteForList(ids, Supplier.class);
        assertTrue(dao.loadForList(ids, Supplier.class).isEmpty());
    }
    @Test
    public void testSaveAndDeleteForListChild() {
        Long id0 = 1L;
        Supplier supplier0 = Distributor.build()
                .setValues(id0, "IBM", "International Business Machines Corporation", "USA", "IT Technology");
        Long id1 = 2L;
        Supplier supplier1 = Distributor.build()
                .setValues(id1, "Microsoft", "The Microsoft Technology Corporation", "USA", "IT Technology");
        Long id2 = 3L;
        Supplier supplier2 = Vendor.build()
                .setValues(id2,"IBM Special Store(Beijing)",id0,"The Beijing Park, Beijing");

        List<Long> ids = new ArrayList<>();
        ids.add(id0);
        ids.add(id1);
        ids.add(id2);

        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(supplier0);
        suppliers.add(supplier1);
        suppliers.add(supplier2);

        dao.deleteForList(ids, Supplier.class);
        dao.insertOrUpdateForList(suppliers);
        assertThat(dao.loadForList(ids, Supplier.class), hasItems(
                supplier0, supplier1, supplier2
        ));

        supplier0.setName("IBM China");
        supplier1 = Vendor.build()
                .setValues(id1,"Microsoft Special Store(Beijing)",id1,"The Happy Street, Beijing");
        Long id3 = 4L;
        Supplier supplier3 = Vendor.build()
                .setValues(id3,"Microsoft Special Store(Shanghai)",id1,"The Chinese Mall, Shanghai");

        ids = new ArrayList<>();
        ids.add(id0);
        ids.add(id1);
        ids.add(id3);

        suppliers = new ArrayList<>();
        suppliers.add(supplier0);
        suppliers.add(supplier1);
        suppliers.add(supplier3);

        dao.insertOrUpdateForList(suppliers);
        assertThat(dao.loadForList(ids, Supplier.class), hasItems(
                supplier0, supplier1, supplier3
        ));

        ids.add(id2);
        dao.deleteForList(ids, Supplier.class);
        assertTrue(dao.loadForList(ids, Supplier.class).isEmpty());
    }
    @Test
    public void testSaveAndDeleteWithoutChild() {
        Long id = 1L;
        Supplier supplier = Supplier.build()
                .setValues(id, "JD", "selfSupport");
        dao.delete(id, Supplier.class);
        dao.insert(supplier);
        assertThat(dao.load(id, Supplier.class), equalTo(SelfSupport.build()
                .setValues(id,"JD")));

        supplier.setName("Microsoft");
        dao.update(supplier);
        assertThat(dao.load(id, Supplier.class), equalTo(SelfSupport.build()
                .setValues(id,"Microsoft")));

        supplier.setSupplierType("vendor");
        dao.update(supplier);
        assertThat(dao.load(id, Supplier.class), equalTo(Vendor.build()
                .setValues(id,"Microsoft",null,null)));

        dao.delete(id, Supplier.class);
        assertNull(dao.load(id, Supplier.class));
    }
    @Test
    public void testSaveAndDeleteWithoutChild0() {
        Long id = 1L;
        SelfSupport selfSupport = SelfSupport.build()
                .setValues(id, "JD");
        dao.delete(id, Supplier.class);
        dao.insert(selfSupport);
        assertThat(dao.load(id, Supplier.class), equalTo(SelfSupport.build()
                .setValues(id,"JD")));

        Vendor vendor = Vendor.build().setValues(id,"IBM Special Store(Beijing)",20001L,"Beijing");
        dao.update(vendor);
        assertThat(dao.load(id, Vendor.class), equalTo(Vendor.build()
                .setValues(id,"IBM Special Store(Beijing)",20001L,"Beijing")));

        dao.delete(id, Vendor.class);
        assertNull(dao.load(id, Vendor.class));
    }
}
