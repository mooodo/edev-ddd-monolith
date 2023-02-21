package com.edev.trade.order.service.impl.discount;

import com.edev.trade.customer.entity.Vip;
import com.edev.trade.customer.service.VipService;
import com.edev.trade.order.entity.Discount;
import com.edev.trade.order.entity.Order;
import com.edev.trade.order.entity.OrderItem;
import com.edev.trade.order.entity.VipDiscount;
import com.edev.trade.order.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VipDiscountStrategy implements DiscountStrategy {
    @Autowired
    private VipService vipService;
    @Autowired
    private DiscountService discountService;
    @Override
    public void doDiscount(Order order) {
        Vip vip = vipService.loadByCustomer(order.getCustomerId());
        if(vip==null) return;
        VipDiscount template = new VipDiscount();
        template.setVipType(vip.getVipType());
        Discount discount = discountService.load(template);
        if(discount==null) return;
        List<OrderItem> orderItems = order.getOrderItems();
        if(orderItems==null||orderItems.isEmpty()) return;
        orderItems.forEach(orderItem -> {
            Double amount = order.getAmount() * discount.getDiscount();
            orderItem.setAmount(amount);
        });
    }
}
