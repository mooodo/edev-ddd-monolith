package com.edev.trade.order.service.impl;

import com.edev.support.ddd.NullEntityException;
import com.edev.support.exception.ValidException;
import com.edev.trade.customer.service.AccountService;
import com.edev.trade.inventory.service.InventoryService;
import com.edev.trade.order.entity.Order;
import com.edev.trade.order.service.OrderService;
import com.edev.trade.order.service.OrderAggService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("orderAgg")
public class OrderAggServiceImpl implements OrderAggService {
    private final static Log log = LogFactory.getLog(OrderAggServiceImpl.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private InventoryService inventoryService;
    @Override
    @Transactional
    public Long placeOrder(Order order) {
        if (order==null) throw new NullEntityException();
        Long orderId = orderService.create(order);
        log.debug(String.format("create an order: [orderId: %d]", orderId));

        Long accountId = order.getPayment().getAccountId();
        Double balance = accountService.payoff(accountId, order.getAmount());
        log.debug(String.format("pay off the order: [balance of the account: %f]", balance));

        stockOut(order);
        return orderId;
    }

    private void stockOut(Order order) {
        if(order==null||order.getOrderItems()==null) return;
        order.getOrderItems().forEach(orderItem -> {
            Long productId = orderItem.getProductId();
            Long quantity = orderItem.getQuantity();
            Long balance = inventoryService.stockOut(productId, quantity);
            log.debug(String.format("stock out for the order: [productId: %d,quantity: %d,balance: %d]",
                    productId, quantity, balance));
        });
    }

    @Override
    @Transactional
    public void returnGoods(Long orderId) {
        if(orderId==null) throw new ValidException("The order id is null!");
        Order order = orderService.load(orderId);
        orderService.delete(orderId);
        log.debug(String.format("return the goods: [orderId: %d]", orderId));

        Long accountId = order.getPayment().getAccountId();
        Double balance = accountService.refund(accountId, order.getAmount());
        log.debug(String.format("refund for return the goods [balance of the account: %f]", balance));

        stockIn(order);
    }

    private void stockIn(Order order) {
        if(order==null||order.getOrderItems()==null) return;
        order.getOrderItems().forEach(orderItem -> {
            Long productId = orderItem.getProductId();
            Long quantity = orderItem.getQuantity();
            Long balance = inventoryService.stockIn(productId, quantity);
            log.debug("stock in for the order: [productId:"+productId+",quantity:"+quantity+
                    ",balance:"+balance+"]");
        });
    }
}
