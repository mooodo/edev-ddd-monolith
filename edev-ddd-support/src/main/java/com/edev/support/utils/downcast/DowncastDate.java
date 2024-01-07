package com.edev.support.utils.downcast;

import com.edev.support.exception.OrmException;
import com.edev.support.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class DowncastDate implements DowncastValue<Date> {
    @Override
    public boolean isAvailable(Class<?> clazz) {
        return clazz.equals(Date.class);
    }

    @Override
    public Date downcast(Object value) {
        String str = value.toString();
        if(value instanceof Date) return (Date) value;
        if(str.length()==10) return DateUtils.getDate(str,"yyyy-MM-dd");
        if(str.length()==19) return DateUtils.getDate(str,"yyyy-MM-dd HH:mm:ss");
        if(str.length()==28) return DateUtils.getDate(str,"EEE MMM dd HH:mm:ss zzz yyyy");
        if(str.length()==24) return DateUtils.getDateForUTC(str);
        throw new OrmException("cannot convert to Date!");
    }
}
