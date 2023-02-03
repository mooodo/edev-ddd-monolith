package com.edev.support.utils;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class DowncastHelperTest {
    private final DowncastHelper helper = new DowncastHelper();

    @Test
    public void testLong() {
        Object value = helper.downcast(Long.class, "1");
        assertThat(value, equalTo(1L));
        value = helper.downcast(long.class, "2");
        assertThat(value, equalTo(2L));
    }
    @Test
    public void testInteger() {
        Object value = helper.downcast(Integer.class, "1");
        assertThat(value, equalTo(1));
        value = helper.downcast(int.class, "2");
        assertThat(value, equalTo(2));
    }
    @Test
    public void testDouble() {
        Object value = helper.downcast(Double.class, "1.1");
        assertThat(value, equalTo(1.1D));
        value = helper.downcast(double.class, "2.2");
        assertThat(value, equalTo(2.2D));
    }
    @Test
    public void testFlout() {
        Object value = helper.downcast(Float.class, "1.1");
        assertThat(value, equalTo(1.1F));
        value = helper.downcast(float.class, "2.2");
        assertThat(value, equalTo(2.2F));
    }
    @Test
    public void testShort() {
        Object value = helper.downcast(Short.class, "1");
        assertThat(value, equalTo(Short.valueOf("1")));
        value = helper.downcast(short.class, "2");
        assertThat(value, equalTo(Short.valueOf("2")));
    }
    @Test
    public void testDate() {
        String dateStr = "2000-01-01";
        String format = "yyyy-MM-dd";
        Object value = helper.downcast(Date.class, dateStr);
        Date excepted = DateUtils.getDate(dateStr, format);
        assertThat(value, equalTo(excepted));
    }
    @Test
    public void testDatetime() {
        String dateStr = "2000-01-01 00:00:00";
        String format = "yyyy-MM-dd HH:mm:ss";
        Object value = helper.downcast(Date.class, dateStr);
        Date excepted = DateUtils.getDate(dateStr, format);
        assertThat(value, equalTo(excepted));
    }
    @Test
    public void testDatetimeForUTC() {
        String dateStr = "1979-09-30T16:00:00.000Z";
        Object value = helper.downcast(Date.class, dateStr);
        String exceptedStr = "1979-10-01 00:00:00";
        String format = "yyyy-MM-dd HH:mm:ss";
        Date excepted = DateUtils.getDate(exceptedStr, format);
        assertThat(value, equalTo(excepted));
    }
    @Test
    public void testListOfLong() throws NoSuchFieldException {
        class Test {
            public List<Long> ids;
        }
        Test test = new Test();
        Field field = test.getClass().getField("ids");
        Type type = field.getGenericType();
        List<String> list = Arrays.asList("1","2","3");
        Object value = helper.downcast(type, list);
        assertThat((List<Long>)value, hasItems(1L,2L,3L));
    }
    @Test
    public void testSetOfString() throws NoSuchFieldException {
        class Test {
            public Set<String> names;
        }
        Test test = new Test();
        Field field = test.getClass().getField("names");
        Type type = field.getGenericType();
        List<String> list = Arrays.asList("Jack","Mary","John");
        Object value = helper.downcast(type, list);
        assertThat((Set<String>)value, hasItems("Jack","Mary","John"));
    }
}
