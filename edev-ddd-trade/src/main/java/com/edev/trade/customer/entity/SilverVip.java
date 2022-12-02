package com.edev.trade.customer.entity;

public class SilverVip extends Vip {
    public SilverVip() {}

    public SilverVip(Long id, Boolean available, Long coin) {
        super(id, available, coin, "silver");
    }

    @Override
    public Double discount() {
        return 0.75D;
    }
}
