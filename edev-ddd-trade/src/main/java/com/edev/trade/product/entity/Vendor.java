package com.edev.trade.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Vendor extends Supplier {
    private Long distributorId;
    private String position;

    public static Vendor build() {
        return new Vendor();
    }

    public Vendor setValues(Long id, String name, Long distributorId, String position) {
        super.setValues(id, name, "vendor");
        this.distributorId = distributorId;
        this.position = position;
        return this;
    }
}
