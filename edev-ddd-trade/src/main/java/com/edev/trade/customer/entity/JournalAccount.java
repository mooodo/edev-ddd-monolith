package com.edev.trade.customer.entity;

import com.edev.support.entity.Entity;
import com.edev.support.utils.DateUtils;

import java.util.Date;

public class JournalAccount extends Entity<Long> {
    private Long id;
    private Long accountId;
    private Double amount;
    private String operation;
    private Date operateTime;

    public JournalAccount() {}

    public JournalAccount(Long accountId, Double amount, String operation) {
        this(null, accountId, amount, operation, null);
    }

    public JournalAccount(Long id, Long accountId, Double amount, String operation, Date operateTime) {
        setId(id);
        setAccountId(accountId);
        setAmount(amount);
        setOperation(operation);
        setOperateTime(operateTime);
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime==null ? DateUtils.getNow() : operateTime;
    }
}
