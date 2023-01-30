package com.edev.support.ddd;

import com.edev.support.dao.BasicDao;
import com.edev.trade.TradeApplication;
import com.edev.trade.authority.entity.Authority;
import com.edev.trade.authority.entity.User;
import com.edev.trade.order.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeApplication.class)
public class RepositoryTest {
    @Autowired
    @Qualifier("repository")
    private BasicDao dao;
    @Test
    public void testSaveAndDelete() {
        Long id = 1L;
        Order order = new Order(id,10001L,1000100L,null);
        Customer customer = new Customer(10001L,"李秋水","女",
                "510110197910012312","13388990123");
        order.setCustomer(customer);
        Address address = new Address(1000100L,10001L,"中国","湖北",
                "武汉","洪山区","珞瑜路726号","13300224466");
        order.setAddress(address);
        Long accountId = 1000901L;
        Payment payment = new Payment(order.getId(), accountId);
        order.setPayment(payment);
        OrderItem orderItem0 = new OrderItem(11L,id,30001L,1L,4000D,null);
        Product product0 = new Product(30001L,"Apple iPhone X 256GB 深空灰色 移动联通电信4G手机",4000D,
                "台",20004L,"手机","/static/img/product1.jpg",5600D,"自营");
        orderItem0.setProduct(product0);
        order.addOrderItem(orderItem0);
        OrderItem orderItem1 = new OrderItem(12L,id,30004L,2L,958D,null);
        Product product1 = new Product(30004L,"Kindle Paperwhite电纸书阅读器 电子书墨水屏 6英寸wifi 黑色",958D,
                "个",20002L,"电子书","/static/img/product4.jpg",999D,"秒杀");
        orderItem1.setProduct(product1);
        order.addOrderItem(orderItem1);

        dao.delete(id, Order.class);
        dao.insert(order);
        assertThat(dao.load(id, Order.class), equalTo(order));

        order.setCustomerId(10002L);
        dao.update(order);
        Order actual = dao.load(id, Order.class);
        Customer excepted = new Customer(10002L,"逍遥子","男","510110197607103322","13422584349");
        assertThat(actual.getCustomerId(), equalTo(10002L));
        assertThat(actual.getCustomer(),equalTo(excepted));

        dao.delete(id, Order.class);
        assertNull(dao.load(id, Order.class));
    }
    @Test
    public void testSaveAndDeleteForList() {
        Long id0 = 1L;
        Order order0 = new Order(id0,10001L,1000100L,null);
        Customer customer0 = new Customer(10001L,"李秋水","女",
                "510110197910012312","13388990123");
        order0.setCustomer(customer0);
        Address address0 = new Address(1000100L,10001L,"中国","湖北",
                "武汉","洪山区","珞瑜路726号","13300224466");
        order0.setAddress(address0);

        Long id1 = 2L;
        Order order1 = new Order(id1,10001L,1000100L,null);
        Customer customer1 = new Customer(10001L,"李秋水","女",
                "510110197910012312","13388990123");
        order1.setCustomer(customer1);
        Address address1 = new Address(1000100L,10001L,"中国","湖北",
                "武汉","洪山区","珞瑜路726号","13300224466");
        order1.setAddress(address1);
        OrderItem orderItem0 = new OrderItem(21L,id1,30001L,1L,4000D,null);
        Product product0 = new Product(30001L,"Apple iPhone X 256GB 深空灰色 移动联通电信4G手机",4000D,
                "台",20004L,"手机","/static/img/product1.jpg",5600D,"自营");
        orderItem0.setProduct(product0);
        order1.addOrderItem(orderItem0);
        OrderItem orderItem1 = new OrderItem(22L,id1,30004L,2L,958D,null);
        Product product1 = new Product(30004L,"Kindle Paperwhite电纸书阅读器 电子书墨水屏 6英寸wifi 黑色",958D,
                "个",20002L,"电子书","/static/img/product4.jpg",999D,"秒杀");
        orderItem1.setProduct(product1);
        order1.addOrderItem(orderItem1);

        List<Long> ids = Arrays.asList(id0,id1);
        List<Order> orders = Arrays.asList(order0,order1);
        dao.deleteForList(ids, Order.class);
        dao.insertOrUpdateForList(orders);
        assertThat("fail insert", dao.loadForList(ids, Order.class), hasItems(order0, order1));

        order0.setAmount(5000D);
        Customer customer2 = new Customer(10002L,"逍遥子","男","510110197607103322","13422584349");
        order0.setCustomerId(10002L);
        order0.setCustomer(customer2);

        orderItem0.setAmount(5000D);
        orderItem0.setProductId(30004L);
        orderItem0.setProduct(product1);

        OrderItem orderItem2 = new OrderItem(23L,id1,null,10L,300D,3000D);
        order1.setOrderItems(Arrays.asList(orderItem0, orderItem2));
        dao.insertOrUpdateForList(Arrays.asList(order0, order1));
        assertThat("fail update", dao.loadForList(ids, Order.class), hasItems(order0, order1));

        dao.deleteForList(ids, Order.class);
        assertTrue("fail delete", dao.loadForList(ids, Order.class).isEmpty());
    }
    @Test
    public void testSaveAndDeleteWithManyToMany() {
        Long id = 1L;
        User user = new User(id,"Johnwood","123");
        Authority authority0 = new Authority(50001L,"registerUser","/orm/user/register",true);
        Authority authority1 = new Authority(50002L,"modifyUser","/orm/user/modify",true);
        user.addAuthority(authority0);
        user.addAuthority(authority1);

        dao.delete(id, User.class);
        dao.insert(user);
        assertThat(dao.load(id, User.class), equalTo(user));

        user.setName("Mary");
        dao.update(user);
        assertThat(dao.load(id, User.class), equalTo(user));

        Authority authority2 = new Authority(50003L,"removeUser","/orm/user/remove",true);
        List<Authority> authorities = Arrays.asList(authority0, authority2);
        user.setAuthorities(authorities);
        dao.update(user);
        assertThat(user.getAuthorities(), hasItems(authority0, authority2));

        dao.delete(id, User.class);
        assertNull(dao.load(id, User.class));
    }
    @Test
    public void testSaveAndDeleteWithManyToManyForList() {
        Long id0 = 1L;
        User user0 = new User(id0,"Johnwood","123");
        Long id1 = 2L;
        User user1 = new User(id1,"Mary","123");
        Authority authority0 = new Authority(50001L,"registerUser","/orm/user/register",true);
        Authority authority1 = new Authority(50002L,"modifyUser","/orm/user/modify",true);
        user1.addAuthority(authority0);
        user1.addAuthority(authority1);

        List<Long> ids = Arrays.asList(id0, id1);
        List<User> users = Arrays.asList(user0, user1);
        dao.deleteForList(ids, User.class);
        dao.insertOrUpdateForList(users);
        assertThat(dao.loadForList(ids, User.class), hasItems(user0, user1));

        user0.setName("Johnwood");
        Authority authority2 = new Authority(50003L,"removeUser","/orm/user/remove",true);
        user1.setAuthorities(Arrays.asList(authority0, authority2));
        dao.insertOrUpdateForList(Arrays.asList(user0, user1));
        assertThat(dao.loadForList(ids, User.class), hasItems(user0, user1));

        dao.deleteForList(ids, User.class);
        assertTrue(dao.loadForList(ids, User.class).isEmpty());
    }
}
