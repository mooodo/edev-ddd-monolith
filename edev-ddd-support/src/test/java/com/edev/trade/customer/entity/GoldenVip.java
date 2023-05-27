package com.edev.trade.customer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class GoldenVip extends Vip{
    private Double cashback;

    public static GoldenVip build() {
        return new GoldenVip();
    }

    public GoldenVip setValues(Long id, Boolean available, Long coin, Double cashback) {
        return setValues(id, null, null, available, coin, cashback);
    }

    public GoldenVip setValues(Long id, Date createTime, Date updateTime, Boolean available, Long coin, Double cashback) {
        super.setValues(id, createTime, updateTime, available, coin, "golden");
        this.cashback = cashback;
        return this;
    }
}
