package com.edev.trade.customer.entity;

import com.edev.support.entity.Entity;
import com.edev.support.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class Account extends Entity<Long> {
    private Long id;
    private Long customerId;
    private Double balance;
    private Date createTime;
    private Date updateTime;
    private Customer customer;

    public static Account build() {
        return new Account();
    }

    public Account setValues(Long id, Long customerId, Double balance, Date createTime, Date updateTime) {
        this.setId(id);
        this.setCustomerId(customerId);
        this.setBalance(balance);
        this.setCreateTime(createTime);
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setBalance(Double balance) {
        if (balance == null) balance = 0D;
        this.balance = balance;
    }

    public void setCreateTime(Date createTime) {
        if(createTime == null) createTime = DateUtils.getNow();
        this.createTime = createTime;
    }
}
