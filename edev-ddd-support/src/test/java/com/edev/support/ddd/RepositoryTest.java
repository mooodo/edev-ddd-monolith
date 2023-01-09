package com.edev.support.ddd;

import com.edev.support.dao.BasicDao;
import com.edev.trade.TradeApplication;
import com.edev.trade.order.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        OrderItem orderItem0 = new OrderItem(1L,id,30001L,1L,4000D,null);
        Product product0 = new Product(30001L,"Apple iPhone X 256GB 深空灰色 移动联通电信4G手机",4000D,
                "台",20004L,"手机","/static/img/product1.jpg",5600D,"自营");
        orderItem0.setProduct(product0);
        order.addOrderItem(orderItem0);
        OrderItem orderItem1 = new OrderItem(2L,id,30004L,2L,958D,null);
        Product product1 = new Product(30004L,"Kindle Paperwhite电纸书阅读器 电子书墨水屏 6英寸wifi 黑色",958D,
                "个",20002L,"电子书","/static/img/product4.jpg",999D,"秒杀");
        orderItem1.setProduct(product1);
        order.addOrderItem(orderItem1);

        dao.delete(id, Order.class);
        dao.insert(order);
        assertThat(dao.load(id, Order.class), equalTo(order));
    }
}
