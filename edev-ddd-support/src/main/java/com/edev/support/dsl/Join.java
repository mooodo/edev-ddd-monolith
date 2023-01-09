package com.edev.support.dsl;

import java.util.Objects;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJoinKey() {
        return joinKey;
    }

    public void setJoinKey(String joinKey) {
        this.joinKey = joinKey;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getJoinClassKey() {
        return joinClassKey;
    }

    public void setJoinClassKey(String joinClassKey) {
        this.joinClassKey = joinClassKey;
    }

    public String getJoinClass() {
        return joinClass;
    }

    public void setJoinClass(String joinClass) {
        this.joinClass = joinClass;
    }

    public boolean isAggregation() {
        return isAggregation;
    }

    public void setAggregation(boolean aggregation) {
        isAggregation = aggregation;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Join join = (Join) o;
        return isAggregation == join.isAggregation && name.equals(join.name) && joinKey.equals(join.joinKey) && joinType.equals(join.joinType) && Objects.equals(joinClassKey, join.joinClassKey) && Objects.equals(joinClass, join.joinClass) && clazz.equals(join.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, joinKey, joinType, joinClassKey, joinClass, isAggregation, clazz);
    }

    @Override
    public String toString() {
        return "Join{" +
                "name='" + name + '\'' +
                ", joinKey='" + joinKey + '\'' +
                ", joinType='" + joinType + '\'' +
                ", joinClassKey='" + joinClassKey + '\'' +
                ", joinClass='" + joinClass + '\'' +
                ", isAggregation=" + isAggregation +
                ", clazz='" + clazz + '\'' +
                '}';
    }
}
