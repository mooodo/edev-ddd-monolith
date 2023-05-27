package com.edev.support.dsl;

import lombok.Data;

@Data
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
}
