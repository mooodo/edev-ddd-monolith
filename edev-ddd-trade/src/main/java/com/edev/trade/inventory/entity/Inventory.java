package com.edev.trade.inventory.entity;

import com.edev.support.entity.Entity;

import java.util.Date;
import java.util.Objects;

public class Inventory extends Entity<Long> {
    private Long id;
    private Long quantity;
    private Date updateTime;
    private Product product;

    public Inventory() {}

    public Inventory(Long id, Long quantity, Date updateTime) {
        this.id = id;
        this.quantity = quantity;
        this.updateTime = updateTime;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    protected String[] exclude() {
        return new String[]{"updateTime"};
    }
}
