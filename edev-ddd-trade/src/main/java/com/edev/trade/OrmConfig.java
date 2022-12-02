package com.edev.trade;

import com.edev.support.dao.BasicDao;
import com.edev.trade.authority.service.UserService;
import com.edev.trade.authority.service.impl.UserServiceImpl;
import com.edev.trade.customer.service.AccountService;
import com.edev.trade.customer.service.CustomerService;
import com.edev.trade.customer.service.JournalAccountService;
import com.edev.trade.customer.service.VipService;
import com.edev.trade.customer.service.impl.AccountServiceImpl;
import com.edev.trade.customer.service.impl.CustomerServiceImpl;
import com.edev.trade.customer.service.impl.JournalAccountServiceImpl;
import com.edev.trade.customer.service.impl.VipServiceImpl;
import com.edev.trade.inventory.service.InventoryService;
import com.edev.trade.inventory.service.impl.InventoryServiceImpl;
import com.edev.trade.order.service.OrderService;
import com.edev.trade.order.service.impl.OrderServiceImpl;
import com.edev.trade.product.service.ProductService;
import com.edev.trade.product.service.SupplierService;
import com.edev.trade.product.service.impl.ProductServiceImpl;
import com.edev.trade.product.service.impl.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public SupplierService supplier() {
        return new SupplierServiceImpl(repository);
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
}
