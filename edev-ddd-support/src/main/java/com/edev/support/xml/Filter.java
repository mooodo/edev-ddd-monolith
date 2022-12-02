/*
 * created on Dec 3, 2009
 */
package com.edev.support.xml;

/**
 * The file filter
 * @author fangang
 */
public interface Filter {
	
	/**
	 * @param fileName the filename pattern such as "*.xml"
	 * @return whether satisfied the filename pattern.
	 */
	boolean isSatisfied(String fileName);
}
