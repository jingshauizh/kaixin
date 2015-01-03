package com.kaixin001.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeConvertUtil
{
  public static Date calendarToDate(Calendar paramCalendar)
  {
    return paramCalendar.getTime();
  }

  public static String calenderToString(Calendar paramCalendar)
  {
    return new SimpleDateFormat("yyyy-MM-dd").format(paramCalendar.getTime());
  }

  public static Calendar dateToCalendar(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return localCalendar;
  }

  public static String dateToStr(Date paramDate)
  {
    return new SimpleDateFormat("yyyy-MM-dd").format(paramDate);
  }

  public static String dateToStrLong(Date paramDate)
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramDate);
  }

  public static Timestamp dateToTimeStamp(Date paramDate)
  {
    return Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
  }

  public static Calendar millisToCalendar(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    return localCalendar;
  }

  public static Date strToDate(String paramString)
  {
    return new SimpleDateFormat("yyyy-MM-dd").parse(paramString, new ParsePosition(0));
  }

  public static Date strToDateLong(String paramString)
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paramString, new ParsePosition(0));
  }

  public static long strToLong(String paramString)
    throws ParseException
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paramString).getTime();
  }

  public static Calendar stringToCalendar(String paramString)
    throws ParseException
  {
    Date localDate = new SimpleDateFormat("yyyy-MM-dd").parse(paramString);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    return localCalendar;
  }

  public static Timestamp stringToTimestamp(String paramString)
  {
    return Timestamp.valueOf(paramString);
  }

  public static String timestampToString(Timestamp paramTimestamp)
  {
    return new SimpleDateFormat("yyyy-MM-dd").format(paramTimestamp);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.TimeConvertUtil
 * JD-Core Version:    0.6.0
 */