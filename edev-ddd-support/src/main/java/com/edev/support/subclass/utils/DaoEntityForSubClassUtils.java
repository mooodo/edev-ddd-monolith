package com.edev.support.subclass.utils;

import com.edev.support.dao.impl.utils.DaoEntity;
import com.edev.support.dao.impl.utils.DaoEntityBuilder;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.dsl.Property;
import com.edev.support.dsl.SubClass;
import com.edev.support.entity.Entity;
import com.edev.support.subclass.exception.SubClassNotExistsException;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DaoEntityForSubClassUtils {
    private DaoEntityForSubClassUtils() {}
    /**
     * read and decode the data of entity, and build the daoEntity.
     * And then set the columns of subclass into daoEntity.
     * The method be used for the subclass which the type is 'simple'.
     * @param child the child entity
     * @return the daoEntity
     */
    public static DaoEntity buildWithEntityAndItsSubClass(@NotNull Entity<?> child) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(child.getClass().getSuperclass());
        DaoEntity daoEntity = DaoEntityBuilder.build(child, dObj);

        //set the columns of subclass into daoEntity
        SubClass subClass = getSubClass(dObj, child.getClass());
        List<Property> properties = subClass.getProperties();
        daoEntity.setProperties(child, properties);
        return daoEntity;
    }

    /**
     * get dsl of the subclass
     * @param dObj the dsl of the parent class
     * @param subClazz the class of the subclass
     * @return the dsl of the subclass
     */
    private static SubClass getSubClass(@NotNull DomainObject dObj, @NotNull Class<?> subClazz) {
        List<SubClass> subClasses = dObj.getSubClasses();
        for(SubClass subClass : subClasses)
            if(subClass.getClazz().equals(subClazz.getName()))
                return subClass;
        throw new SubClassNotExistsException(subClazz);
    }

    /**
     * read and decode the data of child entity, and build the parent daoEntity.
     * @param child the child entity
     * @return the daoEntity for parent
     */
    public static DaoEntity buildForParent(@NotNull Entity<?> child) {
        DomainObject dObj = DomainObjectFactory.getDomainObject(child.getClass().getSuperclass());
        return DaoEntityBuilder.build(child, dObj);
    }
}
