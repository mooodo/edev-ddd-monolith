/*
 * Created by 2020-06-25 13:13:31 
 */
package com.edev.support.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * load resources with class local path.
 * @author fangang
 */
public class ClassPathResourceLoader 
						extends AbstractResourceLoader implements ResourceLoader {
	private static final Log log = LogFactory.getLog(ClassPathResourceLoader.class);
	private Class<?> clazz = this.getClass();
	
	public ClassPathResourceLoader() { super(); }
	
	/**
	 * @param clazz the class to load resource
	 */
	public ClassPathResourceLoader(Class<?> clazz) {
		if(clazz!=null) this.clazz = clazz;
	}
	@Override
	public boolean loadResource(ResourceCallBack callback, String path) {
		try {
			Resource resource = new ClassPathResource(path, clazz);
			log.debug(resource);
			InputStream is = resource.getInputStream();
			callback.apply(is);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
