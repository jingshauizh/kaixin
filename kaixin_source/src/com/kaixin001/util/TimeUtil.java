package com.kaixin001.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

public class TimeUtil
{
  public static boolean RightDate(String paramString)
  {
    new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    if (paramString == null)
      return false;
    SimpleDateFormat localSimpleDateFormat;
    if (paramString.length() > 10)
      localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    try
    {
      while (true)
      {
        localSimpleDateFormat.parse(paramString);
        return true;
        localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      }
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public static String getChineseWeek(Calendar paramCalendar)
  {
    return new String[] { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" }[(-1 + paramCalendar.get(7))];
  }

  public static int getDays(long paramLong)
  {
    return getDays(paramLong);
  }

  public static int getDays(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try
    {
      Date localDate = localSimpleDateFormat.parse(paramString);
      long l = (new Date().getTime() - localDate.getTime()) / 86400000L;
      return (int)l;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  public static int getDays(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.equals("")));
    do
      return 0;
    while ((paramString2 == null) || (paramString2.equals("")));
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date localDate1 = null;
    try
    {
      localDate1 = localSimpleDateFormat.parse(paramString1);
      Date localDate3 = localSimpleDateFormat.parse(paramString2);
      localDate2 = localDate3;
      return (int)((localDate1.getTime() - localDate2.getTime()) / 86400000L);
    }
    catch (Exception localException)
    {
      while (true)
        Date localDate2 = null;
    }
  }

  public static int getDaysBetween(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    if (paramCalendar1.after(paramCalendar2))
    {
      Calendar localCalendar2 = paramCalendar1;
      paramCalendar1 = paramCalendar2;
      paramCalendar2 = localCalendar2;
    }
    int i = paramCalendar2.get(6) - paramCalendar1.get(6);
    int j = paramCalendar2.get(1);
    if (paramCalendar1.get(1) != j)
    {
      Calendar localCalendar1 = (Calendar)paramCalendar1.clone();
      do
      {
        i += localCalendar1.getActualMaximum(6);
        localCalendar1.add(1, 1);
      }
      while (localCalendar1.get(1) != j);
    }
    return i;
  }

  public static String getEDate(String paramString)
  {
    String[] arrayOfString = new SimpleDateFormat("yyyy-MM-dd").parse(paramString, new ParsePosition(0)).toString().split("");
    return arrayOfString[2] + arrayOfString[1].toUpperCase() + arrayOfString[5].substring(2, 4);
  }

  public static String getEndDateOfMonth(String paramString)
  {
    String str = paramString.substring(0, 8);
    int i = Integer.parseInt(paramString.substring(5, 7));
    if ((i == 1) || (i == 3) || (i == 5) || (i == 7) || (i == 8) || (i == 10) || (i == 12))
      return str + "31";
    if ((i == 4) || (i == 6) || (i == 9) || (i == 11))
      return str + "30";
    if (isLeapYear(paramString))
      return str + "29";
    return str + "28";
  }

  public static int getHolidays(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    return getDaysBetween(paramCalendar1, paramCalendar2) - getWorkingDays(paramCalendar1, paramCalendar2);
  }

  public static int getIntHour()
  {
    return Integer.parseInt(getStrHour());
  }

  public static Date getLastDate(long paramLong)
  {
    return new Date(new Date().getTime() - 122400000L * paramLong);
  }

  public static String getNextDay(String paramString1, String paramString2)
  {
    try
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date localDate = TimeConvertUtil.strToDate(paramString1);
      localDate.setTime(1000L * (localDate.getTime() / 1000L + 60 * (60 * (24 * Integer.parseInt(paramString2)))));
      String str = localSimpleDateFormat.format(localDate);
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  public static Calendar getNextMonday(Calendar paramCalendar)
  {
    Calendar localCalendar = paramCalendar;
    do
    {
      localCalendar = (Calendar)localCalendar.clone();
      localCalendar.add(5, 1);
    }
    while (localCalendar.get(7) != 2);
    return localCalendar;
  }

  public static String getNo(int paramInt)
  {
    return getUserDate("yyyyMMddhhmmss") + getRandom(paramInt);
  }

  public static Date getNow()
  {
    return new Date();
  }

  public static Date getNowDate()
  {
    Date localDate = new Date();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return localSimpleDateFormat.parse(localSimpleDateFormat.format(localDate), new ParsePosition(8));
  }

  public static Date getNowDateShort()
  {
    Date localDate = new Date();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    return localSimpleDateFormat.parse(localSimpleDateFormat.format(localDate), new ParsePosition(8));
  }

  public static String getNowMonth(String paramString)
  {
    String str = paramString.substring(0, 8) + "01";
    Date localDate = TimeConvertUtil.strToDate(str);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    return getNextDay(str, 1 - localCalendar.get(7));
  }

  public static String getPreTime(String paramString1, String paramString2)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try
    {
      Date localDate = localSimpleDateFormat.parse(paramString1);
      localDate.setTime(1000L * (localDate.getTime() / 1000L + 60 * Integer.parseInt(paramString2)));
      String str = localSimpleDateFormat.format(localDate);
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  public static String getRandom(int paramInt)
  {
    Random localRandom = new Random();
    String str;
    if (paramInt == 0)
      str = "";
    while (true)
    {
      return str;
      str = "";
      for (int i = 0; i < paramInt; i++)
        str = str + localRandom.nextInt(9);
    }
  }

  public static String getSeqWeek()
  {
    Calendar localCalendar = Calendar.getInstance(Locale.CHINA);
    String str = Integer.toString(localCalendar.get(3));
    if (str.length() == 1)
      str = "0" + str;
    return Integer.toString(localCalendar.get(1)) + str;
  }

  public static String getStrHour()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate).substring(11, 13);
  }

  public static String getStringDate()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate);
  }

  public static String getStringDateShort()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
  }

