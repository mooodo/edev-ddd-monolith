package com.edev.trade.product.entity;

public class Vendor extends Supplier {
    private Long distributorId;
    private String position;

    public Vendor() {
        super();
    }

    public Vendor(Long id, String name, Long distributorId, String position) {
        this.setId(id);
        this.setName(name);
        this.setSupplierType("vendor");
        this.distributorId = distributorId;
        this.position = position;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
