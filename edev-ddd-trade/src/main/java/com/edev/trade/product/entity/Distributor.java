package com.edev.trade.product.entity;

public class Distributor extends Supplier {
    public Distributor() {
        super();
    }

    public Distributor(Long id, String name) {
        this.setId(id);
        this.setName(name);
        this.setSupplierType("distributor");
    }
}
