package com.edev.support.web;

import com.edev.support.ddd.DddFactory;
import com.edev.support.entity.Entity;
import com.edev.support.utils.BeanUtils;
import com.edev.support.utils.DowncastHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class OrmController extends AbstractController {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DddFactory dddFactory;
    @Autowired
    private DowncastHelper helper;
    @RequestMapping(value = "orm/{bean}/{method}", method = {RequestMethod.GET, RequestMethod.POST})
    public Object perform(@PathVariable("bean") String beanName,
                          @PathVariable("method") String methodName,
                          @RequestBody(required = false) Map<String, Object> map,
                          HttpServletRequest request) {
        Object service = BeanUtils.getService(beanName, applicationContext);
        Method method = BeanUtils.getMethod(service, methodName);
        Map<String, Object> json = mergeDataToJson(map, request);
        Object[] args = getArguments(method, json);
        return BeanUtils.invoke(service, method, args);
    }

    /**
     * execute a bean's method by POST method.
     * @param beanName the name of the bean in the spring factory
     * @param methodName the name of the method that need to call
     * @param json the json that submit with
     * @return the returned value of the method after called
     */
    public Object doPost(String beanName, String methodName, Map<String, Object> json) {
        return perform(beanName, methodName, json, null);
    }

    /**
     * execute a bean's method by GET method.
     * @param beanName the name of the bean in the spring factory
     * @param methodName the name of the method that need to call
     * @param request the http servlet request that get data from client
     * @return the returned value of the method after called
     */
    public Object doGet(String beanName, String methodName, HttpServletRequest request) {
        return perform(beanName, methodName, null, request);
    }

    @PostMapping("list/{bean}/{method}")
    public Object list(@PathVariable("bean") String beanName,
                       @PathVariable("method") String methodName,
                       @RequestBody List<Object> list) {
        Object service = BeanUtils.getService(beanName, applicationContext);
        Method method = BeanUtils.getMethod(service, methodName);
        Object[] args = getArguments(method, list);
        return BeanUtils.invoke(service, method, args);
    }

    public Object doList(String beanName, String methodName, List<Object> list) {
        return list(beanName, methodName, list);
    }

    private <E extends Entity<S>, S extends Serializable>
            Object[] getArguments(Method method, Map<String, Object> json) {
        //There is no parameter to use
        if(json==null||json.isEmpty()) return new Object[]{};
        Parameter[] parameters = method.getParameters();
        //The method has no parameter
        if(parameters.length == 0) return new Object[]{};

        List<Object> args = new ArrayList<>();
        for(Parameter parameter : parameters) {
            if(dddFactory.isEntity(parameter.getType())) {
                Class<E> clazz = (Class<E>) parameter.getType();
                E entity = dddFactory.createEntityByJson(clazz, json);
                args.add(entity);
            } else if(dddFactory.isListOrSetOfEntities(parameter.getParameterizedType())) {
                Type type = parameter.getParameterizedType();
                String name = parameter.getName();
                Object value = json.get(name);
                List<E> entities = dddFactory.createEntityByJsonForList(type, value);
                args.add(entities);
            } else {
                String name = parameter.getName();
                Object value = json.get(name);
                value = helper.downcast(parameter.getParameterizedType(), value);
                args.add(value);
            }
        }
        return args.toArray();
    }

    private <E extends Entity<S>, S extends Serializable>
            Object[] getArguments(Method method, List<Object> list) {
        if(list==null||list.isEmpty()) return new Object[]{};
        Parameter[] parameters = method.getParameters();
        if(parameters.length!=1)
            throw new WebException("The parameter must be a single list, or use 'orm/{bean}/{method}' request");
        Parameter parameter = parameters[0];
        List<Object> args = new ArrayList<>();
        if(dddFactory.isListOrSetOfEntities(parameter.getParameterizedType())) {
            Type type = parameter.getParameterizedType();
            List<E> entities = dddFactory.createEntityByJsonForList(type, list);
            args.add(entities);
        } else {
            Object value = helper.downcast(parameter.getParameterizedType(), list);
            args.add(value);
        }
        return args.toArray();
    }
}
