package com.edev.support.utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class DateUtilsTest {
	@Test
	public void testGetDateForDate() {
		String string = "2020-01-01";
		String format = "yyyy-MM-dd";
		Date date = DateUtils.getDate(string, format);
		String dateString = DateUtils.getStringOfDate(date, format);
		assertThat(dateString, equalTo(string));
	}
	@Test
	public void testGetDateForDateTime() {
		String string = "2020-01-01 23:59:59";
		String format = "yyyy-MM-dd HH:mm:ss";
		Date date = DateUtils.getDate(string, format);
		String dateString = DateUtils.getStringOfDate(date, format);
		assertThat(dateString, equalTo(string));
	}
	@Test
	public void testGetDateForUTC() {
		String string = "1979-09-30T16:00:00.000Z";
		Date date = DateUtils.getDateForUTC(string);
		String dateString = DateUtils.getStringOfDate(date);
		assertThat(dateString, equalTo("1979-10-01 00:00:00"));
	}
	@Test
	public void testGetCalendar() {
		Date date = DateUtils.getDate("2020-07-09", "yyyy-MM-dd");
		Calendar calendar = DateUtils.getCalendar(date);
		assertThat(calendar.getTime(), equalTo(date));
	}
	@Test
	public void testGetYear() {
		Date date = DateUtils.getDate("2020-07-09", "yyyy-MM-dd");
		assertThat(DateUtils.getYear(date), equalTo(2020));
	}
	@Test
	public void testGetFirstDayOfYear() {
		Date date = DateUtils.getDate("2020-07-09 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date excepted = DateUtils.getDate("2020-01-01 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date theFirstDayOfYear = DateUtils.getFirstDayOfYear(date);
		assertThat(theFirstDayOfYear, equalTo(excepted));
	}
	@Test
	public void testGetLastDayOfYear() {
		Date date = DateUtils.getDate("2020-07-09 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date excepted = DateUtils.getDate("2020-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date theLastDayOfYear = DateUtils.getLastDayOfYear(date);
		assertThat(theLastDayOfYear, equalTo(excepted));
	}
	@Test
	public void testGetYearsBetween() {
		Date theFirstDay = DateUtils.getDate("2020-07-09 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date theLastDay = DateUtils.getDate("2022-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
		int yearsBetween = DateUtils.getYearsBetween(theFirstDay, theLastDay);
		assertThat(yearsBetween, equalTo(2));
	}
	@Test
	public void testGetMonth() {
		Date date = DateUtils.getDate("2020-07-09 23:59:59", "yyyy-MM-dd HH:mm:ss");
		int month = DateUtils.getMonth(date);
		assertThat(month, equalTo(7));
	}
	@Test
	public void testGetFirstDayOfMonth() {
		Date date = DateUtils.getDate("2020-01-09 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date excepted = DateUtils.getDate("2020-01-01 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date theFirstDayOfMonth = DateUtils.getFirstDayOfMonth(date);
		assertThat(theFirstDayOfMonth, equalTo(excepted));
	}
	@Test
	public void testGetLastDayOfMonth() {
		Date date = DateUtils.getDate("2020-06-09 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date excepted = DateUtils.getDate("2020-06-30 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date theLastDayOfMonth = DateUtils.getLastDayOfMonth(date);
		assertThat(theLastDayOfMonth, equalTo(excepted));

		Date date1 = DateUtils.getDate("2020-12-09 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date excepted1 = DateUtils.getDate("2020-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date theLastDayOfMonth1 = DateUtils.getLastDayOfMonth(date1);
		assertThat(theLastDayOfMonth1, equalTo(excepted1));
	}
	@Test
	public void testMonthsBetween() {
		Date theFirstDay = DateUtils.getDate("2020-07-09 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date theLastDay = DateUtils.getDate("2020-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
		int theMonthsBetween = DateUtils.getMonthsBetween(theFirstDay, theLastDay);
		assertThat(theMonthsBetween, equalTo(5));

		Date theFirstDay1 = DateUtils.getDate("2020-07-09 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date theLastDay1 = DateUtils.getDate("2022-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
		int theMonthsBetween1 = DateUtils.getMonthsBetween(theFirstDay1, theLastDay1);
		assertThat(theMonthsBetween1, equalTo(29));
	}
	@Test
	public void testDatesBetween() {
		Date theFirstDay = DateUtils.getDate("2020-07-09 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date theLastDay = DateUtils.getDate("2020-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
		long theDatesBetween = DateUtils.getDatesBetween(theFirstDay, theLastDay);
		assertThat(theDatesBetween, equalTo(175L));

		Date theFirstDay1 = DateUtils.getDate("2020-07-09 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date theLastDay1 = DateUtils.getDate("2022-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
		long theDatesBetween1 = DateUtils.getDatesBetween(theFirstDay1, theLastDay1);
		assertThat(theDatesBetween1, equalTo(905L));
	}
}
