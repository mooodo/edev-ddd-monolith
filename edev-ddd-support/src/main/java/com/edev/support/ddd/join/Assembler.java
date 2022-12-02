package com.edev.support.ddd.join;

import com.edev.support.entity.Entity;

import java.io.Serializable;
import java.util.Collection;

public interface Assembler<E extends Entity<S>, S extends Serializable> {
    void insertValue(E entity);
    void updateValue(E entity);
    void deleteValue(E entity);
    void setValue(E entity);
    void setValueForList(Collection<E> list);
}
