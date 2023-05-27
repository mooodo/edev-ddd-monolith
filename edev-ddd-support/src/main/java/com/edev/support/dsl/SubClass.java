package com.edev.support.dsl;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SubClass extends DomainObject {
    private String value = "";
    public SubClass() {
        super();
    }

    public SubClass(String clazz, String value) {
        super(clazz, "", "");
        this.value = value;
    }
}
