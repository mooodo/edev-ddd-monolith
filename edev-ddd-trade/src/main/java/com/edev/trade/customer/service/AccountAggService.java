package com.edev.trade.customer.service;

public interface AccountAggService {
    Double topUp(Long id, Double amount);
    Double payoff(Long id, Double amount);
    Double refund(Long id, Double amount);
}
