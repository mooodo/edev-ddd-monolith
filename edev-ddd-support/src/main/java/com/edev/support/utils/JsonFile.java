package com.edev.support.utils;

import com.edev.support.exception.OrmException;
import com.edev.support.xml.FileResourceLoader;
import com.edev.support.xml.ResourceLoader;
import com.edev.support.xml.UrlResourceLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonFile {
    private JsonFile() {}
    public static String read(String file) {
        StringBuilder buffer = new StringBuilder();
        boolean success;
        ResourceLoader loader;
        if(!file.endsWith(".json"))
            throw new OrmException("Please input json file[%s]",file);

        loader = new UrlResourceLoader();
        success = loader.loadResource(inputStream -> readStream(inputStream, buffer), file);
        if (success) return buffer.toString();

        loader = new FileResourceLoader();
        success = loader.loadResource(inputStream -> readStream(inputStream, buffer), file);
        if (success) return buffer.toString();

        throw new OrmException("No found the json file[%s]",file);
    }

    private static void readStream(InputStream inputStream, StringBuilder buffer) {
        if(inputStream==null) return;
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        int i;
        try {
            while ((i = reader.read()) != -1) buffer.append((char)i);
        } catch (IOException e) {
            throw new OrmException("failure to read json file",e);
        }
    }
}
