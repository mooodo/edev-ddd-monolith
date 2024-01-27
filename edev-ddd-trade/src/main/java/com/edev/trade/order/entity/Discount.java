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
}
