package com.iswater.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @date 2011-09-17, 16:33:04
 * @author long@7road.com
 * @description DateUtil:日期转换工具类
 */
public class DateUtil {

	public static String defaultSimpleFormater = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMAT1 = "yyyy/MM/dd HH:mm:ss";
	
	
	//获取两个Date时间差，按秒返回
	public static long getDateInterval(Date dateOld, Date dateNew) {
		long interval;
		interval = (dateNew.getTime() - dateOld.getTime())/1000;
		return interval;
	}
	
	// 获取当天的开始时间
	public static java.util.Date getDayBegin() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
		
	// 获取本周的开始时间
	public static Date getBeginDayOfWeek() {
		Date date = new Date();
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return getDayStartTime(cal.getTime());
	}
		
	// 获取本月的开始时间
	public static Date getBeginDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		return getDayStartTime(calendar.getTime());
	}
	
	// 获取今年是哪一年
	public static Integer getNowYear() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return Integer.valueOf(gc.get(1));
	}

	// 获取本月是哪一月
	public static int getNowMonth() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return gc.get(2) + 1;
	}
	
	// 获取某个日期的开始时间
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
				0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}
	

	/**
	 * 获取一天的开始时间 先转换成 yyyy-MM-dd 这样的字串就只保留了天数，再转成long 类型的时间戳 本来想 time /
	 * (24*3600*1000) *24*3600*1000 这样就能把一天的时间里的多的那部分去掉，但是不知道为什么通过这样的方式总是取出来的时间是
	 * 当天的八点，如果在计算后的时间减掉 八小时 时间改变之后却又无法控制，暂时没有找到比较好的方法.
	 * 
	 * @param time
	 * @return long 2013-8-6 下午03:34:41
	 * @throws ParseException
	 */
	public static long getDayStartTime(long time) throws ParseException {
		Date date = new Date(time);
		String fmt = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.parse(sdf.format(date)).getTime();
	}

	public static Long getNowTime() throws ParseException {
		Calendar todayStart = Calendar.getInstance();
		return todayStart.getTime().getTime();
	}

	public static boolean isToday(Date date) {
		String dateStr = format(date, "yyyy-MM-dd");
		String today = format(new Date(), "yyyy-MM-dd");

		return today.equals(dateStr);
	}

	public static Long getDayBegin(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(sdf.parse(date));
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		return todayStart.getTime().getTime();
	}

	public static Long getDayEnd(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(sdf.parse(date));
		todayStart.set(Calendar.HOUR_OF_DAY, 23);
		todayStart.set(Calendar.MINUTE, 59);
		todayStart.set(Calendar.SECOND, 59);
		return todayStart.getTime().getTime();
	}

	public static Long getDayBegin(Date date) throws ParseException {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		return todayStart.getTime().getTime();
	}

	public static Date getDayBeginDate(Date date) throws ParseException {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		return todayStart.getTime();
	}

	public static Long getDayEnd(Date date) throws ParseException {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		todayStart.set(Calendar.HOUR_OF_DAY, 23);
		todayStart.set(Calendar.MINUTE, 59);
		todayStart.set(Calendar.SECOND, 59);
		return todayStart.getTime().getTime();
	}

	public static Long getTodayBegin() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		return todayStart.getTime().getTime();
	}

	public static Long getTodayEndTime() {
		Calendar yesEnd = Calendar.getInstance();
		yesEnd.set(Calendar.HOUR_OF_DAY, 23);
		yesEnd.set(Calendar.MINUTE, 59);
		yesEnd.set(Calendar.SECOND, 59);
		return yesEnd.getTime().getTime();
	}

	public static Long getYesterdayStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.add(Calendar.DATE, -1);
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		return todayStart.getTime().getTime();
	}

	public static Long getYesterdayEndTime() {
		Calendar yesEnd = Calendar.getInstance();
		yesEnd.add(Calendar.DATE, -1);
		yesEnd.set(Calendar.HOUR_OF_DAY, 23);
		yesEnd.set(Calendar.MINUTE, 59);
		yesEnd.set(Calendar.SECOND, 59);
		return yesEnd.getTime().getTime();
	}

	public static int getMinute24(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);

		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);

		return hour * 60 + minutes;

	}

	/**
	 * 把某种格式的字串时间转换成long类型
	 * 
	 * @param
	 * @return
	 * @throws ParseException
	 *             long 2013-8-16 下午02:55:34
	 */
	public static long pasreStringToLong(String date, String fmt) throws ParseException {
		String fmtdefault = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = null;
		if (fmt == null || fmt.equals("")) {
			sdf = new SimpleDateFormat(fmtdefault);
		} else {
			sdf = new SimpleDateFormat(fmt);
		}
		return sdf.parse(date).getTime();
	}

	/**
	 * 默认简单日期字符串
	 *
	 * @return
	 */
	public static String getDefaultSimpleFormater() {
		return defaultSimpleFormater;
	}

	/**
	 * 设置默认简单日期格式字符串
	 *
	 * @param defaultFormatString
	 */
	public static void setDefaultSimpleFormater(String defaultFormatString) {
		DateUtil.defaultSimpleFormater = defaultFormatString;
	}

	/**
	 * 格式化日期
	 *
	 * @param date
	 * @param formatString
	 * @return
	 */
	public static String format(Date date, String formatString) {
		SimpleDateFormat df = new SimpleDateFormat(formatString);
		return df.format(date);
	}

	/**
	 * 格式化日期(使用默认格式)
	 *
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, defaultSimpleFormater);
	}

	/**
	 * 转换成日期
	 *
	 * @param dateString
	 * @param formatString
	 * @return
	 */
	public static Date parse(String dateString, String formatString) {
		SimpleDateFormat df = new SimpleDateFormat(formatString);
		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 转换成日期(使用默认格式)
	 *
	 * @param dateString
	 * @return
	 */
	public static Date parse(String dateString) {
		return parse(dateString, defaultSimpleFormater);
	}

	/**
	 * 昨天
	 *
	 * @return
	 */
	public static Date yesterday() {
		return addDay(-1);
	}

	/**
	 * 明天
	 *
	 * @return
	 */
	public static Date tomorrow() {
		return addDay(1);
	}

	/**
	 * 现在
	 *
	 * @return
	 */
	public static Date now() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 按日加
	 *
	 * @param value
	 * @return
	 */
	public static Date addDay(int value) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, value);
		return now.getTime();
	}

	/**
	 * 按日加,指定日期
	 *
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addDay(Date date, int value) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.DAY_OF_YEAR, value);
		return now.getTime();
	}

	/**
	 * 按月加
	 *
	 * @param value
	 * @return
	 */
	public static Date addMonth(int value) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, value);
		return now.getTime();
	}

	/**
	 * 按月加,指定日期
	 *
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addMonth(Date date, int value) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.MONTH, value);
		return now.getTime();
	}

	/**
	 * 按年加
	 *
	 * @param value
	 * @return
	 */
	public static Date addYear(int value) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, value);
		return now.getTime();
	}

	/**
	 * 按年加,指定日期
	 *
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addYear(Date date, int value) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.YEAR, value);
		return now.getTime();
	}

	/**
	 * 按小时加
	 *
	 * @param value
	 * @return
	 */
	public static Date addHour(int value) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR_OF_DAY, value);
		return now.getTime();
	}

	/**
	 * 按小时加,指定日期
	 *
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addHour(Date date, int value) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.HOUR_OF_DAY, value);
		return now.getTime();
	}

	/**
	 * 按分钟加
	 *
	 * @param value
	 * @return
	 */
	public static Date addMinute(int value) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, value);
		return now.getTime();
	}

	/**
	 * 按分钟加,指定日期
	 *
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addMinute(Date date, int value) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.MINUTE, value);
		return now.getTime();
	}

	/**
	 * 年份
	 *
	 * @return
	 */
	public static int year() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR);
	}

	/**
	 * 月份
	 *
	 * @return
	 */
	public static int month() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MONTH);
	}

	/**
	 * 日(号)
	 *
	 * @return
	 */
	public static int day() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 小时(点)
	 *
	 * @return
	 */
	public static int hour() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.HOUR);
	}

	/**
	 * 分钟
	 *
	 * @return
	 */
	public static int minute() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MINUTE);
	}

	/**
	 * 秒
	 *
	 * @return
	 */
	public static int second() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.SECOND);
	}

	/**
	 * 星期几(礼拜几)
	 *
	 * @return
	 */
	public static int weekday() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 是上午吗?
	 *
	 * @return
	 */
	public static boolean isAm() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.AM_PM) == 0;
	}

	/**
	 * 是下午吗?
	 *
	 * @return
	 */
	public static boolean isPm() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.AM_PM) == 1;
	}

	/**
	 * 
	 * @return 当前的时间戳 yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowUnix() {

		return DateTimeToUnix(new Date());

	}

	/**
	 * 获取时间戳
	 * 
	 * @param date
	 *            需要转化时间戳的时间
	 * @return 时间戳
	 */
	public static String DateTimeToUnix(Date date) {

		return Long.toString(date.getTime());

	}

	/**
	 * 获取格式化时间字符串
	 *
	 * @return 格式化时间字符串
	 */
	public static String formatNow() {
		return DateUtil.format(DateUtil.now());
	}

	public static String formatNow(String format) {
		return DateUtil.format(DateUtil.now(), format);
	}

	public static Timestamp getCurrentTimestamp() {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		return currentTime;
	}

	/**
	 * 将日期转换成当前月份1号的日期
	 *
	 * @return 当前月的1号所对应的日期
	 */
	public static Date coverToHourDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 将日期转换成当前月份1号的日期
	 *
	 * @return 当前月的1号所对应的日期
	 */
	public static Date getFirstDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new Date(cal.getTimeInMillis());
	}

	public static Date getLastDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 创建时间： 2013-6-17 上午10:42:59 创建人：cutter.li 参数： 把从db中取出来的sql的是时间转换成date的时间
	 * 返回值： Date 方法描述 :
	 */
	public static Date transforFromSqlToUtilDate(java.sql.Date sqlDate) {

		if (null != sqlDate) {
			return new Date(sqlDate.getTime());
		}

		return null;
	}

	/**
	 * 创建时间： 2013-6-17 上午11:44:32 创建人：cutter.li 参数： 返回值： java.sql.Date 方法描述
	 * :把应用系统的时间转换成sql的时间
	 */
	public static java.sql.Date transforFromUtilToSqlDate(Date utilDate) {

		if (null != utilDate) {
			return new java.sql.Date(utilDate.getTime());
		}

		return null;
	}

	public static Date yesterday(int count) {
		return addDay(count);
	}

	public static Date getHourUp(int count) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, -count);
		return now.getTime();
	}

	public static void main(String[] args) {
		System.out.println(DateUtil.format(new Date(DateUtil.getYesterdayEndTime()), DATE_TIME_FORMAT));
		System.out.println(DateUtil.format(new Date(DateUtil.getYesterdayStartTime()), DATE_TIME_FORMAT));
	}

}
