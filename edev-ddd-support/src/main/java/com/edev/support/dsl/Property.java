package com.edev.support.dsl;

import java.util.Objects;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public boolean isDiscriminator() {
        return isDiscriminator;
    }

    public void setDiscriminator(boolean discriminator) {
        isDiscriminator = discriminator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return isPrimaryKey == property.isPrimaryKey && isDiscriminator == property.isDiscriminator && name.equals(property.name) && column.equals(property.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, column, isPrimaryKey, isDiscriminator);
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", column='" + column + '\'' +
                ", isPrimaryKey=" + isPrimaryKey +
                ", isDiscriminator=" + isDiscriminator +
                '}';
    }
}
