package com.edev.trade.order.service;

import com.edev.trade.order.entity.Discount;
import com.edev.trade.order.entity.Order;

public interface DiscountService {
    void create(Discount discount);
    void modify(Discount discount);
    void delete(Long discountId);
    void doDiscount(Order order);
    Discount load(Long discountId);
    Discount load(Discount template);
}
