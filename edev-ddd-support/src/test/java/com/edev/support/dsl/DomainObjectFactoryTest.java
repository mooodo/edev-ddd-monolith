package com.edev.support.dsl;

import com.edev.trade.customer.entity.Customer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DomainObjectFactoryTest {
    @Test
    public void testLoadWithJoin () {
        DomainObjectFactory factory = new DomainObjectFactory();
        factory.initFactory("classpath:entity/customer.xml");
        assertTrue(DomainObjectFactory.isExists("com.edev.trade.customer.entity.Customer"));
        DomainObject dObj = DomainObjectFactory.getDomainObject("com.edev.trade.customer.entity.Customer");
        assertThat(dObj.getClazz(), equalTo("com.edev.trade.customer.entity.Customer"));
        assertThat(dObj.getTable(), equalTo("Customer"));
        assertThat(dObj.getProperties(), hasItems(new Property("id","id",true,false),
                new Property("name","name",false,false),
                new Property("gender","gender",false,false),
                new Property("birthdate","birthdate",false,false),
                new Property("identification","identification",false,false),
                new Property("phoneNumber","phone_number",false,false)
        ));
        assertThat(dObj.getJoins(), hasItems(
                new Join("addresses","customer_id","oneToMany", true, "com.edev.trade.customer.entity.Address")
        ));
    }
    @Test
    public void testLoadWithRef() {
        DomainObjectFactory factory = new DomainObjectFactory();
        factory.initFactory("classpath:entity/product.xml");
        assertTrue(DomainObjectFactory.isExists("com.edev.trade.product.entity.Product"));
        DomainObject dObj = DomainObjectFactory.getDomainObject("com.edev.trade.product.entity.Product");
        assertThat(dObj.getClazz(), equalTo("com.edev.trade.product.entity.Product"));
        assertThat(dObj.getTable(), equalTo("Product"));
        assertThat(dObj.getProperties(), hasItems(new Property("id","id",true,false),
                new Property("name","name",false,false)
        ));
        assertThat(dObj.getRefs(), hasItems(
                new Ref("supplier","supplier_id","manyToOne",
                        "com.edev.product.service.SupplierService","loadSupplier","loadSuppliers")
        ));
    }
    @Test
    public void testLoadSimpleSubclass() {
        DomainObjectFactory factory = new DomainObjectFactory();
        factory.initFactory("classpath:entity/customer.xml");
        DomainObject dObj = DomainObjectFactory.getDomainObject("com.edev.trade.customer.entity.Vip");
        assertThat(dObj.getSubClassType(), equalTo("simple"));
        assertThat(dObj.getProperties(), hasItems(new Property("vipType", "vip_type", false, true)));
        assertThat(dObj.getSubClasses(), hasItems(
                new SubClass("com.edev.trade.customer.entity.GoldenVip", "golden"),
                new SubClass("com.edev.trade.customer.entity.SilverVip", "silver")
        ));
    }
    @Test
    public void testLoadUnionSubclass() {
        DomainObjectFactory factory = new DomainObjectFactory();
        factory.initFactory("classpath:entity/product.xml");
        DomainObject dObj = DomainObjectFactory.getDomainObject("com.edev.trade.product.entity.Supplier");
        assertThat(dObj.getClazz(), equalTo("com.edev.trade.product.entity.Supplier"));
        assertThat(dObj.getTable(), equalTo(""));
        assertThat(dObj.getSubClassType(), equalTo("union"));
        assertThat(dObj.getProperties(), hasItems(new Property("supplierType", "supplier_type", false, true)));
        SubClass distributor = new SubClass("com.edev.trade.product.entity.Distributor", "distributor");
        SubClass vendor = new SubClass("com.edev.trade.product.entity.Vendor", "vendor");
        assertThat(dObj.getSubClasses(), hasItems(distributor,vendor));
    }
    @Test
    public void testLoadJoinedSubClass() {
        DomainObjectFactory factory = new DomainObjectFactory();
        factory.initFactory("classpath:entity/user.xml");
        DomainObject dObj = DomainObjectFactory.getDomainObject("com.edev.trade.authority.entity.User");
        assertThat(dObj.getClazz(), equalTo("com.edev.trade.authority.entity.User"));
        assertThat(dObj.getTable(), equalTo("User"));
        assertThat(dObj.getSubClassType(), equalTo("joined"));
        assertThat(dObj.getProperties(), hasItems(
                new Property("id","id",true,false),
                new Property("name","name",false,false),
                new Property("password","password",false,false),
                new Property("userType","user_type",false,true)
        ));
        SubClass customer = new SubClass("com.edev.trade.customer.entity.Customer", "customer");
        SubClass supplier = new SubClass("com.edev.trade.product.entity.Supplier", "supplier");
        assertThat(dObj.getSubClasses(), hasItems(customer, supplier));
    }
    @Test
    public void testGetByClass() {
        DomainObjectFactory factory = new DomainObjectFactory();
        factory.initFactory("classpath:entity/customer.xml");
        assertTrue(DomainObjectFactory.isExists(Customer.class));
        DomainObject dObj = DomainObjectFactory.getDomainObject(Customer.class);
        assertThat(dObj.getClazz(), equalTo("com.edev.trade.customer.entity.Customer"));
    }
}
