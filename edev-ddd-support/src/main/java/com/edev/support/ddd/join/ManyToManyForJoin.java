package com.edev.support.ddd.join;

import com.edev.support.dao.BasicDao;
import com.edev.support.ddd.DddException;
import com.edev.support.ddd.utils.EntityBuilder;
import com.edev.support.dsl.Join;
import com.edev.support.entity.Entity;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

public class ManyToManyForJoin<E extends Entity<S>, S extends Serializable> extends AbstractAssembler<E,S> implements Assembler<E,S> {
    public ManyToManyForJoin(Join join, BasicDao dao) {super(join, dao);}

    private Entity<S> getJoinObject() {
        return (new EntityBuilder<Entity<S>, S>(join.getJoinClass())).createEntity();
    }
    @Override
    @Transactional
    public void insertValue(E entity) {
        if(entity==null) throw new DddException("The entity is null!");
        S id = entity.getId();
        String name = join.getName();
        Collection<Entity> collection = (Collection<Entity>)entity.getValue(name);
        if(collection==null||collection.isEmpty()) return;

        collection.forEach(e->{
            Entity<S> joinObj = getJoinObject();
            joinObj.setValue(join.getJoinKey(), id);
            joinObj.setValue(join.getJoinKey(), e.getId());
            dao.insert(joinObj);
        });
    }

    @Override
    public void updateValue(E entity) {

    }

    @Override
    public void deleteValue(E entity) {

    }

    @Override
    public void setValue(E entity) {

    }

    @Override
    public void setValueForList(Collection<E> list) {

    }
}
