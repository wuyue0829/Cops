package com.pengdikj.cops.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
    //一天 ,  一分钟
	public static long ONE_DAY = 24 * 60 * 60 * 1000 , ONE_MINITE =  60 * 1000; 

	/**
	 * 获取当前时间
	 */
	public static Date getCurrent()
	{
		Date currentDate = new Date();
		return currentDate;
	}
	/**
	 * 获取当天起始时间
	 */  
	public static Date getCurrentdate()
	{
		String currentDate = getFormatDate("yyyyMMdd", 
				new Date());
		Date date = getDate("yyyyMMddHHmmss", currentDate + 
				"000000");
		return date;
	}
	/**
	 * 获取当天结束时间
	 */ 
	public static Date getCurrentdateEnd()
	{
		String currentDate = getFormatDate("yyyyMMdd", 
				new Date());
		Date date = getDate("yyyyMMddHHmmss", currentDate + 
				"235959");
		return date;
	}

	/**
	 * 获取date所在的月的星期数
	 * @param date 日期 
	 */ 
	public static int getMonthWeekCount(Date date)
	{
		Date monthenddate = getMonthEndDay(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(monthenddate);
		return cal.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获取当前月份
	 */
	public static Date getCurrentMonth()
	{
		return getMonthFirstDay(new Date());
	}
	/**
	 * 获取date所在的月的第一天的日期
	 * @param date 日期 
	 */ 
	public static Date getMonthFirstDay(Date date)
	{
		String month = getFormatDate("yyyyMM", date) + "01";
		Date firstday = null;

		firstday = getDate("yyyyMMdd", month);

		return firstday;
	}

	/**
	 * 获取date所在的月的最后一天的日期
	 * @param date 日期 
	 */
	public static String getMonthEndDayStr(Date date)
	{
		return getFormatDate("yyyy-MM-dd HH:mm:ss",getMonthEndDay(date));
	}
	/**
	 * 获取date所在的月的第一天的日期
	 * @param date 日期 
	 */ 
	public static String getMonthFirstDayStr(Date date)
	{
		return getFormatDate("yyyy-MM-dd HH:mm:ss",getMonthFirstDay(date));
	}
	/**
	 * 获取date所在的星期的星期一的日期
	 * @param date 日期 
	 */
	public static Date getFirstDayOfWeek(Date date)
	{
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DATE, c.getFirstDayOfWeek());
		return c.getTime();
	}
	/**
	 * 获取date所在的星期的星期天的日期
	 * @param date 日期 
	 */
	public static Date getFirstDayOfWeekSunDay(Date date)
	{
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getFirstDayOfWeek());
		return c.getTime();
	}
	/**
	 * 获取date所在的星期的起始时间字符串,格式为yyyy-MM-dd 00:00:00
	 * @param date 日期 
	 */
	public static String getWeekFirstDayStr(Date date)
	{
		String str = getFormatDate("yyyy/MM/dd", getFirstDayOfWeek(date));
		String strArr = str.replace("/", "-");
		return strArr + " 00:00:00";
	}
	/**
	 * 获取date日的起始时间字符串,格式为yyyy/MM/dd 00:00:00
	 * @param date 日期 
	 */
	public static String getDayStartStr(Date date)
	{
		String str = getFormatDate("yyyy-MM-dd", date);
		return str + " 00:00:00";
	}
	/**
	 * 获取date日的结束时间字符串,格式为yyyy/MM/dd 00:00:00
	 * @param date 日期 
	 */
	public static String getDayEndStr(Date date)
	{
		String str = getFormatDate("yyyy-MM-dd", date);
		return str + " 23:59:59";
	}

	/**
	 * 获取date日的起始月第一天,格式为yyyy/MM/01 00:00:00
	 * @param date 日期 
	 */
	public static String getDayStartStrMon(Date date)
	{
		String str = getFormatDate("yyyy-MM", date);
		return str +"-01" + " 00:00:00";
	}
	/**
	 * 获取date日的结束时间字符串,格式为yyyy/MM/dd 00:00:00
	 * @param date 日期 
	 */
	public static String getDayEndStrMon(Date date)
	{
		String str = getFormatDate("yyyy-MM", date);
		return str +"-31" + " 23:59:59";
	}

	/**
	 * 获取date日的结束时间字符串,格式为yyyy-MM-dd 00:00:00
	 * @param date 日期 
	 */
	public static Date getDayEndDate(Date date)
	{
		String str = getFormatDate("yyyy-MM-dd", date);
		str+= " 23:59:59";
		return getDate("yyyy-MM-dd HH:mm:ss", str);
	}


	/**
	 * 获取date月的结束时间字符串,格式为yyyy-MM-dd 00:00:00
	 * @param date 日期 
	 */
	public static Date getDayEndDateMon(Date date)
	{
		String str = getFormatDate("yyyy-MM", date);
		str+= "-31 23:59:59";
		return getDate("yyyy-MM-dd HH:mm:ss", str);
	}

	/**
	 * 获取date所在的星期的星期天的日期
	 * @param date 日期 
	 */
	public static Date getLastDayOfWeek(Date date)
	{
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getFirstDayOfWeek() + 6);
		return c.getTime();
	}

	/**
	 * 获取date所在的星期结束时间字符串,格式为yyyy-MM-dd 23:59:59
	 * @param date 日期 
	 */
	public static String getWeekEndDayStr(Date date)
	{
		String str = getFormatDate("yyyy/MM/dd", getFirstDayOfWeek(date));
		String strArr = str.replace("/", "-");
		return strArr + " 23:59:59";
	}
	/**
	 * 获取date所在的月的最后一天的日期
	 * @param date 日期 
	 */
	public static Date getMonthEndDay(Date date)
	{
		Date endday = dateAdd(3, -1, dateAdd(
				2, 1, getMonthFirstDay(date)));
		return endday;
	}

	/**
	 * 获取date日的起始时间字符串,格式为yyyy-MM-dd 00:00:00
	 * @param date 日期 
	 */
	public static Date getDayStartDate(Date date)
	{
		String str = getFormatDate("yyyy-MM-dd", date);
		str+= " 00:00:00";
		return getDate("yyyy-MM-dd HH:mm:ss", str);
	}

	/**
	 * 获取date月的起始时间字符串,格式为yyyy-MM-dd 00:00:00
	 * @param date 日期 
	 */
	public static Date getDayStartDateMon(Date date)
	{
		String str = getFormatDate("yyyy-MM", date);
		str+= "-01 00:00:00";
		return getDate("yyyy-MM-dd HH:mm:ss", str);
	}


	/**
	 * 获取最后一天的日期 系统设定为29991231
	 */
	public static Date getEndDate()
	{
		return getDate("yyyyMMdd", "29991231");
	}

	/**
	 * 获取服务的最后一天的日期 系统设定为99991231
	 */
	public static Date getServiceEndDate()
	{
		return getDate("yyyyMMdd", "99991231");
	}
	/**
	 * 获取起始天的日期 系统设定为19900101
	 */
	public static Date getStartDate()
	{
		return getDate("yyyyMMdd", "19900101");
	}

	/**
	 * 获取起始天的日期 系统设定为19900101
	 */
	public static String getStartDateStr()
	{
		return "1990-01-01 00:00:00";
	}
	/**
	 * 获取指定月份的起始时间
	 * @param month 月份
	 */
	public static Date getSelectMonth(String month)
	{
		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String date = sdf.format(time);
		date = date + "-" + month + "-01 00:00:00.0";
		sdf.applyPattern("yyyy-MM");
		Date curDate = null;
		try {
			curDate = sdf.parse(date);
		}
		catch (Exception e) {
		}
		return curDate;
	}

	/**
	 * 根据生日获取年龄
	 * @param birthday 生日日期字符串
	 */
	public static String getAge(String birthday)
	{
		if ((birthday == null) || ("".equals(birthday)))
			return "0";
		Date timenow = new Date();
		Date birth = null;

		birth = getDate("yyyyMMdd", birthday);
		int byear = Integer.parseInt(getFormatDate("yyyy", birth));
		int nyear = Integer.parseInt(getFormatDate("yyyy", timenow));
		int bmonth = Integer.parseInt(getFormatDate("MM", birth));
		int nmonth = Integer.parseInt(getFormatDate("MM", timenow));
		int age = nyear - byear;
		if (age < 0)
			return "0";
		if (nmonth < bmonth)
			--age;
		return String.valueOf(age);
	}
	/**
	 * 根据生日获取年龄
	 * @param birthday 生日日期
	 */
	public static String getAge(Date birthday)
	{
		if (birthday == null)
			return "0";
		Date timenow = new Date();
		int byear = Integer.parseInt(getFormatDate("yyyy", birthday));
		int nyear = Integer.parseInt(getFormatDate("yyyy", timenow));
		int bmonth = Integer.parseInt(getFormatDate("MM", birthday));
		int nmonth = Integer.parseInt(getFormatDate("MM", timenow));
		int age = nyear - byear;
		if (age < 0)
			return "0";
		if (nmonth < bmonth)
			--age;
		return String.valueOf(age);
	}
	/**
	 * 根据年龄获取生日年份
	 * @param age 年龄
	 */
	public static String getBirthday(String age)
	{
		if (age == null)
			return null;

		String nowyear = getFormatDate("yyyy", new Date());

		Long birthday = Long.parseLong(nowyear) - Long.parseLong(age);

		return birthday.toString();
	}
	/**
	 * 取得日期
	 */
	public static String getToday()
	{
		return getFormatDate("yyyy-MM-dd",new Date());
	}
	public static String getNow()
	{
		return getFormatDate("yyyy-MM-dd HH:mm:ss",new Date());
	}
	public static String getNowTime()
	{
		return new Date().getTime()+"";
	}
	public static String getYesterday()
	{
		return getFormatDate("yyyy-MM-dd",dateAdd(3, -1, new Date()));
	}
	public static String getBeforeYesterday()
	{
		return getFormatDate("yyyy-MM-dd",dateAdd(3, -2, new Date()));
	}
	public static String getTomorrow()
	{
		return getFormatDate("yyyy-MM-dd",dateAdd(3, 1, new Date()));
	}
	/**
	 * 取得日期
	 */
	public static String getWelcome()
	{
		Date date = new Date();
		int hour = date.getHours();
		if(hour>=1&&hour<=8){
			return "早上好！";
		}
		if(hour>=9&&hour<=10){
			return "上午好！";
		}
		if(hour>=11&&hour<=13){
			return "中午好！";
		}
		if(hour>=14&&hour<=18){
			return "下午好！";
		}
		if(hour>=19||hour<1){
			return "晚上好！";
		}
		return "早上好！";
	}
	/**
	 * 根据生日日期字符串和指定日期获取指定日期时的年龄
	 * @param birthday 生日日期字符串
	 * @param curDate  指定日期
	 */
	public static String getAge(Date birthday, Date curDate)
	{
		if (birthday == null)
			return "0";
		Date timenow = curDate;
		int byear = Integer.parseInt(getFormatDate("yyyy", birthday));
		int nyear = Integer.parseInt(getFormatDate("yyyy", timenow));
		int bmonth = Integer.parseInt(getFormatDate("MM", birthday));
		int nmonth = Integer.parseInt(getFormatDate("MM", timenow));
		int age = nyear - byear;
		if (age < 0)
			return "0";
		if (nmonth < bmonth)
			--age;
		return String.valueOf(age);
	}

	/**
	 * 根据格式和日期获取按格式显示的日期字符串
	 * @param sFormat 格式
	 * @param date    日期
	 */
	public static String getFormatDate(String sFormat, Date date)
	{
		if (date == null)
			return null;

		if ((sFormat == "yy") || (sFormat == "yyyy") || (sFormat == "yyyy-MM")||(sFormat == "yy.MM.dd")||(sFormat == "yyyy.MM.dd")||
				(sFormat == "MM") || (sFormat == "dd") || (sFormat == "MM-dd") || (sFormat == "M-dd") ||
				(sFormat == "MM.dd")||(sFormat == "M/dd")||(sFormat == "MM/dd") || (sFormat == "yyyyMM") || 
				(sFormat == "yyyyMMdd") || (sFormat == "yyyy/MM") || 
				(sFormat == "yy/MM/dd") || 
				(sFormat == "yyyy/MM/dd") || 
				(sFormat == "yyyy-MM-dd") || (sFormat == "yyyy-MM-dd HH:mm:ss") || 
				(sFormat == "yyyy/MM/dd HH:mm:ss") || (sFormat == "M月d日 HH:mm")||
				(sFormat == "yyyyMMddHHmmss") || (sFormat == "yyyyMMddHHmmssSSS") ||
				(sFormat == "yyyy年MM月dd日") ||(sFormat == "yyyy年M月d日") || (sFormat == "yyyy年") || 
				(sFormat == "yyyy年MM月") || (sFormat == "MM月dd日") ||(sFormat == "M月d日") ||  
				(sFormat == "dd日") || (sFormat == "HH") || (sFormat == "H") || 
				(sFormat == "mm") || (sFormat == "ss")|| (sFormat == "SSS") || (sFormat == "HH:mm")||(sFormat == "HH:mm:ss")||
				(sFormat == "yyyy/MM/dd HH:mm")||(sFormat == "yyyy.MM.dd HH:mm")||(sFormat == "yyyy年M月d日 HH:mm")) {
			SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
			return formatter.format(date);
		}else{
			if ((sFormat == "HH:mm")) {
				sFormat = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
				return formatter.format(date).substring(11, 16);
			}
		}
		return null;
	}
	/**
	 * 根据格式和日期获取按格式显示的日期字符串
	 * @param sFormat 格式
	 */
	public static String getFormatDate(String sFormat, Timestamp timestamp)
	{
		if (timestamp == null)
			return null;

		if ((sFormat == "yy") || (sFormat == "yyyy") || (sFormat == "yyyy-MM")||
				(sFormat == "MM") || (sFormat == "dd") || (sFormat == "MM-dd") || 
				(sFormat == "MM.dd")||(sFormat == "MM/dd") || (sFormat == "yyyyMM") || 
				(sFormat == "yyyyMMdd") || (sFormat == "yyyy/MM") || 
				(sFormat == "yy/MM/dd") || 
				(sFormat == "yyyy/MM/dd") || 
				(sFormat == "yyyy-MM-dd") || (sFormat == "yyyy-MM-dd HH:mm:ss") || 
				(sFormat == "yyyy/MM/dd HH:mm:ss") || 
				(sFormat == "yyyyMMddHHmmss") || (sFormat == "yyyyMMddHHmmssSSS") ||
				(sFormat == "yyyy年MM月dd日") ||(sFormat == "yyyy年M月d日") || (sFormat == "yyyy年") || 
				(sFormat == "yyyy年MM月") || (sFormat == "MM月dd日") ||(sFormat == "M月d日") ||  
				(sFormat == "dd日") || (sFormat == "HH") || (sFormat == "H") || 
				(sFormat == "mm") || (sFormat == "ss")|| (sFormat == "SSS") || 
				(sFormat == "yyyy/MM/dd HH:mm")) {
			SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
			return formatter.format(timestamp);
		}else{
			if ((sFormat == "HH:mm")) {
				sFormat = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
				return formatter.format(timestamp).substring(11, 16);
			}
		}
		return null;
	}
	/**
	 * 根据格式和日期获取按格式显示的日期
	 * @param sFormat 格式
	 * @param date    日期
	 */
	public static Date getDate(String sFormat, String date)
	{
		if ((date == null) || ("".equals(date)))
			return null;

		if ((sFormat == "yy") || (sFormat == "yyyy") || 
				(sFormat == "MM") || (sFormat == "dd") || 
				(sFormat == "MM/dd") || (sFormat == "yyyyMM") || 
				(sFormat == "yyyyMMdd") || (sFormat == "yyyy/MM") || 
				(sFormat == "yy/MM/dd") || 
				(sFormat == "yyyy/MM/dd") || 
				(sFormat == "yyyy-MM-dd") || 
				(sFormat == "yyyy/MM/dd HH:mm:ss") || (sFormat == "yyyy-MM-dd HH:mm:ss") ||
				(sFormat == "yyyyMMddHHmm") || 
				(sFormat == "yyyyMMddHHmmss") || 
				(sFormat == "yyyyMMdd-HHmmss") || 
				(sFormat == "yyyy年MM月dd日") || 
				(sFormat == "yyyy年MM月") || (sFormat == "MM月dd日") || 
				(sFormat == "dd日") || (sFormat == "HH") ||(sFormat == "MM.dd") ||
				(sFormat == "mm")) {
			SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
			try {
				return formatter.parse(date);
			}
			catch (Exception e) {
			}
		}else{
			if (sFormat == "HH:mm") {
				sFormat = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
				try {
					return formatter.parse("1970-01-01 "+date+":00");
				}
				catch (Exception e) {
				}
			}
		}
		return null;
	}
	/**
	 * 根据格式获取下一个月的当前时间的前一天时间
	 */
	public static Date getLastDay(Date date)
	{
		if (date == null)
			return null;

		return dateAdd(3, -1, dateAdd(2, 1, date));
	}

	/**
	 * 根据格式获取按格式显示的当前日期
	 * @param sFormat 格式
	 */
	public static String getFormatDate(String sFormat)
	{
		return getFormatDate(sFormat, getCurrent());
	}
	/**
	 * 为给定的日期添加或减去指定的时间量
	 * @param iField 日历的规则(1为多少年,2为多少月,3为多少天,10为多少小时,11为一天中的小时,12为分,13为秒)
	 * @param iValue 时间量
	 * @param date 日期
	 */
	public static Date dateAdd(int iField, int iValue, Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch (iField) { case 1:
			cal.add(cal.YEAR, iValue);
			break;
		case 2:
			cal.add(cal.MONTH, iValue);
			break;
		case 3:
			cal.add(cal.DATE, iValue);
			break;
		case 10:
			cal.add(cal.HOUR, iValue);
			break;
		case 11:
			cal.add(cal.HOUR_OF_DAY, iValue);
			break;
		case 12:
			cal.add(cal.MINUTE, iValue);
			break;
		case 13:
			cal.add(cal.SECOND, iValue);
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9: } return cal.getTime();
	}
	/**
	 * 根据规则得到两个给定日期之前相差的时间
	 * @param iField 日历的规则(1为多少年,2为多少月,3为多少天,10为多少小时,11为一天中的小时,12为分,13为秒)
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 */
	public static long dateDiff(int iField, Date startDate, Date endDate)
	{
		if(null==startDate||null==endDate){
			return 0;
		}
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();

		int startYear = Integer.parseInt(getFormatDate("yyyy", startDate));
		int endYear = Integer.parseInt(getFormatDate("yyyy", endDate));
		int startMonth = Integer.parseInt(getFormatDate("MM", startDate)) - 1;
		int endMonth = Integer.parseInt(getFormatDate("MM", endDate)) - 1;
		int startDay = Integer.parseInt(getFormatDate("dd", startDate));
		int endDay = Integer.parseInt(getFormatDate("dd", endDate));
		int startHour = Integer.parseInt(getFormatDate("HH", startDate));
		int endHour = Integer.parseInt(getFormatDate("HH", endDate));
		int startMinute = Integer.parseInt(getFormatDate("mm", startDate));
		int endMinute = Integer.parseInt(getFormatDate("mm", endDate));
		int startSecond = Integer.parseInt(getFormatDate("ss", startDate));
		int endSecond = Integer.parseInt(getFormatDate("ss", endDate));
		switch (iField) { case 1:
			return (endYear - startYear);
		case 2:
			long yearDiff = endYear - startYear;
			long monthDiff = endMonth - startMonth;
			return (yearDiff * 12L + monthDiff);
		case 3:
			start.set(startYear, startMonth, startDay, 0, 0, 0);
			end.set(endYear, endMonth, endDay, 0, 0, 0);
			return ((end.getTimeInMillis() - start.getTimeInMillis()) / 
					86400000L);
		case 10:
			start.set(startYear, startMonth, startDay, startHour, 0, 0);
			end.set(endYear, endMonth, endDay, endHour, 0, 0);
			return ((end.getTimeInMillis() - start.getTimeInMillis()) / 
					3600000L);
		case 11:
			start.set(startYear, startMonth, startDay, startHour, 0, 0);
			end.set(endYear, endMonth, endDay, endHour, 0, 0);
			return ((end.getTimeInMillis() - start.getTimeInMillis()) / 
					3600000L);
		case 12:
			start.set(startYear, startMonth, startDay, startHour, startMinute, 
					0);
			end.set(endYear, endMonth, endDay, endHour, endMinute, 0);
			return ((end.getTimeInMillis() - start.getTimeInMillis()) / 
					60000L);
		case 13:
			start.set(startYear, startMonth, startDay, startHour, startMinute, 
					startSecond);
			end.set(endYear, endMonth, endDay, endHour, endMinute, endSecond);
			return ((end.getTimeInMillis() - start.getTimeInMillis()) / 
					1000L);
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9: } return (end.getTimeInMillis() - start.getTimeInMillis());
	}
	/**
	 * 为当前日期添加或减去指定的时间量
	 * @param iField 日历的规则(1为多少年,2为多少月,3为多少天,10为多少小时,11为一天中的小时,12为分,13为秒)
	 * @param iValue 时间量
	 */
	public static Date dateAdd(int iField, int iValue)
	{
		return dateAdd(iField, iValue, getCurrent());
	}

	/**
	 * 根据日期获取按yyyyMMdd格式显示的日期
	 * @param date    日期
	 */
	public static Date dateTrunc(Date date)
	{
		return getDate("yyyyMMdd", getFormatDate(
				"yyyyMMdd", date));
	}
	/**
	 * 根据日期所在的月有多少天
	 * @param date    日期
	 */
	public static long getMonthDayCount(Date date)
	{
		Date start = getMonthFirstDay(date);
		Date end = getMonthEndDay(date);
		return (dateDiff(3, start, end) + 1L);
	}
	/**
	 * 根据类型和日期获取日期是第几个星期
	 * @param type    类型(1为日期所在年份的第几个星期,2为日期所在月份的第几个星期)
	 * @param date    日期
	 */
	public static int getWeekNum(int type, Date date)
	{
		Date monthenddate = getMonthEndDay(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(monthenddate);

		if (type == 1)
		{
			return cal.get(Calendar.MONTH);
		}
		if (type == 2)
		{
			return cal.get(Calendar.WEEK_OF_YEAR);
		}

		return 0;
	}
	/**
	 * 根据小时,分钟,秒,月,天,年获取当前日期
	 * @param hour 小时
	 * @param minute 分钟
	 * @param second 秒
	 * @param month 月
	 * @param day 天
	 * @param year 年
	 */
	public static Date mktime(int hour, int minute, int second, int month, int day, int year)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, hour, minute, second);
		return cal.getTime();
	}
	/**
	 * 获取指定日期的时间戳
	 * @param date    日期
	 */
	public static Timestamp getTimestamp(Date date)
	{
		return new Timestamp(date.getTime());
	}
	/**
	 * 获取指定日期的时间戳
	 */
	public static Date getDateFormTimestamp(Timestamp timestamp)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(df.format(timestamp));
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 获取当前日期的时间戳
	 */
	public static Timestamp getCurrentDateTimestamp()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp createDttm = Timestamp.valueOf(df
				.format(new Date()));
		return createDttm;
	}

	/**
	 * 获取两个给定日期之前相差的天数,小时,分钟
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 */
	public static long[] dateDiffEx(Date startDate, Date endDate)
	{
		long[] diffTime = new long[3];

		long minuteDiff = 0L;
		long hourDiff = 0L;
		long dayDiff = 0L;

		long diff = dateDiff(12, startDate, endDate);

		if (diff > 0L)
		{
			minuteDiff = diff % 60L;
			diff /= 60L;
		}

		if (diff > 0L)
		{
			hourDiff = diff % 24L;
			diff /= 24L;
		}

		if (diff > 0L)
		{
			dayDiff = diff;
		}

		diffTime[0] = dayDiff;
		diffTime[1] = hourDiff;
		diffTime[2] = minuteDiff;

		return diffTime;
	}

	/**
	 * 获取两个给定日期之前相差的年,月,日,时,分,秒
	 */
	public static int[] getTimeDiff(Date startTime, Date endTime)
	{
		int[] ret = new int[6];
		if ((startTime == null) || (endTime == null))
			return null;
		int syear = 0;
		if (getFormatDate("yyyy", startTime) != null)
			syear = Integer.parseInt(getFormatDate("yyyy", 
					startTime));
		int eyear = 0;
		if (getFormatDate("yyyy", endTime) != null)
			eyear = Integer.parseInt(getFormatDate("yyyy", 
					endTime));

		int smonth = 0;
		if (getFormatDate("MM", startTime) != null)
			smonth = Integer.parseInt(getFormatDate("MM", 
					startTime));
		int emonth = 0;
		if (getFormatDate("MM", endTime) != null)
			emonth = Integer.parseInt(getFormatDate("MM", 
					endTime));
		int sday = 0;
		if (getFormatDate("dd", startTime) != null)
			sday = Integer.parseInt(getFormatDate("dd", 
					startTime));
		int eday = 0;
		if (getFormatDate("dd", endTime) != null)
			eday = Integer.parseInt(
					getFormatDate("dd", endTime));
		int shour = 0;
		if (getFormatDate("HH", startTime) != null)
			shour = Integer.parseInt(getFormatDate("HH", 
					startTime));
		int ehour = 0;
		if (getFormatDate("HH", endTime) != null)
			ehour = Integer.parseInt(getFormatDate("HH", 
					endTime));
		int sminute = 0;
		if (getFormatDate("mm", startTime) != null)
			sminute = Integer.parseInt(getFormatDate("mm", startTime));
		int eminute = 0;
		if (getFormatDate("mm", endTime) != null)
			eminute = Integer.parseInt(getFormatDate("mm", endTime));

		int ssecond = 0;
		if (getFormatDate("ss", endTime) != null)
			ssecond = Integer.parseInt(getFormatDate("ss", startTime));
		int esecond = 0;
		if (getFormatDate("ss", endTime) != null)
			esecond = Integer.parseInt(getFormatDate("ss", endTime));

		int secondDif = esecond - ssecond;

		int minuteDif = eminute - sminute;

		int hourDif = ehour - shour;

		int dayDif = eday - sday;

		int monthDif = emonth - smonth;

		int yearDif = eyear - syear;

		if (secondDif < 0) {
			secondDif += 60;
			--minuteDif;
		}
		if (minuteDif < 0) {
			minuteDif += 60;
			--hourDif;
		}
		if (hourDif < 0) {
			hourDif += 24;
			--dayDif;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endTime);
		int days = calendar.getMaximum(Calendar.WEEK_OF_MONTH);

		if (dayDif < 0) {
			dayDif += days;
			--monthDif;
		}
		if (monthDif < 0) {
			monthDif += 12;
			--yearDif;
		}

		ret[0] = yearDif;
		ret[1] = monthDif;
		ret[2] = dayDif;
		ret[3] = hourDif;
		ret[4] = minuteDif;
		ret[5] = secondDif;

		return ret;
	}
	/**
	 * 根据给定格式和获取给定日期的星期
	 * @param sFormat 格式
	 * @param date    日期
	 */
	public static String getWeekday(String sFormat, String date)
	{
		Date datetime = getDate(sFormat, date);

		return getWeekday(datetime);
	}
	/**
	 * 获取给定日期的星期
	 * @param date    日期
	 */
	public static String getWeekday(Date date)
	{
		String week = "";

		if (date != null) {
			int day = date.getDay();
			switch (day)
			{
			case 1:
				week = "星期一";
				break;
			case 2:
				week = "星期二";
				break;
			case 3:
				week = "星期三";
				break;
			case 4:
				week = "星期四";
				break;
			case 5:
				week = "星期五";
				break;
			case 6:
				week = "星期六";
				break;
			case 0:
				week = "星期日";
			}

		}

		return week;
	}


	/**
	 * 获取给定日期的星期
	 * @param date    日期
	 */
	public static String getWeekday2(Date date)
	{
		String week = "";

		if (date != null) {
			int day = date.getDay();
			switch (day)
			{
			case 1:
				week = "周一";
				break;
			case 2:
				week = "周二";
				break;
			case 3:
				week = "周三";
				break;
			case 4:
				week = "周四";
				break;
			case 5:
				week = "周五";
				break;
			case 6:
				week = "周六";
				break;
			case 0:
				week = "周日";
			}

		}

		return week;
	}
	/**
	 * 判断是否为周六或者周天
	 * @param date
	 * @return
	 */
	public static boolean isWeekDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
		{
			return true;
		}
		return false;
	}


	/**
	 * 判断是否为周六
	 * @param date
	 * @return
	 */
	public static boolean isSaturday(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
		{
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为周天
	 * @param date
	 * @return
	 */
	public static boolean isSunday(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
		{
			return true;
		}
		return false;
	}


	/**
	 * 根据给定格式和日期获取按格式显示的日期字符串
	 * @param sFormat 格式
	 * @param date    日期
	 */
	public static String getStringDate(String sFormat, Date date)
	{
		if ((date == null)) {
			return null;
		}

		String year = getFormatDate("yyyy", date);

		String month = getFormatDate("MM", date);

		String day = getFormatDate("dd", date);

		if ("DYYYYMMDD".equals(sFormat))
			return year + "." + month + "." + day;

		if ("DYYYYMM".equals(sFormat))
			return year + "." + month;

		return "";
	}
	/**
	 * 根据指定月数给出中文字
	 * @param month
	 * @return
	 */
	public static String parseMonthNumber(int month){
		switch(month){
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 7:
			return "七";
		case 8:
			return "八";
		case 9:
			return "九";
		case 10:
			return "十";
		case 11:
			return "十一";
		case 12:
			return "十二";

		}
		return "";
	}

	/**
	 * 判断日期是否是yyyy-MM-dd的格式
	 * @param dateString
	 * @return
	 */
	public static boolean validate(String dateString){
		//使用正则表达式 测试 字符 符合 dddd-dd-dd 的格式(d表示数字)
		Pattern p = Pattern.compile("\\d{4}+[-]\\d{1,2}+[-]\\d{1,2}+");
		Matcher m = p.matcher(dateString);
		if(!m.matches()){	return false;} 

		//得到年月日
		String[] array = dateString.split("-");
		int year = DoNumberUtil.intNullDowith(array[0]);
		int month = DoNumberUtil.intNullDowith(array[1]);
		int day = DoNumberUtil.intNullDowith(array[2]);

		if(month<1 || month>12){	return false;}
		int[] monthLengths = new int[]{0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if(isLeapYear(year)){
			monthLengths[2] = 29;
		}else{
			monthLengths[2] = 28;
		}
		int monthLength = monthLengths[month];
		if(day<1 || day>monthLength){
			return false;	
		}
		return true;
	}

	/** 是否是闰年 */
	public static boolean isLeapYear(int year){
		return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ;
	}
	public static void main(String args[]){
		//for test
	}
	/**
	 * 日期格式化
	 * @param date
	 * @return
	 */
	public static String getDateFormat(Date date){
		long second = dateDiff(13,date,new Date());
		if(second<60){
			if(second <1){
				second =1;
			}
			return second +"秒前";
		}else{
			long minute = (long)(second/60);
			if(minute<60){
				return minute +"分钟前";
			}else{
				if(dateDiff(13,date,getDate("yyyy/MM/dd HH:mm:ss", getDayStartStr(new Date())))<0){
					return "今天"+getFormatDate("HH:mm", date);
				}else{
					return getFormatDate("M月d日", date)+" "+getFormatDate("HH:mm", date);
				}
			}
		}
	}
	/**
	 * 日期格式化
	 * @return
	 */
	public static String getDayFormat(String day){
		int days = 0;
		if(!BaseUtil.isSpace(day)){
			days = DoNumberUtil.intNullDowith(day);
		}
		if(days>=24*60){
			int dayI = days/24/60;
			String returnStr = dayI +"天";
			int diff = days - dayI*24*60;
			if(diff>0){
				int hour = diff/60;
				if(hour>0){
					returnStr+=hour +"小时";
				}
			}
			return returnStr;
		}else{
			if(days>=60){
				int hour = days/60;
				String returnStr = hour +"小时";
				if((days-hour*60)>0){
					returnStr+=(days-hour*60)+"分钟";
				}
				return returnStr;
			}else{
				return days +"分钟";
			}
		}
	}
	/**
	 * 日期格式化

		return day;	 * @param date
	 * @return
	 */
	public static String getDateFormat(Timestamp timestamp){
		Date date = getDate("yyyy-MM-dd HH:mm:ss",getFormatDate("yyyy-MM-dd HH:mm:ss",timestamp));
		long second=dateDiff(13,date,new Date());
		if(second<60){
			if(second <1){
				second =1;
			}
			return second +"秒前";
		}else{
			long minute = (long)(second/60);
			if(minute<60){
				return minute +"分钟前";
			}else{
				if(dateDiff(13,date,getDate("yyyy/MM/dd HH:mm:ss", getDayStartStr(new Date())))<0){
					return "今天"+getFormatDate("HH:mm", date);
				}else{
					return getFormatDate("M月d日", date)+" "+getFormatDate("HH:mm", date);
				}
			}
		}
	}
	/**
	 * 日期格式化
	 * @param date
	 * @param format 超过3天的日期格式
	 * @return
	 */
	public static String getDateFormatDiary(Date date,String format){
		String dateStr = getFormatDate("yyyy-MM-dd", date);
		long day=dateDiff(3,date,new Date());
		if(dateStr.equals(getToday())){
			return "今天";
		}
		if(dateStr.equals(getYesterday())){
			return "昨天";
		}
		if(dateStr.equals(getBeforeYesterday())){
			return "前天";
		}
		return DateUtil.getFormatDate(format, date);
	}



	/**
	 * 获取今天星期几
	 */
	public static String getJinTianWeekCN(){
		Calendar calendar = Calendar.getInstance();
		return getWeekDayCN(calendar);
	}

	//得到星期几
	private static String getWeekDayCN(Calendar c ){

		int whichDay = c.get(Calendar.DAY_OF_WEEK); 

		if(Calendar.MONDAY == whichDay)		
			return "星期一" ;

		if(Calendar.TUESDAY == whichDay)	
			return "星期二" ;

		if(Calendar.WEDNESDAY == whichDay)	
			return "星期三" ;

		if(Calendar.THURSDAY == whichDay)	
			return "星期四" ;

		if(Calendar.FRIDAY == whichDay)		
			return "星期五";

		if(Calendar.SATURDAY == whichDay)	
			return  "星期六";

		if(Calendar.SUNDAY == whichDay)		
			return "星期日";


		return "星期一" ;

	}

	/**索引变成周几*/
	public static String index2Week(int index){
		switch (index) {
		case 0:
			return "一";
		case 1:
			return "二";
		case 2:
			return "三";
		case 3:
			return "四";
		case 4:
			return "五";
		case 5:
			return "六";
		case 6:
			return "日";

		default:
			break;
		}

		return "";
	}
	//-----------------------分割线,以后对上面的方法清理----by:zzz-------------

	/**
	 * 获取今天0点0分0秒的Date,
	 * @return
	 */
	public static Date getTodayDate(){
		return getDate("yyyy-MM-dd", getToday());
	}

	/**
	 * 获取昨天0点0分0秒的Date
	 * @return
	 */
	public static Date getYesterdayDate(){
		return getDate("yyyy-MM-dd", getYesterday());
	}

	/**
	 * 获取前天0点0分0秒的Date
	 * @return
	 */
	public static Date getQianDate(){
		return getDate("yyyy-MM-dd", getBeforeYesterday());
	}

	/**
	 * 判断该天是不是周某
	 * @return
	 */
	public static boolean isTodayWeekend(){ 
		int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		return dayOfWeek == 1 || dayOfWeek == 7;
	}

	/*
	 * 从日期类型的数据取得时间字符串
	 * 例：2012-1-2 12:32:00 返回 12:32 
	 * */
	public static String getTimeFromDate(Date date)
	{
		//		Calendar c = Calendar.getInstance();
		//		int year = c.get(Calendar.YEAR);
		//		int month = c.get(Calendar.MONTH)+1;
		//		int day = c.get(Calendar.DAY_OF_MONTH);

		int hour = date.getHours();
		int minute = date.getMinutes();

		String currentTime=BaseUtil.getFormatStringFromInt(hour,2)+":"+
				BaseUtil.getFormatStringFromInt(minute,2);

		return currentTime;
	}

	//的信息转成字符串
	public static String weekInfoIndex2Str(String indexStr){
		if(indexStr == null || "".equals(indexStr)) return ""; //返回未设置
		String[] indexs = indexStr.split(",");
		if(indexs.length == 7 ){
			return "每天";
		}
		StringBuffer sb = new StringBuffer();
		for (int i= 0 ;i != indexs.length ;i++) {
			String index = indexs[i];
			int num = DoNumberUtil.intNullDowith(index);
			sb.append(index2Week(num));
			if(i != indexs.length - 1){
				sb.append('、');
			}
		}
		return sb.toString();
	}


	/**
	 * 改天 离今天几天
	 * @param date
	 * @return
	 */
	public static int numTheDate2Today(Date date){
		return (int) ((getTodayDate().getTime() - date.getTime()) / DateUtil.ONE_DAY);
	}
	/**
	 *  获取指定日期属于指定日期年份的第几周  例如2013-01-01返回的就是1表示是第一周
	 * @param date
	 * @return int 第几周    
	 */
	public static int getWeek(Date date) {
		Calendar g = Calendar.getInstance();
		g.setTime(date);
		return g.get(Calendar.WEEK_OF_YEAR);//获得周数
	}
	/**
	 *  获取指定日期的年份
	 * @param date
	 * @return int 例如2013   
	 */
	public static int getYear(Date date) {
		Calendar g = Calendar.getInstance();
		g.setTime(date);
		return g.get(Calendar.YEAR);//获得年份
	}

	/**
	 * 特殊方法 因为周记只在周六 周日 周一记录  周六记录的当周的  周日 周一记录的是上周的  
	 * 所以就将日期减去4天 
	 *  获取指定日期的年份+当前属于今天的周数 例如2013的第2周就是 20132  &&    2013的第10周就是201310
	 * @param date
	 * @return int 例如201310   
	 */
	public static String getYAndW(Date date) {
		date = DateUtil.dateAdd(3, -4, date);
		int year = getYear(date);
		int week = getWeek(date);
		String yandw = DoNumberUtil.IntToStr(year)+DoNumberUtil.IntToStr(week);
		return yandw;
	}

	/**
	 *  获取当前月份
	 * @param date
	 * @return int 例如10  
	 */
	public static int getMonth() {
		Calendar g = Calendar.getInstance();
		return g.get(Calendar.MONTH)+1;//获得当前月份
	}

	/**
	 *  获取当前日期   多少号  
	 * @param date
	 * @return int 例如21  
	 */
	public static int getDay() {
		Calendar g = Calendar.getInstance();
		return g.get(Calendar.DAY_OF_MONTH);//获得当前日期
	}
	/**
	 * 将传入的时间（秒）转化成 时：分：秒的形式
	 * @param s
	 * @return
	 */
	public static String getFromS(long s){
		String f = String.format("%02d",  s%60);         
		String f_ = String.format("%02d",  s%3600/60);
		String f_h = String.format("%02d", s/3600);
		return f_h+":"+ f_ +":"+f;
	}
}
