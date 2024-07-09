package com.edev.trade.product.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SelfSupport extends Supplier {
    public static SelfSupport build() {
        return new SelfSupport();
    }

    public SelfSupport setValues(Long id, String name) {
        super.setValues(id, name, "selfSupport");
        return this;
    }
}
