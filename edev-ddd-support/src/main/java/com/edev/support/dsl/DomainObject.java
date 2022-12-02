package com.edev.support.dsl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DomainObject {
    private String clazz;
    private String table;
    private String subClassType;
    private List<Property> properties = new ArrayList<>();
    private List<Join> joins = new ArrayList<>();
    private List<Ref> refs = new ArrayList<>();
    private List<SubClass> subClasses = new ArrayList<>();

    public DomainObject() {}
    public DomainObject(String clazz, String table, String subClassType) {
        this.clazz = clazz;
        this.table = table;
        this.subClassType = subClassType;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSubClassType() {
        return subClassType;
    }

    public void setSubClassType(String subClassType) {
        this.subClassType = subClassType;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void addProperty(Property property) {
        this.properties.add(property);
    }

    public List<Join> getJoins() {
        return joins;
    }

    public void setJoins(List<Join> joins) {
        this.joins = joins;
    }

    public void addJoin(Join join) { this.joins.add(join); }

    public List<Ref> getRefs() {
        return refs;
    }

    public void setRefs(List<Ref> refs) {
        this.refs = refs;
    }

    public void addRef(Ref ref) { this.refs.add(ref); }

    public List<SubClass> getSubClasses() {
        return subClasses;
    }

    public void setSubClasses(List<SubClass> subClasses) {
        this.subClasses = subClasses;
    }

    public void addSubClass(SubClass subClass) { this.subClasses.add(subClass); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainObject that = (DomainObject) o;
        return clazz.equals(that.clazz) && Objects.equals(table, that.table) && Objects.equals(subClassType, that.subClassType) && Objects.equals(properties, that.properties) && Objects.equals(joins, that.joins) && Objects.equals(refs, that.refs) && Objects.equals(subClasses, that.subClasses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, table, subClassType, properties, joins, refs, subClasses);
    }

    @Override
    public String toString() {
        return "DomainObject{" +
                "clazz='" + clazz + '\'' +
                ", table='" + table + '\'' +
                ", subClassType='" + subClassType + '\'' +
                ", properties=" + properties +
                ", joins=" + joins +
                ", refs=" + refs +
                '}';
    }
}
