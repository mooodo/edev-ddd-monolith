package com.edev.support.dsl;

import java.util.Objects;

public class SubClass extends DomainObject {
    private String value;
    public SubClass() {
        super();
    }

    public SubClass(String clazz, String value) {
        super(clazz, "", "");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubClass subClass = (SubClass) o;
        return Objects.equals(value, subClass.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return super.toString() + ',' +
                "SubClass{" +
                "value='" + value + '\'' +
                '}';
    }
}
