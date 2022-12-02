/*
 * created on Nov 30, 2009
 */
package com.edev.support.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * search and read file with java.io.File
 * @author fangang
 */
public class FileResource implements Resource, ResourcePath {
	private File file;
	private Filter filter = null;
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * Default constructor
	 */
	public FileResource() {
		super();
	}
	/**
	 * Constructor for file
	 * @param file the file
	 */
	public FileResource(File file) {
		super();
		this.file = file;
	}
	
	/**
	 * read files with the code <code>new FileInputStream(file)</code>,
	 * and return null if it is a directory.
	 * @return InputStream
	 */
	public InputStream getInputStream() throws IOException {
		if(file==null||file.isDirectory()){
			return null;
		}
		if(filter!=null&&!filter.isSatisfied(file.getName())){
			return null;
		}
		return new FileInputStream(file);
	}
	
	/**
	 * get all of the files in the path with the method <code>File.listFiles()</code>
	 * @return Resource[]
	 */
	public Resource[] getResources() throws IOException {
		if(file==null) return new Resource[]{};
		if(file.isDirectory()){
			File[] files = file.listFiles();
			if(files==null) return new Resource[]{};
			List<FileResource> fileLoaders = new ArrayList<>();
			for (File value : files) {
				if (filter != null && !filter.isSatisfied(value.getName())) {
					continue;
				}
				fileLoaders.add(new FileResource(value));
			}
			return fileLoaders.toArray(new Resource[0]);
		}
		Resource resource = new FileResource(file);
		resource.setFilter(this.getFilter());
		return new Resource[]{resource};
	}
	
	@Override
	public String getDescription() {
		try {
			return "FileResource:[file:" + file.getCanonicalPath() + "]";
		} catch (IOException e) {
			return "";
		}
	}

	@Override
	public Filter getFilter() {
		return this.filter;
	}
	
	@Override
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	
	@Override
	public String getFileName() {
		if(file==null) return "";
		String fileName = file.getName();
		if(fileName.regionMatches(0, "\\", 0, 2))
			fileName = fileName.replace("\\\\", "/");
		return fileName;
	}
}
