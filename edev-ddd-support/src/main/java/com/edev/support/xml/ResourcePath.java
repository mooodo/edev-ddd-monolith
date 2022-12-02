/*
 * created on Nov 30, 2009
 */
package com.edev.support.xml;

import java.io.IOException;

/**
 * read resources by loader.
 * @author 范钢
 */
public interface ResourcePath {
	
	/**
	 * read resources by loader
	 * @return the array of Resources
	 */
	public Resource[] getResources() throws IOException;

	/**
	 * @param filter the file filter
	 */
	public void setFilter(Filter filter);
	
	/**
	 * @return the file filter
	 */
	public Filter getFilter();
}
