/*
 * Created by 2020-06-25 13:44:36 
 */
package com.edev.support.xml;

/**
 * @author fangang
 */
public abstract class AbstractResourceLoader implements ResourceLoader {
	private Filter filter = null;

	/**
	 * @return the file filter, default the xml file filter.
	 */
	@Override
	public Filter getFilter() {
		if(filter==null){
			filter = new Filter(){
				public boolean isSatisfied(String fileName) {
					return fileName.endsWith(".xml") || fileName.endsWith(".XML");
				}};
		}
		return filter;
	}

	/**
	 * @param filter the filter
	 */
	@Override
	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	@Override
	public boolean loadResource(ResourceCallBack callback, String... paths) {
		for (String path : paths) {
			if (!loadResource(callback, path)) return false;
		}
		return true;
	}
}
