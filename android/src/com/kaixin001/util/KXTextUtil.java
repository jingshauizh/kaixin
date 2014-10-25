package com.kaixin001.util;

import android.content.Context;
import android.content.res.Resources;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KXTextUtil
{
  private static final String RE_AT_FRIEND = "(@[0-9]+)\\(\\#(\\S+?)\\#\\)";
  private static final String TAG_AT = "@";

  public static String formatTimestamp(long paramLong)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    int i = localCalendar1.get(1);
    int j = localCalendar1.get(6);
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
    localCalendar2.setTimeInMillis(paramLong);
    int k = localCalendar2.get(1);
    int m = localCalendar2.get(6);
    String str = null;
    if (k != i)
      str = "yyyy骞碝M鏈坉d鏃�HH:mm";
    
      Date localDate = localCalendar2.getTime();
      
      if (m != j)
      {
        str = "MM鏈坉d鏃�HH:mm";
       
      }
      str = "浠婂ぉ HH:mm";
    
    return new SimpleDateFormat(str).format(localDate);
  }

  public static String getAtUserText()
  {
    return "[|s|]@[|m|]10066329[|m|]-101[|e|]";
  }

  public static String getLbsFormatDistance(double paramDouble)
  {
    if (paramDouble < 1000.0D)
      return new DecimalFormat("###绫�").format(paramDouble);
    if (paramDouble < 10000.0D)
      return new DecimalFormat("###.0鍏噷").format(paramDouble / 1000.0D);
    return new DecimalFormat("###鍏噷").format(paramDouble / 1000.0D);
  }

  public static String getLbsPoiText(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[|s|]");
    localStringBuffer.append(paramString1).append("[|m|]");
    localStringBuffer.append(paramString2).append("[|m|]");
    localStringBuffer.append("-110");
    localStringBuffer.append("[|e|]");
    return localStringBuffer.toString();
  }

  public static String getNewsDate(Context paramContext, String paramString)
  {
    try
    {
      SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("MM鏈坉d鏃�");
      Date localDate1 = localSimpleDateFormat1.parse(paramString);
      Date localDate2 = new Date();
      if ((localDate2.getYear() == localDate1.getYear()) && (localDate2.getMonth() == localDate1.getMonth()) && (localDate2.getDate() == localDate1.getDate()))
        return paramContext.getResources().getString(2131427563);
      String str = localSimpleDateFormat2.format(localDate1);
      return str;
    }
    catch (ParseException localParseException)
    {
    }
    return paramString;
  }

  public static String getNewsFormatTime(long paramLong1, long paramLong2)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
    int i = localCalendar1.get(1);
    int j = localCalendar1.get(6);
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
    localCalendar2.setTimeInMillis(paramLong1);
    int k = localCalendar2.get(1);
    int m = localCalendar2.get(6);
    if (k != i);
    for (String str = "yyyy骞碝M鏈坉d鏃�HH:mm"; ; str = "MM鏈坉d鏃�HH:mm")
    {
      Date localDate = localCalendar2.getTime();
      
      if (m == j)
        break;
    }
    long l1 = (paramLong2 - paramLong1) / 1000L;
    if (l1 < 60L)
    {
      if (l1 <= 0L)
        return "0绉掑墠";
      return l1 + "绉掑墠";
    }
    long l2 = l1 / 60L;
    if (l2 < 60L)
      return l2 + "鍒嗛挓鍓�";
    return l2 / 60L + "灏忔椂鍓�";
   
  }

  public static String getNewsTime(Context paramContext, String paramString)
  {
    try
    {
      SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("MM鏈坉d鏃�");
      SimpleDateFormat localSimpleDateFormat3 = new SimpleDateFormat("HH:mm");
      Date localDate1 = localSimpleDateFormat1.parse(paramString);
      Date localDate2 = new Date();
      if ((localDate2.getYear() == localDate1.getYear()) && (localDate2.getMonth() == localDate1.getMonth()) && (localDate2.getDay() == localDate1.getDay()));
      String str1;
     Object localObject = paramContext.getResources().getString(2131427563);

      
        String str2 = localSimpleDateFormat3.format(localDate1);
       
        str1 = localSimpleDateFormat2.format(localDate1);
      
      return localObject + " " + str2;
    }
    catch (ParseException localParseException)
    {
    	return (String)paramString;
    }
    
  }

  public static String getURLLinkText(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[|s|]");
    localStringBuffer.append(paramString1).append("[|m|]");
    localStringBuffer.append(paramString2).append("[|m|]");
    localStringBuffer.append("-103");
    localStringBuffer.append("[|e|]");
    return localStringBuffer.toString();
  }

  public static String getUserText(String paramString1, String paramString2, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[|s|]");
    localStringBuffer.append(paramString1).append("[|m|]");
    localStringBuffer.append(paramString2).append("[|m|]");
    if (paramBoolean);
    for (String str = "-1"; ; str = "0")
    {
      localStringBuffer.append(str);
      localStringBuffer.append("[|e|]");
      return localStringBuffer.toString();
    }
  }

  public static int isCurrentAtSymbol(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramString.length() > 0)
    {
      if (((paramInt3 > 0) && ("@".equals(paramString.substring(paramInt1, paramInt1 + paramInt3)))) || ((paramInt3 == 0) && (paramInt1 > 0) && ("@".equals(paramString.substring(paramInt1 - 1, paramInt1)))))
      {
        if (paramInt3 == 0)
          paramInt1--;
        return paramInt1;
      }
      return -1;
    }
    return -1;
  }

  public static String isValidNameInputing(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt4 >= 0) && (paramInt4 + 1 < paramInt1 + paramInt3))
      return paramString.substring(paramInt4 + 1, paramInt1 + paramInt3);
    return null;
  }

  public static int isValidPositionAtSymbol(String paramString, int paramInt)
  {
    if ((paramInt >= 0) && (paramString.length() > 0) && (paramInt < paramString.length()) && (paramString.substring(paramInt, paramInt + 1).equals("@")))
      return paramInt;
    return -1;
  }

  public static String tranformAtFriend(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Matcher localMatcher = Pattern.compile("(@[0-9]+)\\(\\#(\\S+?)\\#\\)").matcher(paramString);
    int i = 0;
    while (true)
    {
      if (!localMatcher.find())
      {
        localStringBuffer.append(paramString.substring(i, paramString.length()));
        return localStringBuffer.toString();
      }
      localStringBuffer.append(paramString.substring(i, localMatcher.start()));
      localStringBuffer.append(localMatcher.group(1));
      i = localMatcher.end();
      if ((i >= paramString.length()) || (' ' == paramString.charAt(i)))
        continue;
      localStringBuffer.append(" ");
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.KXTextUtil
 * JD-Core Version:    0.6.0
 */