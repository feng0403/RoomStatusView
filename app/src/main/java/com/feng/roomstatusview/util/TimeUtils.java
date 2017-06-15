package com.feng.roomstatusview.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期时间转换工具
 */
public class TimeUtils {

  public static final String DATE_FORMATTER = "yyyy-MM-dd'T'HH:mm:ssz";
  public static final String MONTH_DAY_FORMATTER = "MM-dd";
  public static final String DATE_FORMATTER_NO_ZONE = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String DATE_FORMATTER_NO_TIME = "yyyy-MM-dd";
  public static final String DATE_FORMATTER_NO_ZONE_T = "yyyy-MM-dd HH:mm:ss";

  private static final long DAY_IN_MILLS = 86400000L;

  /**
   * 判断某个时间字符串是不是『今天』
   *
   * @param timeStr 形如 "2016-09-22T12:12:12+08:00";
   */
  public static boolean isToday(String timeStr) {
    Calendar calendar = parse2Calendar(timeStr);
    if (calendar == null) {
      return false;
    }
    clearTimeInfo(calendar);
    Calendar today = Calendar.getInstance();
    clearTimeInfo(today);
    return today.equals(calendar);
  }

  /**
   * @param timeStr 形如 "2016-09-22T12:12:12+08:00";
   */
  public static Calendar parse2Calendar(String timeStr) {
    if (TextUtils.isEmpty(timeStr)) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER, Locale.getDefault());
    try {
      calendar.setTime(dateFormat.parse(timeStr));
      return calendar;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 返回接口中的时间的时区信息
   */
  public static TimeZone getProtocolTimeZone() {
    return TimeZone.getTimeZone("GMT+0800");
  }

  /**
   * 格式时间为：yyyy-MM-ddTHH:mm:ss +0800的字符串
   */
  public static String format(long timeInMills) {
    return android.text.format.DateFormat.format(DATE_FORMATTER, timeInMills).toString();
  }

  /**
   * 将格式为：yyyy-MM-ddTHH:mm:ss +0800的字符串转化为UnixTime
   *
   * @return -1表示失败，其他表示成功
   */
  public static long parse(String dateStr) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER, Locale.getDefault());
    try {
      return dateFormat.parse(dateStr).getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return -1;
  }

  public static String getTimeStamp() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.SECOND, 1800);
    return format(calendar.getTimeInMillis());
  }

  /**
   * 把日期毫秒转化为字符串。
   *
   * @param millis 要转化的日期毫秒数。
   * @param pattern 要转化为的字符串格式（如：yyyy-MM-dd HH:mm:ss）。
   * @return 返回日期字符串。
   * @
   */
  public static String millisToString(long millis, String pattern) {
    SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
    return format.format(new Date(millis));
  }

  public static String transformDateStr(String strDate, String inFormat, String outFormat) {
    if (TextUtils.isEmpty(inFormat)) {
      inFormat = DATE_FORMATTER;
    }
    SimpleDateFormat sdf1 = new SimpleDateFormat(inFormat, Locale.getDefault());
    SimpleDateFormat sdf2 = new SimpleDateFormat(outFormat, Locale.getDefault());
    Date d;
    try {
      d = sdf1.parse(strDate);
    } catch (Exception e) {
      return strDate;
    }
    return sdf2.format(d);
  }

  /**
   * 获取时区
   */
  public static String getTimeZone() {
    TimeZone tz = TimeZone.getDefault();
    return tz.getDisplayName(false, TimeZone.SHORT) + "  " + tz.getID();
  }

  public static String format(Calendar calendar, String pattern) {
    SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
    return format.format(calendar.getTime());
  }

  public static void clearTimeInfo(Calendar calendar) {
    calendar.clear(Calendar.AM_PM);
    calendar.clear(Calendar.HOUR);
    calendar.clear(Calendar.HOUR_OF_DAY);
    calendar.clear(Calendar.HOUR);
    calendar.clear(Calendar.MINUTE);
    calendar.clear(Calendar.SECOND);
    calendar.clear(Calendar.MILLISECOND);
  }

  /**
   * 清除时间信息，并返回一个新的Calendar
   */
  public static Calendar clearTimeInfoWithNew(Calendar calendar) {
    Calendar calendar1 = (Calendar) calendar.clone();
    clearTimeInfo(calendar1);
    return calendar1;
  }

  /**
   * 获取两个日期间隔的天数
   */
  public static int getDayDurations(Calendar start, Calendar end) {
    clearTimeInfo(start);
    clearTimeInfo(end);
    long duration = Math.abs(start.getTimeInMillis() - end.getTimeInMillis());
    return (int) (duration / DAY_IN_MILLS);
  }

  /**
   * 获取startCalendar之后或之前offsetDays的Calendar
   */

  public static Calendar getOffsetCalendar(Calendar startCalendar, int offsetDays) {
    Calendar endCalendar = (Calendar) (startCalendar.clone());
    endCalendar.add(Calendar.DAY_OF_MONTH, offsetDays);
    return endCalendar;
  }

  /**
   * 获取当前月的第一天
   */
  public static Calendar getFirstDayOfCurrentMonth() {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.DAY_OF_MONTH, 1);
    return c;
  }

  public static List<Calendar> getCalendarsBetweenDates(Calendar startC, Calendar endC) {
    List<Calendar> calendars = new ArrayList<>();
    Calendar calendar = (Calendar) (startC.clone());
    clearTimeInfo(calendar);
    clearTimeInfo(endC);

    while (calendar.before(endC)) {
      calendars.add((Calendar) calendar.clone());
      calendar.add(Calendar.DATE, 1);
    }

    if (calendar.equals(endC)) {
      calendars.add((Calendar) calendar.clone());
    }

    return calendars;
  }

  /**
   * 获取[startCalendar,startCalendar+offsetDays]中targetCalendar的pos
   *
   * @param startCalendar 开始日期
   * @param offsetDays 持续天数
   * @param targetCalendar 待查找日期
   * @return -1 未找到
   */
  public static int getCalenderPos(Calendar startCalendar, int offsetDays,
                                   Calendar targetCalendar) {
    Calendar endCalendar = getOffsetCalendar(startCalendar, offsetDays);
    clearTimeInfo(startCalendar);
    clearTimeInfo(endCalendar);
    clearTimeInfo(targetCalendar);

    Calendar calendar = (Calendar) (startCalendar.clone());

    for (int i = 0; i < offsetDays; i++) {
      if (calendar.before(endCalendar)) {
        if (calendar.equals(targetCalendar)) {
          return i;
        }
        calendar.add(Calendar.DATE, 1);
      }
    }
    return -1;
  }
}
