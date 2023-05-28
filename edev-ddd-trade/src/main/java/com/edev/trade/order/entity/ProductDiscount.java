package com.edev.trade.order.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDiscount extends Discount {
    private Long productId;

    public static ProductDiscount build() {
        return new ProductDiscount();
    }

    public ProductDiscount setValues(Long id, String name, Date beginTime, Date endTime, Double discount, Long productId) {
        super.setValues(id, name, beginTime, endTime, discount, "productDiscount");
        this.productId = productId;
        return this;
    }
}
