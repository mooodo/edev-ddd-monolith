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
    public void setStatus(String status) {
        this.status = (status==null) ? "READY" : status;
    }
}
