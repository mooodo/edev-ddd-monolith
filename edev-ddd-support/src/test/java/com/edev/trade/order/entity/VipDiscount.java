package com.edev.trade.order.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class VipDiscount extends Discount {
    private String vipType;

    public static VipDiscount build() {
        return new VipDiscount();
    }

    public VipDiscount setValues(Long id, String name, Date beginTime, Date endTime,
                                 Double discount, String vipType) {
        super.setValues(id, name, beginTime, endTime, discount, "vipDiscount");
        this.vipType = vipType;
        return this;
    }
}
