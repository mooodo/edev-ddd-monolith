package com.edev.trade.order.entity;

import java.util.Date;

public class VipDiscount extends Discount {
    private String vipType;

    public VipDiscount() {
    }

    public VipDiscount(Long id, String name, Date beginTime, Date endTime, Double discount, String vipType) {
        super(id, name, beginTime, endTime, discount, "vipDiscount");
        this.vipType = vipType;
    }

    public String getVipType() {
        return vipType;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }
}
