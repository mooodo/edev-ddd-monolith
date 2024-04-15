package com.edev.trade.order.service.impl;

import com.edev.support.exception.ValidException;
import com.edev.trade.customer.service.AccountService;
import com.edev.trade.inventory.service.InventoryService;
import com.edev.trade.order.entity.Order;
import com.edev.trade.order.service.OrderService;
import com.edev.trade.order.service.OrderAggService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("orderAgg")
@Slf4j
public class OrderAggServiceImpl implements OrderAggService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private InventoryService inventoryService;
    @Override
    public Long placeOrder(@NonNull Order order) {
        Long orderId = orderService.create(order);
        log.debug(String.format("create an order: [orderId: %d]", orderId));

        return orderId;
    }

    @Override
    @Transactional
    public void payoff(@NonNull Order order) {
        if(order.getPayment()==null||order.getPayment().getAccountId()==null)
            throw new ValidException("no account for payoff: [orderId: %s]", order.getId());
        Long accountId = order.getPayment().getAccountId();
        Double balance = accountService.payoff(accountId, order.getAmount());
        log.debug(String.format("pay off the order: [balance of the account: %f]", balance));

        stockOut(order);

        order.setStatus("PAYOFF");
        order.getPayment().setStatus("PAYOFF");
        orderService.modify(order);
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
    public void cancelOrder(@NonNull Long orderId) {
        Order order = orderService.load(orderId);
        if (order==null) throw new  ValidException("no found the order: [orderId: %d]", orderId);
        orderService.delete(orderId);
        log.debug(String.format("cancel the order: [orderId: %d]", orderId));

        Long accountId = order.getPayment().getAccountId();
        Double balance = accountService.refund(accountId, order.getAmount());
        log.debug(String.format("refund for cancel the order [balance of the account: %f]", balance));

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
