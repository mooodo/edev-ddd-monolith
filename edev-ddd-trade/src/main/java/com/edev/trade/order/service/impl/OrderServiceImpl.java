package com.edev.trade.order.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.trade.customer.service.VipService;
import com.edev.trade.order.entity.Order;
import com.edev.trade.order.entity.OrderItem;
import com.edev.trade.order.entity.Payment;
import com.edev.trade.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Autowired
    private VipService vipService;
    private final BasicDao dao;
    public OrderServiceImpl(BasicDao dao) {
        this.dao = dao;
    }

    private void validOrder(Order order) {
        if(order.getId()==null) throw new ValidException("The id is null!");
        if(order.getCustomerId()==null) throw new ValidException("The customerId is null");
        if(order.getAddressId()==null) throw new ValidException("The addressId is null");
    }

    private void sumOfAmount(Order order) {
        if(order.getOrderItems()==null||order.getOrderItems().isEmpty()) return;
        Double discount = vipService.discount(order.getCustomerId());
        Double amount = 0D;
        for(OrderItem orderItem : order.getOrderItems()) {
            setAmount(orderItem);
            vipDiscount(orderItem, discount);
            amount += orderItem.getAmount();
        }
        order.setAmount(amount);
    }

    private void vipDiscount(OrderItem orderItem, Double discount) {
        Double amount = orderItem.getAmount() * discount;
        orderItem.setAmount(amount);
    }

    private void setAmount(OrderItem orderItem) {
        Double amount = orderItem.getAmount();
        if(amount==null) amount = orderItem.getPrice() * orderItem.getQuantity();
        orderItem.setAmount(amount);
    }

    private void payoff(Order order) {
        Payment payment = order.getPayment();
        if(payment==null) return;
        payment.setAmount(order.getAmount());
        payment.setStatus("payoff");
    }

    @Override
    public Long create(Order order) {
        validOrder(order);
        sumOfAmount(order);
        payoff(order);
        return dao.insert(order);
    }

    @Override
    public void modify(Order order) {
        validOrder(order);
        sumOfAmount(order);
        payoff(order);
        dao.update(order);
    }

    @Override
    public void delete(Long orderId) {
        dao.delete(orderId, Order.class);
    }

    @Override
    public Order load(Long orderId) {
        return dao.load(orderId, Order.class);
    }

    @Override
    public void saveAll(List<Order> orders) {
        orders.forEach(order -> {
            validOrder(order);
            sumOfAmount(order);
            payoff(order);
        });
        dao.insertOrUpdateForList(orders);
    }

    @Override
    public void deleteAll(List<Long> orderIds) {
        dao.deleteForList(orderIds, Order.class);
    }

    @Override
    public Collection<Order> loadAll(List<Long> orderIds) {
        return dao.loadForList(orderIds, Order.class);
    }
}
