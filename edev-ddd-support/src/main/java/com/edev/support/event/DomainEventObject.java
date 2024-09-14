package com.edev.support.event;

import com.alibaba.fastjson.JSONObject;
import com.edev.support.ddd.DddException;
import com.edev.support.entity.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class DomainEventObject {
    private String eventId;
    private String publisher;
    private Date publishTime;
    private Object data;

    public DomainEventObject() {}

    public DomainEventObject(String eventId, String publisher, Date publishTime, Object data) {
        this.eventId = eventId;
        this.publisher = publisher;
        this.publishTime = publishTime;
        this.data = data;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public <E extends Entity<S>, S extends Serializable> E convertToEntity(Class<E> clazz) {
        if(data instanceof Map)
            return (new JSONObject((Map<String, Object>) data)).toJavaObject(clazz);
        throw new DddException("The data isn't a map!");
    }

    public Map<String, Object> convertToMap() {
        if(data instanceof Map) return (Map<String, Object>) data;
        throw new DddException("The data isn't a map!");
    }
}
