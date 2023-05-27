package com.edev.support.dsl;

import lombok.Data;

@Data
public class Join {
    private String name = "";
    private String joinKey = "";
    private String joinType = "";
    private String joinClassKey = "";
    private String joinClass = "";
    private boolean isAggregation = false;
    private String clazz = "";

    public Join() {}

    public Join(String name, String joinKey, String joinType, boolean isAggregation, String clazz) {
        this.name = name;
        this.joinKey = joinKey;
        this.joinType = joinType;
        this.isAggregation = isAggregation;
        this.clazz = clazz;
    }

    public Join(String name, String joinKey, String joinType, String joinClassKey, String joinClass, boolean isAggregation, String clazz) {
        this.name = name;
        this.joinKey = joinKey;
        this.joinType = joinType;
        this.joinClassKey = joinClassKey;
        this.joinClass = joinClass;
        this.isAggregation = isAggregation;
        this.clazz = clazz;
    }
}
