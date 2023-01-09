package com.edev.support.dsl;

import java.util.Objects;

public class Ref {
    private String name = "";
    private String refKey = "";
    private String refType = "";
    private String bean = "";
    private String method = "";
    private String listMethod = "";

    public Ref() {}

    public Ref(String name, String refKey, String refType, String bean, String method, String listMethod) {
        this.name = name;
        this.refKey = refKey;
        this.refType = refType;
        this.bean = bean;
        this.method = method;
        this.listMethod = listMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefKey() {
        return refKey;
    }

    public void setRefKey(String refKey) {
        this.refKey = refKey;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getBean() {
        return bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getListMethod() {
        return listMethod;
    }

    public void setListMethod(String listMethod) {
        this.listMethod = listMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ref ref = (Ref) o;
        return name.equals(ref.name) && refKey.equals(ref.refKey) && refType.equals(ref.refType) && bean.equals(ref.bean) && method.equals(ref.method) && listMethod.equals(ref.listMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, refKey, refType, bean, method, listMethod);
    }

    @Override
    public String toString() {
        return "Ref{" +
                "name='" + name + '\'' +
                ", refKey='" + refKey + '\'' +
                ", refType='" + refType + '\'' +
                ", bean='" + bean + '\'' +
                ", method='" + method + '\'' +
                ", listMethod='" + listMethod + '\'' +
                '}';
    }
}
