/*
 * created on 2009-11-16 
 */
package com.edev.support.xml;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * The template that read xml files with DOM, and build the factory. 
 * When create the factory, input a path or list of paths, 
 * then it reads the resources and build the factory immediately.
 * <p>
 * The path can be a file, a direction, a list of files or paths, like this:
 * <li>vObj.xml that read from the class local path</li>
 * <li>src/test/java/com/demo2/support/xml that read from relative path</li>
 * <li>C:\\demo-service2-support\\src\\test\\java\\com\\demo2\\support\\xml</li>
 * <li>classpath*:vObj.xml that read from classpath in the project and its jars</li>
 * <li>file:C:\\demo-service2-support\\src\\test\\java\\com\\demo2\\support\\xml</li>
 * <li>/mapper/genericDaoMapper.xml that read from the context of the project</li>
 * <li>classpath:applicationContext-*.xml that use wildcard character '*'</li>
 * <li>/entity/customer.xml, /entity/product.xml that list many files separated by comma</li>
 * <p>
 * Inherit the template and call the initFactory(), 
 * and then implement the loadBean(element), such as: 
 * <pre>
	protected void loadBean(Element element) {
		String clazz = element.getAttribute("class");
		String tableName = element.getAttribute("tableName");
		loadChildNodes(element, vObj);
	}
	
	private void loadChildNodes(Element element) {
		NodeList nodeList = element.getChildNodes();
		for(int i=0; i<=nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(!(node instanceof Element)) continue;
			if (node.getNodeName().equals("property")) {
				Element child = (Element) node;
				String name = child.getAttribute("name");
			}
		}
	}
 * </pre>
 * @author fangang
 */
@Getter @Setter
@Slf4j
public abstract class XmlBuildFactoryTemplate {
	private boolean validating = false;
	private boolean namespaceAware = false;
	private String[] paths;
	protected Class<?> clazz;

	/**
	 * initialize a factory with path
	 * @param path the path to read xml file
	 */
	public void initFactory(@NonNull String path){
		initFactory(path.split(","));
	}
	
	/**
	 * initialize a factory with list of paths
	 * @param paths the list of paths to read xml files.
	 */
	public void initFactory(@NonNull String... paths) {
		this.paths = paths;
		boolean success;
		ResourceLoader loader;

		loader = new ClassPathResourceLoader(this.clazz);
		success = loader.loadResource(this::readXmlStream, paths);
		if(success) return;

		loader = new FileResourceLoader();
		success = loader.loadResource(this::readXmlStream, paths);
		if(success) return;

		loader = new UrlResourceLoader();
		success = loader.loadResource(this::readXmlStream, paths);
		if(success) return;

		log.warn("Nothing load in factory because of no found xml files! ");
	}
	
	/**
	 * reload the factory that read xml files again.
	 */
	public void reloadFactory(){
		initFactory(this.paths);
	}

	/**
	 * read the xml input stream, and then call <code>buildFactory(Element)</code>
	 * @param inputStream the input stream
	 */
	protected void readXmlStream(InputStream inputStream) {
		try {
			if(inputStream==null) throw new XmlBuildException("Input stream is null");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // compliant
	        factory.setValidating(this.isValidating());
	        factory.setNamespaceAware(this.isNamespaceAware());
	        DocumentBuilder build = factory.newDocumentBuilder();
	        Document doc = build.parse(new InputSource(inputStream));
	        Element root = doc.getDocumentElement();
	        buildFactory(root);
		} catch (IOException | ParserConfigurationException | SAXException e) {
			throw new XmlBuildException("Error when decode xml stream by sax", e);
		}
	}
	
	/**
	 * read from xml and build the factory.
	 * @param root the root of the xml
	 */
	protected void buildFactory(Element root) {
		NodeList nodeList = root.getChildNodes();
		for(int i=0; i<=nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(!(node instanceof Element)) continue;
			Element element = (Element) node;
			loadBean(element);
		}
	}
	
	/**
	 * define what to do with each of node.
	 * @param element the element
	 */
	protected abstract void loadBean(Element element);
}
