package com.edev.trade.order.service;

import com.edev.trade.order.entity.Order;

public interface OrderAggService {
    /**
     * place an order
     * @param order the order, include its account and order items.
     * @return the order id
     */
    Long placeOrder(Order order);

    /**
     * return the goods for an order
     * @param orderId the order id
     */
    void returnGoods(Long orderId);
}
