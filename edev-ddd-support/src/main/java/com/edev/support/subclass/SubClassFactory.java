package com.edev.support.subclass;

import com.edev.support.ddd.utils.EntityUtils;
import com.edev.support.dsl.DomainObject;
import com.edev.support.dsl.DomainObjectFactory;
import com.edev.support.subclass.exception.SubClassNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class SubClassFactory {
    @Autowired
    private final Map<String, SubClassDao> subClassMap = new HashMap<>();

    public SubClassDao getSubClass(String name) {
        return subClassMap.get(name);
    }

    public Collection<SubClassDao> getAllSubClasses() {
        return subClassMap.values();
    }

    /**
     * according to the class of the subclass entity, choose the subclass dao
     * @param childClass the class of entity which is a subclass
     * @return the subclass dao
     * @exception SubClassNotExistsException if the class isn't a subclass
     */
    public SubClassDao chooseSubClass(Class<?> childClass) {
        for(SubClassDao subClassHelper : getAllSubClasses()) {
            DomainObject dObj = null;
            if(EntityUtils.isEntity(childClass.getSuperclass()))
                dObj = DomainObjectFactory.getDomainObject(childClass.getSuperclass());
            if(dObj==null) dObj = DomainObjectFactory.getDomainObject(childClass);
            if(subClassHelper.available(dObj)) return subClassHelper;
        }
        throw new SubClassNotExistsException(childClass);
    }
}
