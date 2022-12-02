package com.edev.trade.product.entity;

public class Vendor extends Supplier {
    private Long distributorId;
    private Distributor distributor;

    public Vendor() {
        super();
    }

    public Vendor(Long id, String name, Long distributorId) {
        this.setId(id);
        this.setName(name);
        this.setSupplierType("vendor");
        this.distributorId = distributorId;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }
}
