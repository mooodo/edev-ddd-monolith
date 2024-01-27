package com.edev.trade.customer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class SilverVip extends Vip {

    @Override
    public Double discount() {
        return 0.75D;
    }
}
