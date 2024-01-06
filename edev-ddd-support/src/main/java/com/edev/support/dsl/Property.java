package com.edev.support.dsl;

import lombok.Data;

@Data
public class Property {
    private String name = "";
    private String column = "";
    private boolean isPrimaryKey = false;
    private boolean isDiscriminator = false;

    public Property() {}
    public Property(String name, String column, boolean isPrimaryKey, boolean isDiscriminator) {
        this.name = name;
        this.column = column;
        this.isPrimaryKey = isPrimaryKey;
        this.isDiscriminator = isDiscriminator;
    }
}
