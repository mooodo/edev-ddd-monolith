package com.edev.trade.order.service;

import com.edev.trade.order.entity.Order;

import java.util.Collection;
import java.util.List;

public interface OrderService {
    Long create(Order order);
    void modify(Order order);
    void delete(Long orderId);
    Order load(Long orderId);
    void saveAll(List<Order> orders);
    void deleteAll(List<Long> orderIds);
    Collection<Order> loadAll(List<Long> orderIds);
}
