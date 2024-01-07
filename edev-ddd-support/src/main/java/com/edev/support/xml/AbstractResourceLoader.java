/*
 * Created by 2020-06-25 13:44:36 
 */
package com.edev.support.xml;

import lombok.Getter;
import lombok.Setter;

/**
 * @author fangang
 */
@Getter
@Setter
public abstract class AbstractResourceLoader implements ResourceLoader {
	private Filter filter = null;

	@Override
	public boolean loadResource(ResourceCallBack callback, String... paths) {
		for (String path : paths) {
			if (!loadResource(callback, path)) return false;
		}
		return true;
	}
}
