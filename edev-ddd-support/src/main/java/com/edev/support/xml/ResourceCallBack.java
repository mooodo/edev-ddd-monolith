/*
 * Created by 2020-06-25 14:15:32 
 */
package com.edev.support.xml;

import java.io.InputStream;

/**
 * The callback function for reading resources.
 * @author fangang
 */
@FunctionalInterface
public interface ResourceCallBack {
	/**
	 * @param inputStream the input stream
	 */
	void apply(InputStream inputStream);
}
