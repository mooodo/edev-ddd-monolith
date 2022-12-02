/*
 * Created by 2020-06-25 13:38:10 
 */
package com.edev.support.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;

/**
 * load resources with the url.
 * @author fangang
 */
public class UrlResourceLoader 
					extends AbstractResourceLoader implements ResourceLoader {
	private static final Log log = LogFactory.getLog(UrlResourceLoader.class);
	private Class<?> clazz = this.getClass();
	
	public UrlResourceLoader() { super(); }
	
	/**
	 * @param clazz the class help to load resource.
	 */
	public UrlResourceLoader(Class<?> clazz) {
		if(clazz!=null) this.clazz = clazz;
	}
	@Override
	public boolean loadResource(ResourceCallBack callback, String path) {
		try {
			PathMatchingResourcePatternResolver resolver =
					new PathMatchingResourcePatternResolver(this.clazz.getClassLoader());
			Resource[] loaders = resolver.getResources(path);
			for (Resource loader : loaders) {
				printLog(loader);
				InputStream is = loader.getInputStream();
				callback.apply(is);
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	private void printLog(Resource resource) {
		try {
			log.debug(resource.getFile().getCanonicalPath());
		} catch (IOException ignored) {
			//do nothing
		}
	}
}
