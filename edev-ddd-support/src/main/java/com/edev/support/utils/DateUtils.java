/* 
 * Created by 2018年9月9日
 */
package com.edev.support.utils;

import com.edev.support.exception.ValidException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * The utility of the date.
 * @author fangang
 */
public class DateUtils {
	private DateUtils() {}
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GTM+8");

	/**
	 * @param date the date
	 * @return the calendar
	 */
	public static Calendar getCalendar(Date date){
		Calendar calendar = getCalendar();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * @return the calendar of now
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance(TIME_ZONE);
	}
	
	/**
	 * @return the now
	 */
	public static Date getNow() {
		return getCalendar().getTime();
	}

	/**
	 * @param string the String
	 * @param format the date format
	 * @return convert the string to the date by a certain format
	 */
	public static Date getDate(String string, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TIME_ZONE);
		try {
			return sdf.parse(string);
		} catch (ParseException e) {
			throw new ValidException("fail to format date: "+string);
		}
	}

	/**
	 * @param string the string of the date
	 * @return convert the date to default format
	 */
	public static Date getDate(String string) {
		return getDate(string, DATE_FORMAT);
	}

	/**
	 * @param year the year
	 * @param month the month
	 * @param date the date
	 * @return the date
	 */
	public static Date getDate(int year, int month, int date) {
		Calendar calendar = getCalendar();
		calendar.set(year, month, date);
		return calendar.getTime();
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
	 * @param date the date
	 * @param format the date format
	 * @return the string of the date
	 */
	public static String getStringOfDate(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TIME_ZONE);
		return sdf.format(date);
	}

	/**
	 * @param date the date
	 * @return the string of the date with default format
	 */
	public static String getStringOfDate(Date date){
		return getStringOfDate(date, DATE_FORMAT);
	}

	/**
	 * @param date the date
	 * @return the year of the date
	 */
	public static int getYear(Date date){
		return getCalendar(date).get(Calendar.YEAR);
	}

	/**
	 * @return this year
	 */
	public static int getThisYear(){
		return getYear(getNow());
	}

	/**
	 * @param date the date
	 * @return the first day of the year
	 */
	public static Date getFirstDayOfYear(Date date){
		Calendar calendar = getCalendar(date);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * @param date the date
	 * @return the last day of the year
	 */
	public static Date getLastDayOfYear(Date date){
		Calendar calendar = getCalendar(date);
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		return calendar.getTime();
	}

	/**
	 * @param firstDate the first date
	 * @param lastDate the last date
	 * @return the years between the two dates
	 */
	public static int getYearsBetween(Date firstDate, Date lastDate){
		return getYear(lastDate) - getYear(firstDate);
	}

	/**
	 * @param date the date
	 * @return the month of the date
	 */
	public static int getMonth(Date date){
		return getCalendar(date).get(Calendar.MONTH)+1;
	}

	/**
	 * @return this month
	 */
	public static int getThisMonth(){
		return getMonth(getNow());
	}

	/**
	 * @param date  the date
	 * @return the first date of the month
	 */
	public static Date getFirstDayOfMonth(Date date){
		Calendar calendar = getCalendar(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(year, month, 1);
		return calendar.getTime();
	}

	/**
	 * @param date the date
	 * @return the last day of the month
	 */
	public static Date getLastDayOfMonth(Date date){
		Calendar calendar = getCalendar(date);
		calendar.add(Calendar.MONTH, 1);
		Date theFirstDayOfNextMonth = getFirstDayOfMonth(calendar.getTime());
		calendar = getCalendar(theFirstDayOfNextMonth);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * @param firstDate the first date
	 * @param lastDate the last date
	 * @return the months between the two dates
	 */
	public static int getMonthsBetween(Date firstDate, Date lastDate){
		int yearsBetween = getYearsBetween(firstDate, lastDate);
		Calendar calendar = getCalendar(lastDate);
		calendar.set(Calendar.YEAR, getYear(firstDate));
		int monthsBetween = getMonth(calendar.getTime()) - getMonth(firstDate);
		return yearsBetween*12 + monthsBetween;
	}

	/**
	 * @param firstDate the first date
	 * @param lastDate the last date
	 * @return the dates between the two dates
	 */
	public static long getDatesBetween(Date firstDate, Date lastDate){
		Calendar calendar1 = getCalendar(firstDate);
		Calendar calendar2 = getCalendar(lastDate);
		long between = calendar2.getTimeInMillis()-calendar1.getTimeInMillis();
		return between/(24*60*60*1000);
	}
}
