package com.edev.trade.order.entity;

import com.edev.support.entity.Entity;

public class Payment extends Entity<Long> {
    private Long id;
    private Long accountId;
    private Double amount;
    private String status;

    public Payment() {}

    public Payment(Long id, Long accountId, Double amount, String status) {
        setId(id);
        setAccountId(accountId);
        setAmount(amount);
        setStatus(status);
    }

    public Payment(Long id, Long accountId) {
        this(id, accountId, null, null);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
