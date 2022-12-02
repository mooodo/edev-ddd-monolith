package com.edev.trade.product.entity;

public class Vendor extends Supplier {
    private Long distributor_id;
    private Distributor distributor;

    public Long getDistributorId() {
        return distributor_id;
    }

    public void setDistributorId(Long distributor_id) {
        this.distributor_id = distributor_id;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }
}
