package com.edev.trade.order.service;

import com.edev.trade.order.entity.Discount;
import com.edev.trade.order.entity.Order;

public interface DiscountService {
    Long create(Discount discount);
    void modify(Discount discount);
    void delete(Discount template);
    void deleteById(Long discountId);
    void doDiscount(Order order);
    Discount loadById(Long discountId);
    Discount load(Discount template);
}
