package com.edev.support.dsl;

import com.edev.support.utils.BeanUtils;
import com.edev.support.xml.XmlBuildFactoryTemplate;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DomainObjectFactory extends XmlBuildFactoryTemplate {
    private static final Map<String, DomainObject> map = new HashMap<>();
    private String paths = "classpath:entity/*.xml";
    public DomainObjectFactory() {
        if(map.isEmpty()) {
            initFactory(this.paths);
            doInheritForAllSubclass();
        }
    }
    public DomainObjectFactory(String paths) {
        this.paths = paths;
        initFactory(this.paths);
        doInheritForAllSubclass();
    }
    @Override
    protected void loadBean(Element element) {
        DomainObject domainObject = new DomainObject();
        loadDomainObject(element, domainObject);
        map.put(domainObject.getClazz(), domainObject);
    }

    private void loadDomainObject(Element element, DomainObject domainObject) {
        domainObject.setClazz(element.getAttribute("class"));
        domainObject.setTable(element.getAttribute("tableName"));
        domainObject.setSubClassType(element.getAttribute("subclassType"));
        loadChildNodes(element, domainObject);
    }

    private void loadChildNodes(Element element, DomainObject domainObject) {
        NodeList nodeList = element.getChildNodes();
        for(int index=0; index< nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            if(!(node instanceof Element)) continue;
            if(node.getNodeName().equals("property"))
                loadProperty((Element) node, domainObject);
            if(node.getNodeName().equals("join"))
                loadJoin((Element) node, domainObject);
            if(node.getNodeName().equals("ref"))
                loadRef((Element) node, domainObject);
            if(node.getNodeName().equals("subclass"))
                loadSubClass((Element) node, domainObject);
        }
    }

    private void loadProperty(Element element, DomainObject domainObject) {
        Property property = new Property();
        property.setName(element.getAttribute("name"));
        property.setColumn(element.getAttribute("column"));
        property.setPrimaryKey(element.getAttribute("isPrimaryKey").equals("true"));
        property.setDiscriminator(element.getAttribute("isDiscriminator").equals("true"));
        domainObject.addProperty(property);
    }

    private void loadJoin(Element element, DomainObject domainObject) {
        Join join = new Join();
        join.setName(element.getAttribute("name"));
        join.setJoinKey(element.getAttribute("joinKey"));
        join.setJoinType(element.getAttribute("joinType"));
        join.setJoinClass(element.getAttribute("joinClass"));
        join.setJoinClassKey(element.getAttribute("joinClassKey"));
        join.setAggregation(element.getAttribute("isAggregation").equals("true"));
        join.setClazz(element.getAttribute("class"));
        domainObject.addJoin(join);
    }

    private void loadRef(Element element, DomainObject domainObject) {
        Ref ref = new Ref();
        ref.setName(element.getAttribute("name"));
        ref.setRefKey(element.getAttribute("refKey"));
        ref.setRefType(element.getAttribute("refType"));
        ref.setBean(element.getAttribute("bean"));
        ref.setMethod(element.getAttribute("method"));
        ref.setListMethod(element.getAttribute("listMethod"));
        domainObject.addRef(ref);
    }

    private void loadSubClass(Element element, DomainObject domainObject) {
        SubClass subClass = new SubClass();
        subClass.setValue(element.getAttribute("value"));
        loadDomainObject(element, subClass);
        domainObject.addSubClass(subClass);
    }

    private void doInheritForAllSubclass() {
        map.values().forEach(dObj -> {
            if(dObj.getSubClasses()!=null && !dObj.getSubClasses().isEmpty())
                dObj.getSubClasses().forEach(subClass ->
                        inheritFromParent(dObj.getClazz(), subClass.getClazz()));
        });
    }

    private void inheritFromParent(String parent, String child) {
        try {
            DomainObject parentDObj = getDomainObject(parent, false);
            DomainObject childDObj = getDomainObject(child, false);

//            List<Property> properties = new ArrayList<>();
//            properties.addAll(parentDObj.getProperties());
//            properties.addAll(childDObj.getProperties());
//            childDObj.setProperties(properties.stream().distinct().collect(Collectors.toList()));

            List<Join> joins = new ArrayList<>();
            joins.addAll(parentDObj.getJoins());
            joins.addAll(childDObj.getJoins());
            childDObj.setJoins(joins.stream().distinct().collect(Collectors.toList()));

            List<Ref> refs = new ArrayList<>();
            refs.addAll(parentDObj.getRefs());
            refs.addAll(childDObj.getRefs());
            childDObj.setRefs(refs.stream().distinct().collect(Collectors.toList()));
        } catch (NoSuchDObjException e) {
            return;
        }
    }

    public static DomainObject getDomainObject(String className) {
        return getDomainObject(className, true);
    }

    public static DomainObject getDomainObject(String className, boolean includeParent) {
        Class<?> clazz = BeanUtils.getClazz(className);
        return getDomainObject(clazz, includeParent);
    }

    public static DomainObject getDomainObject(Class<?> clazz) {
        return getDomainObject(clazz, true);
    }

    public static DomainObject getDomainObject(Class<?> clazz, boolean includeParent) {
        DomainObject dObj = map.get(clazz.getName());
        if(includeParent && dObj == null) dObj = map.get(clazz.getSuperclass().getName());
        if(dObj == null) throw new NoSuchDObjException(clazz.getName());
        return dObj;
    }

    public static boolean isExists(String className) {
        return map.containsKey(className);
    }

    public static boolean isExists(Class<?> clazz) {
        return isExists(clazz.getName());
    }
}
