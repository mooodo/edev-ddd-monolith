package com.edev.trade.order.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Payment extends Entity<Long> {
    private Long id;
    private Long accountId;
    private Double amount;
    private String status;

    public static Payment build() {
        return new Payment();
    }

    public Payment setValues(Long id, Long accountId, Double amount, String status) {
        setId(id);
        setAccountId(accountId);
        setAmount(amount);
        setStatus(status);
        return this;
    }

    public Payment setValues(Long id, Long accountId) {
        return setValues(id, accountId, null, null);
    }
}
