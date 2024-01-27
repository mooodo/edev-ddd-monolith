package com.edev.trade.customer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class GoldenVip extends Vip{
    private Double cashback;

    @Override
    public Double discount() {
        return 0.9D;
    }
}
