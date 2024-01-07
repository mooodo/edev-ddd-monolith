/*
 * Created by 2020-06-25 13:13:31 
 */
package com.edev.support.xml;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * load resources with class local path.
 * @author fangang
 */
@Slf4j
public class ClassPathResourceLoader
						extends AbstractResourceLoader implements ResourceLoader {
	private Class<?> clazz = this.getClass();
	
	public ClassPathResourceLoader() { super(); }
	
	/**
	 * @param clazz the class to load resource
	 */
	public ClassPathResourceLoader(Class<?> clazz) {
		if(clazz!=null) this.clazz = clazz;
	}
	@Override
	public boolean loadResource(@NonNull ResourceCallBack callback, @NonNull String path) {
		try {
			Resource resource = new ClassPathResource(path, clazz);
			InputStream is = resource.getInputStream();
			callback.apply(is);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
