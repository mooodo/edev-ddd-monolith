/*
 * Created by 2020-06-25 13:38:10 
 */
package com.edev.support.xml;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;

/**
 * load resources with the url.
 * @author fangang
 */
@Slf4j
public class UrlResourceLoader 
					extends AbstractResourceLoader implements ResourceLoader {
	private Class<?> clazz = this.getClass();
	
	public UrlResourceLoader() { super(); }
	
	/**
	 * @param clazz the class help to load resource.
	 */
	public UrlResourceLoader(Class<?> clazz) {
		if(clazz!=null) this.clazz = clazz;
	}
	@Override
	public boolean loadResource(@NonNull ResourceCallBack callback, @NonNull String path) {
		try {
			PathMatchingResourcePatternResolver resolver =
					new PathMatchingResourcePatternResolver(this.clazz.getClassLoader());
			Resource[] loaders = resolver.getResources(path);
			for (Resource loader : loaders) {
				InputStream is = loader.getInputStream();
				callback.apply(is);
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
