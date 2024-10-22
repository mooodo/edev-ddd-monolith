package com.edev.trade.order.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.support.utils.DateUtils;
import com.edev.trade.order.entity.Order;
import com.edev.trade.order.entity.OrderItem;
import com.edev.trade.order.entity.Payment;
import com.edev.trade.order.service.DiscountService;
import com.edev.trade.order.service.OrderService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final BasicDao dao;
    public OrderServiceImpl(BasicDao dao) {
        this.dao = dao;
    }
    @Autowired
    private DiscountService discountService;

    private void validOrder(@NonNull Order order) {
        if(order.getId()==null) throw new ValidException("The id is null!");
        if(order.getCustomerId()==null) throw new ValidException("The customerId is null");
        if(order.getAddressId()==null) throw new ValidException("The addressId is null");
    }

    private void setAmount(Order order) {
        if(order==null||order.getOrderItems()==null) return;
        order.getOrderItems().forEach(orderItem -> {
            if(orderItem.getAmount()==null) {
                Double amount = orderItem.getPrice() * orderItem.getQuantity();
                orderItem.setAmount(amount);
            }
        });
    }

    private void discount(Order order) {
        if(order!=null) discountService.doDiscount(order);
    }

    private void sumOfAmount(Order order) {
        if(order==null||order.getOrderItems()==null||order.getOrderItems().isEmpty()) return;
        Double amount = 0D;
        for(OrderItem orderItem : order.getOrderItems()) {
            amount += orderItem.getAmount();
        }
        order.setAmount(amount);
    }

    private void payoff(@NonNull Order order) {
        Payment payment = order.getPayment();
        if(payment==null) return;
        payment.setId(order.getId());
        payment.setAmount(order.getAmount());
    }

    @Override
    public Long create(@NonNull Order order) {
        validOrder(order);
        setAmount(order);
        discount(order);
        sumOfAmount(order);
        payoff(order);
        order.setOrderTime(DateUtils.getNow());
        return dao.insert(order);
    }

    @Override
    public void modify(@NonNull Order order) {
        validOrder(order);
        setAmount(order);
        discount(order);
        sumOfAmount(order);
        payoff(order);
        order.setModifyTime(DateUtils.getNow());
        dao.update(order);
    }

    @Override
    public void delete(@NonNull Long orderId) {
        dao.delete(orderId, Order.class);
    }

    @Override
    public Order load(@NonNull Long orderId) {
        return dao.load(orderId, Order.class);
    }

    @Override
    public void saveAll(@NonNull List<Order> orders) {
        orders.forEach(order -> {
            validOrder(order);
            setAmount(order);
            discount(order);
            sumOfAmount(order);
            payoff(order);
        });
        dao.insertOrUpdateForList(orders);
    }

    @Override
    public void deleteAll(@NonNull List<Long> orderIds) {
        dao.deleteForList(orderIds, Order.class);
    }

    @Override
    public Collection<Order> loadAll(@NonNull List<Long> orderIds) {
        return dao.loadForList(orderIds, Order.class);
    }
}
