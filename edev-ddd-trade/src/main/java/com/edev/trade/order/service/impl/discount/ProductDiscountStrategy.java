package com.edev.trade.order.service.impl.discount;

import com.edev.trade.order.entity.Discount;
import com.edev.trade.order.entity.Order;
import com.edev.trade.order.entity.OrderItem;
import com.edev.trade.order.entity.ProductDiscount;
import com.edev.trade.order.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDiscountStrategy implements DiscountStrategy {
    @Autowired
    private DiscountService discountService;
    @Override
    public void doDiscount(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        if(orderItems==null||orderItems.isEmpty()) return;
        orderItems.forEach(orderItem -> {
            ProductDiscount template = new ProductDiscount();
            template.setProductId(orderItem.getProductId());
            Discount discount = discountService.load(template);
            if(discount==null) return;
            Double amount = orderItem.getAmount() * discount.getDiscount();
            orderItem.setAmount(amount);
        });
    }
}
