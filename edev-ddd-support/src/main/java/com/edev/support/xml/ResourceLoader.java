package com.edev.support.xml;

/**
 * The loader for reading resources.
 * @author fangang
 */
public interface ResourceLoader {

	/**
	 * @return the filter
	 */
	public Filter getFilter();

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(Filter filter);

	/**
	 * load the resource by the path.
	 * @param callback the callback method
	 * @param path the pash
	 * @return whether loaded the resource success.
	 */
	public boolean loadResource(ResourceCallBack callback, String path);
	
	/**
	 * load the resource by one more paths.
	 * @param callback the callback method
	 * @param paths the list string of paths
	 * @return whether loaded the resource success.
	 */
	public boolean loadResource(ResourceCallBack callback, String... paths);
}