package com.edev.support.dsl;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DomainObjectFactoryTest {
    private DomainObjectFactory factory;

    @Before
    public void setUp() {
        factory = new DomainObjectFactory();
    }

    /**
     * 业务需求：
     * 1.将领域模型中领域对象及其相互关系的设计，以DSL的形式在软件系统中准确描述
     * 2.定义每个领域对象，并定义领域对象(class)与数据库表(tableName)的对应关系，用do标签表示
     * 3.定义领域对象中的每个属性，并定义属性(name)与数据库字段(column)的对应关系，用property标签表示
     * 4.在property标签中，isPrimaryKey=true代表该属性是主键
     * 5.定义领域对象所有与其它领域对象的关联关系，用join标签表示
     * 6.在join标签，joinKey代表关联字段，joinType代码关联类型，isAggregation=true代表这是聚合关系
     * 8.为每个关联对象单独写领域对象，详细描述它们的所有相关内容
     */
    @Test
    public void testLoadWithJoin () {
        factory.initFactory("classpath:entity/customer.xml");
        assertTrue(DomainObjectFactory.isExists("com.edev.trade.customer.entity.Customer"));
        DomainObject dObj = DomainObjectFactory.getDomainObject("com.edev.trade.customer.entity.Customer");
        assertThat(dObj.getClazz(), equalTo("com.edev.trade.customer.entity.Customer"));
        assertThat(dObj.getTable(), equalTo("t_customer"));
        assertThat(dObj.getProperties(), hasItems(new Property("id","id",true,false),
                new Property("name","name",false,false),
                new Property("gender","gender",false,false),
                new Property("birthdate","birthdate",false,false),
                new Property("identification","identification",false,false),
                new Property("phoneNumber","phone_number",false,false)
        ));
        assertThat(dObj.getJoins(), hasItems(
                new Join("addresses","customerId","oneToMany", true,
                        "com.edev.trade.customer.entity.Address")
        ));
        assertTrue(DomainObjectFactory.isExists("com.edev.trade.customer.entity.Address"));
    }

    /**
     * 业务需求：
     * 1.当领域对象存在继承关系时，将继承关系的父子关系全部存储在一张表中，用Simple方案
     * 2.在定义父类的领域对象时，subClassType=simple
     * 3.在父类领域对象中，有一个标识字段来标识每个子类，该字段isDiscriminator=true
     * 4.在父类领域对象中定义它的所有子类，用subclass标签表示
     * 5.subclass标签中，定义子类(class)与标识字段的值(value)之间的关系
     * 6.subclass标签中，用property标签定义该子类的每个个性化属性
     */
    @Test
    public void testLoadSimpleSubclass() {
        factory.initFactory("classpath:entity/customer.xml");
        DomainObject dObj = DomainObjectFactory.getDomainObject("com.edev.trade.customer.entity.Vip");
        assertThat(dObj.getSubClassType(), equalTo("simple"));
        assertThat(dObj.getTable(), equalTo("t_vip"));
        assertThat(dObj.getProperties(), hasItems(
                new Property("vipType", "vip_type", false, true)
        ));
        SubClass golden = new SubClass("com.edev.trade.customer.entity.GoldenVip", "golden");
        golden.setProperties(Collections.singletonList(new Property("cashback", "cashback", false, false)));
        SubClass silver = new SubClass("com.edev.trade.customer.entity.SilverVip", "silver");
        assertThat(dObj.getSubClasses(), hasItems(
                golden,silver
        ));
    }

    /**
     * 业务需求：
     * 1.当领域对象存在继承关系时，将继承关系的每个子类存一张表，用Union方案
     * 2.在定义父类的领域对象时，subClassType=union
     * 3.该方案没有父类的表，父类的所有字段都写入到各个子类中，因此父类不写tableName
     * 4.在父类领域对象中，有一个标识字段来标识每个子类，该字段isDiscriminator=true
     * 5.在父类领域对象中定义它的所有子类，用subclass标签表示
     * 6.subclass标签中，定义子类(class)与标识字段的值(value)之间的关系
     * 7.为每个子类单独写领域对象，详细描述它们的所有相关内容
     */
    @Test
    public void testLoadUnionSubclass() {
        factory.initFactory("classpath:entity/order.xml");
        DomainObject dObj = DomainObjectFactory.getDomainObject("com.edev.trade.order.entity.Discount");
        assertThat(dObj.getClazz(), equalTo("com.edev.trade.order.entity.Discount"));
        assertThat(dObj.getTable(), equalTo(""));
        assertThat(dObj.getSubClassType(), equalTo("union"));
        assertThat(dObj.getProperties(), hasItems(
                new Property("discountType", "discount_type", false, true)
        ));
        SubClass vipDiscount = new SubClass("com.edev.trade.order.entity.VipDiscount", "vipDiscount");
        SubClass productDiscount = new SubClass("com.edev.trade.order.entity.ProductDiscount", "productDiscount");
        assertThat(dObj.getSubClasses(), hasItems(vipDiscount,productDiscount));
        assertTrue(DomainObjectFactory.isExists("com.edev.trade.order.entity.VipDiscount"));
        assertTrue(DomainObjectFactory.isExists("com.edev.trade.order.entity.ProductDiscount"));
    }

    /**
     * 业务需求：
     * 1.当领域对象存在继承关系时，父类存父类的表，子类存子类的表，用Joined方案
     * 2.在定义父类的领域对象时，subClassType=joined, tableName=父类的表
     * 3.在父类领域对象中，有一个标识字段来标识每个子类，该字段isDiscriminator=true
     * 4.在父类领域对象中定义它的所有子类，用subclass标签表示
     * 5.subclass标签中，定义子类(class)与标识字段的值(value)之间的关系
     * 6.为每个子类单独写领域对象，详细描述它们的所有相关内容
     * 7.在每个子类的领域对象中，tableName=子类的表
     */
    @Test
    public void testLoadJoinedSubClass() {
        factory.initFactory("classpath:entity/product.xml");
        DomainObject dObj = DomainObjectFactory.getDomainObject("com.edev.trade.product.entity.Supplier");
        assertThat(dObj.getClazz(), equalTo("com.edev.trade.product.entity.Supplier"));
        assertThat(dObj.getTable(), equalTo("t_supplier"));
        assertThat(dObj.getSubClassType(), equalTo("joined"));
        assertThat(dObj.getProperties(), hasItems(
                new Property("id","id",true,false),
                new Property("name","name",false,false),
                new Property("supplierType","supplier_type",false,true)
        ));
        SubClass distributor = new SubClass("com.edev.trade.product.entity.Distributor", "distributor");
        SubClass vendor = new SubClass("com.edev.trade.product.entity.Vendor", "vendor");
        assertThat(dObj.getSubClasses(), hasItems(distributor, vendor));
        assertTrue(DomainObjectFactory.isExists("com.edev.trade.product.entity.Distributor"));
        assertTrue(DomainObjectFactory.isExists("com.edev.trade.product.entity.Vendor"));
    }

    /**
     * 业务需求：
     * 1.如果关联类型是一对一关系，joinType=oneToOne
     * 2.joinKey不填，因为该关系的关联方式是关系双方的主键值相同
     * 3.isAggregation: 是否是聚合关系
     * 4.class:被关联对象
     */
    @Test
    public void testJoinOneToOne() {
        factory.initFactory("classpath:entity/customer.xml");
        DomainObject dObj = DomainObjectFactory.getDomainObject("com.edev.trade.customer.entity.Vip");
        assertThat(dObj.getJoins(), hasItems(
                new Join("customer","","oneToOne",false,
                        "com.edev.trade.customer.entity.Customer")
        ));
    }

    /**
     * 业务需求：
     * 1.如果关联关系是多对一关系，joinType=manyToOne
     * 2.joinKey:哪个字段与被关联对象的主键关联
     * 3.class:被关联对象
     */
    @Test
    public void testJoinManyToOne() {
        factory.initFactory("classpath:entity/order.xml");
        DomainObject dObj = DomainObjectFactory.getDomainObject("com.edev.trade.order.entity.Order");
        assertThat(dObj.getProperties(),hasItems(
                new Property("customerId","customer_id",false,false)
        ));
        assertThat(dObj.getJoins(), hasItems(
                new Join("customer","customerId","manyToOne",false,
                        "com.edev.trade.order.entity.Customer")
        ));
    }

    /**
     * 业务需求：
     * 1.如果关联类型是一对多关系，joinType=oneToMany
     * 2.joinType:被关联对象中的哪个字段与本对象的主键关联
     * 3.isAggregation: 是否是聚合关系
     * 4.class:被关联对象
     */
    @Test
    public void testJoinOneToMany() {
        factory.initFactory("classpath:entity/order.xml");
        DomainObject order = DomainObjectFactory.getDomainObject("com.edev.trade.order.entity.Order");
        assertThat(order.getJoins(), hasItems(
                new Join("orderItems","orderId","oneToMany",true,
                        "com.edev.trade.order.entity.OrderItem")
        ));
        DomainObject orderItem = DomainObjectFactory.getDomainObject("com.edev.trade.order.entity.OrderItem");
        assertThat(orderItem.getProperties(), hasItems(
                new Property("orderId","order_id",false,false)
        ));
    }

    /**
     * 业务需求：
     * 1.如果关联类型是多对多关系，joinType=manyToMany
     * 2.多对多关系需要在中间增加一个关联类，将多对多关系变为2个一对多关系
     * 3.joinType: 关联类中的哪个字段与本对象的主键关联
     * 4.joinClass: 关联类
     * 5.joinClassKey: 关联类中的哪个字段与被关联对象的主键关联
     * 6.将关联类单独定义为一个领域对象
     * 7.多对多关系是唯一可以设置双向关联关系的关联类型
     */
    @Test
    public void testJoinManyToMany() {
        factory.initFactory("classpath:entity/user.xml");
        DomainObject role = DomainObjectFactory.getDomainObject("com.edev.trade.authority.entity.Role");
        assertThat(role.getJoins(),hasItems(
                new Join("authorities","roleId","manyToMany","authorityId",
                        "com.edev.trade.authority.entity.RoleGrantedAuthority",false,
                        "com.edev.trade.authority.entity.Authority")
        ));
        DomainObject roleGrantedAuthority = DomainObjectFactory.getDomainObject("com.edev.trade.authority.entity.RoleGrantedAuthority");
        assertThat(roleGrantedAuthority.getProperties(), hasItems(
                new Property("roleId","role_id",false,false),
                new Property("authorityId","authority_id",false,false)
        ));
        assertTrue(DomainObjectFactory.isExists("com.edev.trade.authority.entity.Authority"));
    }
}
