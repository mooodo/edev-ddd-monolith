package com.edev.trade.order.service.impl;

import com.edev.support.dao.BasicDao;
import com.edev.support.exception.ValidException;
import com.edev.support.utils.DateUtils;
import com.edev.trade.order.entity.Order;
import com.edev.trade.order.entity.OrderItem;
import com.edev.trade.order.entity.Payment;
import com.edev.trade.order.service.DiscountService;
import com.edev.trade.order.service.OrderService;
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

    private void validOrder(Order order) {
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
        if(order==null||order.getOrderItems()==null) return;
        Double amount = 0D;
        for(OrderItem orderItem : order.getOrderItems()) {
            amount += orderItem.getAmount();
        }
        order.setAmount(amount);
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
        setAmount(order);
        discount(order);
        sumOfAmount(order);
        payoff(order);
        order.setOrderTime(DateUtils.getNow());
        return dao.insert(order);
    }

    @Override
    public void modify(Order order) {
        validOrder(order);
        setAmount(order);
        discount(order);
        sumOfAmount(order);
        payoff(order);
        order.setModifyTime(DateUtils.getNow());
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
            setAmount(order);
            discount(order);
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
