package com.edev.support.utils;

import com.edev.support.exception.OrmException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The utilities for the spring application context
 */
@Component
public class SpringHelper {
    @Autowired
    private ApplicationContext applicationContext;

    public Object getService(String beanName) {
        if (beanName == null || beanName.isEmpty())
            throw new OrmException("The bean name is empty!");
        try {
            return applicationContext.getBean(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            throw new OrmException("No such bean definition in the spring context!", e);
        } catch (BeansException e) {
            throw new OrmException("error when get the bean[%s]", beanName);
        }
    }
}
