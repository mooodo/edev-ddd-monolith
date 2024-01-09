package com.edev.trade.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Vendor extends Supplier {
    private Long distributorId;
    private String position;
}
