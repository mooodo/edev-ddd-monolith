package com.edev.trade.product.entity;

import java.util.List;

public class Distributor extends Supplier {
    private String introduce;
    private String address;
    private String range;
    private List<Vendor> vendors;
    public Distributor() {
        super();
    }

    public Distributor(Long id, String name, String introduce, String address, String range) {
        this.setId(id);
        this.setName(name);
        this.setSupplierType("distributor");
        this.introduce = introduce;
        this.address = address;
        this.range = range;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }
}
