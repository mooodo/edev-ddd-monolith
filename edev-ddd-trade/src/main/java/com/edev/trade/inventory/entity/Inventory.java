package com.edev.trade.inventory.entity;

import com.edev.support.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
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
}
