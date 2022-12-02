package com.edev.support.xml;

public class XmlBuildException extends RuntimeException {
    public XmlBuildException(String message) {
        super(message);
    }
    public XmlBuildException(String message, Throwable e) {
        super(message, e);
    }
}
