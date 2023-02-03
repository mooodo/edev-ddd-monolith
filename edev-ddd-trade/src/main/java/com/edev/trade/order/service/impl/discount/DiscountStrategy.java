package com.edev.trade.order.service.impl.discount;

import com.edev.trade.order.entity.Order;

public interface DiscountStrategy {
    void doDiscount(Order order);
}
