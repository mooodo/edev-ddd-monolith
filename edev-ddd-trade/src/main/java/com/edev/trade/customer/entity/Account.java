package com.edev.trade.customer.entity;

import com.edev.support.entity.Entity;
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

    public void setBalance(Double balance) {
        if (balance == null) balance = 0D;
        this.balance = balance;
    }
}
