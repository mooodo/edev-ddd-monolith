package com.edev.trade.customer.web;

import com.edev.trade.customer.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    private PaymentService service;
    @GetMapping("topUp")
    public Double topUp(Long id, Double amount) {
        return service.topUp(id, amount);
    }
    @GetMapping("payoff")
    public Double payoff(Long id, Double amount) {
        return service.payoff(id, amount);
    }
    @GetMapping("refund")
    public Double refund(Long id, Double amount) {
        return service.refund(id, amount);
    }
}
