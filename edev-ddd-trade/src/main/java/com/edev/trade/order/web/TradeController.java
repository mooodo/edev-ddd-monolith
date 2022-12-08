package com.edev.trade.order.web;

import com.edev.trade.order.entity.Order;
import com.edev.trade.order.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("trade")
public class TradeController {
    @Autowired
    private TradeService service;
    @PostMapping("placeOrder")
    public Long placeOrder(@RequestBody Order order) {
        return service.placeOrder(order);
    }
    @GetMapping("returnGoods")
    public void returnGoods(Long orderId) {
        service.returnGoods(orderId);
    }
}
