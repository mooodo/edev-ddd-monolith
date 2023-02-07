package com.edev.trade;

import com.edev.support.dao.BasicDao;
import com.edev.trade.authority.service.UserService;
import com.edev.trade.authority.service.impl.UserServiceImpl;
import com.edev.trade.customer.service.*;
import com.edev.trade.customer.service.impl.*;
import com.edev.trade.inventory.service.InventoryService;
import com.edev.trade.inventory.service.impl.InventoryServiceImpl;
import com.edev.trade.order.service.DiscountService;
import com.edev.trade.order.service.OrderService;
import com.edev.trade.order.service.impl.DiscountServiceImpl;
import com.edev.trade.order.service.impl.OrderServiceImpl;
import com.edev.trade.order.service.impl.discount.DiscountStrategy;
import com.edev.trade.product.service.ProductService;
import com.edev.trade.product.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OrmConfig {
    @Autowired @Qualifier("basicDao")
    private BasicDao basicDao;
    @Autowired @Qualifier("repository")
    private BasicDao repository;
    @Bean
    public CustomerService customer() {
        return new CustomerServiceImpl(repository);
    }
    @Bean
    public VipService vip() {
        return new VipServiceImpl(repository);
    }
    @Bean
    public OrderService order() {
        return new OrderServiceImpl(repository);
    }
    @Bean
    public ProductService product() {
        return new ProductServiceImpl(repository);
    }
    @Bean
    public UserService user() {
        return new UserServiceImpl(repository);
    }
    @Bean
    public InventoryService inventory() {
        return new InventoryServiceImpl(repository);
    }
    @Bean
    public AccountService account() {
        return new AccountServiceImpl(repository);
    }
    @Bean
    public JournalAccountService journalAccount() {
        return new JournalAccountServiceImpl(basicDao);
    }
    @Autowired @Qualifier("vipDiscountStrategy")
    private DiscountStrategy vipDiscountStrategy;
    @Autowired @Qualifier("productDiscountStrategy")
    private DiscountStrategy productDiscountStrategy;
    @Bean
    public DiscountService discount() {
        List<DiscountStrategy> strategies = new ArrayList<>();
        //strategies.add(vipDiscountStrategy);
        //strategies.add(productDiscountStrategy);
        return new DiscountServiceImpl(basicDao, strategies);
    }
}
