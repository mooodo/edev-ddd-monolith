package com.edev.support.dsl;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DomainObject {
    private String clazz = "";
    private String table = "";
    private String subClassType = "";
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

    public void addProperty(Property property) {
        this.properties.add(property);
    }

    public void addJoin(Join join) { this.joins.add(join); }

    public void addRef(Ref ref) { this.refs.add(ref); }

    public void addSubClass(SubClass subClass) { this.subClasses.add(subClass); }
}
