package com.edev.trade.customer.entity;

import com.edev.support.entity.Entity;
import com.edev.support.utils.DateUtils;

import java.util.Date;

public class Account extends Entity<Long> {
    private Long id;
    private Long customerId;
    private Double balance;
    private Date createTime;
    private Date updateTime;
    private Customer customer;

    public Account() { }

    public Account(Long id, Long customerId, Double balance, Date createTime, Date updateTime) {
        setId(id);
        setCustomerId(customerId);
        setBalance(balance);
        setCreateTime(createTime);
        setUpdateTime(updateTime);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        if (balance == null) balance = 0D;
        this.balance = balance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        if(createTime == null) createTime = DateUtils.getNow();
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
