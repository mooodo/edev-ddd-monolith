package com.edev.trade.order.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class Discount extends Entity<Long> {
    private Long id;
    private String name;
    private Date beginTime;
    private Date endTime;
    private Double discount;
    private String discountType;

    public static Discount build() {
        return new Discount();
    }

    public Discount setValues(Long id, String name, Date beginTime, Date endTime, Double discount, String discountType) {
        this.id = id;
        this.name = name;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.discount = discount;
        this.discountType = discountType;
        return this;
    }
}
