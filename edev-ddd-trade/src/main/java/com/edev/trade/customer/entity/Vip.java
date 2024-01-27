package com.edev.trade.customer.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class Vip extends Entity<Long> {
    protected Long id;
    protected Date createTime;
    protected Date updateTime;
    protected String available;
    protected Long coin;
    protected String vipType;
    protected Customer customer;

    public Boolean getAvailable() {
        return "Y".equals(available);
    }

    public void setAvailable(Boolean available) {
        this.available = available ? "Y" : "N";
    }

    public Double discount() {
        return 0D;
    }

}
