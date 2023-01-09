package com.edev.trade.order.entity;

import java.util.Date;

public class ProductDiscount extends Discount {
    private Long productId;

    public ProductDiscount() {
    }

    public ProductDiscount(Long id, String name, Date beginTime, Date endTime, Double discount, Long productId) {
        super(id, name, beginTime, endTime, discount, "productDiscount");
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
