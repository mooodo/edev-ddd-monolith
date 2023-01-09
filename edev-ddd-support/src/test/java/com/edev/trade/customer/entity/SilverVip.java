package com.edev.trade.customer.entity;

import java.util.Date;

public class SilverVip extends Vip {
    public SilverVip() {}

    public SilverVip(Long id, Boolean available, Long coin) {
        super(id, available, coin, "silver");
    }

    public SilverVip(Long id, Date createTime, Date updateTime, Boolean available, Long coin) {
        super(id, createTime, updateTime, available, coin, "silver");
    }
}
