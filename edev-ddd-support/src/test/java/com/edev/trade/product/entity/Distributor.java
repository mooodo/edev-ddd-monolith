package com.edev.trade.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Distributor extends Supplier {
    private String introduce;
    private String address;
    private String range;
    private List<Vendor> vendors;
    public static Distributor build() {
        return new Distributor();
    }

    public Distributor setValues(Long id, String name, String introduce, String address, String range) {
        super.setValues(id, name, "distributor");
        this.introduce = introduce;
        this.address = address;
        this.range = range;
        return this;
    }
}
