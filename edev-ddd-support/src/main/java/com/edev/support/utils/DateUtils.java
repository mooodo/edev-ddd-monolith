/* 
 * Created by 2018年9月9日
 */
package com.edev.support.utils;

import com.edev.support.exception.ValidException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The utility of the date.
 * @author fangang
 */
public class DateUtils {
	private DateUtils() {}
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * @param date the date
	 * @return convert date to calendar
	 */
	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * @return get calendar of now
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}
	
	/**
	 * @return get now
	 */
	public static Date getNow() {
		return getCalendar().getTime();
	}

	/**
	 * @param string the String
	 * @param format the format
	 * @return convert the string to the date by a certain format
	 */
	public static Date getDate(String string, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(string);
		} catch (ParseException e) {
			throw new ValidException("fail to format date: "+string);
		}
	}

	/**
	 * @param string the String of UTC
	 * @return convert the String of UTC to datetime
	 */
	public static Date getDateForUTC(String string) {
		string = string.replace("Z", " UTC");
		String format = "yyyy-MM-dd'T'HH:mm:ss.SSS Z";
		return getDate(string, format);
	}
	
	/**
	 * @param string the String of date
	 * @return convert the String to date by the default format
	 */
	public static Date getDate(String string) {
		return getDate(string, DATE_FORMAT);
	}
}
