package com.edev.support.ddd.join;

import com.edev.support.dao.BasicDao;
import com.edev.support.ddd.DddException;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.dsl.Join;
import com.edev.support.entity.Entity;
import com.edev.support.utils.BeanUtils;

import java.io.Serializable;

public abstract class AbstractAssembler<E extends Entity<S>, S extends Serializable> implements Assembler<E,S> {
    protected Join join;
    protected BasicDao dao;

    protected AbstractAssembler(Join join, BasicDao dao) {
        if(join==null||dao==null) throw new DddException("The parameters is null");
        this.join = join;
        this.dao = dao;
    }

    protected Entity<S> getTemplate() {
        return (new EntityBuilder<Entity<S>, S>(join.getClazz())).createEntity();
    }

    protected Class<Entity<S>> getClazz() {
        Class<?> clazz = BeanUtils.getClazz(join.getClazz());
        if(!Entity.class.isAssignableFrom(clazz))
            throw new DddException("The class is not an entity: ["+join.getClazz()+"]");
        return (Class<Entity<S>>) clazz;
    }
}
