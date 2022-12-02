package com.edev.support.web;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController {

    protected Map<String, Object> mergeDataToJson(Map<String, Object> json, HttpServletRequest request) {
        if (json == null) json = new HashMap<>();
        if (request == null) return json;
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements(); ) {
            String key = e.nextElement();
            String value = request.getParameter(key);
            json.put(key, value);
        }
        return json;
    }
}
