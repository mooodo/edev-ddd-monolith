package com.edev.trade.customer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class SilverVip extends Vip {
    public static SilverVip build() {
        return new SilverVip();
    }

    public SilverVip setValues(Long id, Boolean available, Long coin) {
        return (SilverVip) super.setValues(id, available, coin, "silver");
    }

    public SilverVip setValues(Long id, Date createTime, Date updateTime, Boolean available, Long coin) {
        return (SilverVip) super.setValues(id, createTime, updateTime, available, coin, "silver");
    }

    @Override
    public Double discount() {
        return 0.75D;
    }
}
