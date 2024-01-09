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
}
