package com.edev.trade.customer.entity;

import com.edev.support.entity.Entity;
import com.edev.support.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true, exclude = {"operateTime"})
@Data
public class JournalAccount extends Entity<Long> {
    private Long id;
    private Long accountId;
    private Double amount;
    private String operation;
    private Date operateTime;

    public static JournalAccount build() {
        return new JournalAccount();
    }

    public JournalAccount setValues(Long accountId, Double amount, String operation) {
        return setValues(null, accountId, amount, operation, null);
    }

    public JournalAccount setValues(Long id, Long accountId, Double amount, String operation, Date operateTime) {
        setId(id);
        setAccountId(accountId);
        setAmount(amount);
        setOperation(operation);
        setOperateTime(operateTime);
        return this;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime==null ? DateUtils.getNow() : operateTime;
    }
}