  public static String getStringToday()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyyMMdd HHmmss").format(localDate);
  }

  public static String getTime()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate).substring(14, 16);
  }

  public static String getTimeShort()
  {
    return new SimpleDateFormat("HH:mm:ss").format(new Date());
  }

  public static String getTwoDay(String paramString1, String paramString2)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try
    {
      Date localDate1 = localSimpleDateFormat.parse(paramString1);
      Date localDate2 = localSimpleDateFormat.parse(paramString2);
      long l = (localDate1.getTime() - localDate2.getTime()) / 86400000L;
      return l;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  public static String getTwoHour(String paramString1, String paramString2)
  {
    String[] arrayOfString1 = paramString1.split(":");
    String[] arrayOfString2 = paramString2.split(":");
    if (Integer.parseInt(arrayOfString1[0]) < Integer.parseInt(arrayOfString2[0]))
      return "0";
    double d1 = Double.parseDouble(arrayOfString1[0]) + Double.parseDouble(arrayOfString1[1]) / 60.0D;
    double d2 = Double.parseDouble(arrayOfString2[0]) + Double.parseDouble(arrayOfString2[1]) / 60.0D;
    if (d1 - d2 > 0.0D)
      return d1 - d2;
    return "0";
  }

  public static String getUserDate(String paramString)
  {
    Date localDate = new Date();
    return new SimpleDateFormat(paramString).format(localDate);
  }

  public static String getWeek(String paramString)
  {
    Date localDate = TimeConvertUtil.strToDate(paramString);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    return new SimpleDateFormat("EEEE").format(localCalendar.getTime());
  }

  public static String getWeek(String paramString1, String paramString2)
  {
    Date localDate = TimeConvertUtil.strToDate(paramString1);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(localDate);
    if (paramString2.equals("1"))
      localCalendar.set(7, 2);
    while (true)
    {
      return new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime());
      if (paramString2.equals("2"))
      {
        localCalendar.set(7, 3);
        continue;
      }
      if (paramString2.equals("3"))
      {
        localCalendar.set(7, 4);
        continue;
      }
      if (paramString2.equals("4"))
      {
        localCalendar.set(7, 5);
        continue;
      }
      if (paramString2.equals("5"))
      {
        localCalendar.set(7, 6);
        continue;
      }
      if (paramString2.equals("6"))
      {
        localCalendar.set(7, 7);
        continue;
      }
      if (!paramString2.equals("0"))
        continue;
      localCalendar.set(7, 1);
    }
  }

  public static String getWeekStr(String paramString)
  {
    String str = getWeek(paramString);
    if ("1".equals(str))
      str = "星期日";
    do
    {
      return str;
      if ("2".equals(str))
        return "星期一";
      if ("3".equals(str))
        return "星期二";
      if ("4".equals(str))
        return "星期三";
      if ("5".equals(str))
        return "星期四";
      if ("6".equals(str))
        return "星期五";
    }
    while (!"7".equals(str));
    return "星期六";
  }

  public static int getWorkingDays(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    if (paramCalendar1.after(paramCalendar2))
    {
      Calendar localCalendar = paramCalendar1;
      paramCalendar1 = paramCalendar2;
      paramCalendar2 = localCalendar;
    }
    getDaysBetween(paramCalendar1, paramCalendar2);
    int i = 7 - paramCalendar1.get(7);
    int j = 7 - paramCalendar2.get(7);
    int k = 0;
    if (i != 0)
    {
      k = 0;
      if (i != 6)
        k = i - 1;
    }
    int m = 0;
    if (j != 0)
    {
      m = 0;
      if (j != 6)
        m = j - 1;
    }
    return k + 5 * (getDaysBetween(getNextMonday(paramCalendar1), getNextMonday(paramCalendar2)) / 7) - m;
  }

  public static boolean isLeapYear(String paramString)
  {
    Date localDate = TimeConvertUtil.strToDate(paramString);
    GregorianCalendar localGregorianCalendar = (GregorianCalendar)Calendar.getInstance();
    localGregorianCalendar.setTime(localDate);
    int i = localGregorianCalendar.get(1);
    if (i % 400 == 0);
    while (true)
    {
      return true;
      if (i % 4 != 0)
        break;
      if (i % 100 == 0)
        return false;
    }
    return false;
  }

  public static boolean isSameWeekDates(Date paramDate1, Date paramDate2)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar1.setTime(paramDate1);
    localCalendar2.setTime(paramDate2);
    int i = localCalendar1.get(1) - localCalendar2.get(1);
    if (i == 0)
    {
      if (localCalendar1.get(3) != localCalendar2.get(3));
    }
    else
      do
      {
        return true;
        if ((1 != i) || (11 != localCalendar2.get(2)))
          break;
      }
      while (localCalendar1.get(3) == localCalendar2.get(3));
    do
      return false;
    while ((-1 != i) || (11 != localCalendar1.get(2)) || (localCalendar1.get(3) != localCalendar2.get(3)));
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.TimeUtil
 * JD-Core Version:    0.6.0
 */