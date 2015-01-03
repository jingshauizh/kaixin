package com.google.zxing.client.result;

import com.google.zxing.Result;

final class VEventResultParser extends ResultParser
{
  public static CalendarParsedResult parse(Result paramResult)
  {
    String str1 = paramResult.getText();
    if (str1 == null)
      return null;
    if (str1.indexOf("BEGIN:VEVENT") < 0)
      return null;
    String str2 = VCardResultParser.matchSingleVCardPrefixedField("SUMMARY", str1, true);
    String str3 = VCardResultParser.matchSingleVCardPrefixedField("DTSTART", str1, true);
    String str4 = VCardResultParser.matchSingleVCardPrefixedField("DTEND", str1, true);
    String str5 = VCardResultParser.matchSingleVCardPrefixedField("LOCATION", str1, true);
    String str6 = VCardResultParser.matchSingleVCardPrefixedField("DESCRIPTION", str1, true);
    String str7 = VCardResultParser.matchSingleVCardPrefixedField("GEO", str1, true);
    double d1;
    double d3;
    if (str7 == null)
    {
      d1 = (0.0D / 0.0D);
      d3 = (0.0D / 0.0D);
    }
    while (true)
    {
      try
      {
        CalendarParsedResult localCalendarParsedResult = new CalendarParsedResult(str2, str3, str4, str5, null, str6, d1, d3);
        return localCalendarParsedResult;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        return null;
      }
      int i = str7.indexOf(';');
      try
      {
        d1 = Double.parseDouble(str7.substring(0, i));
        double d2 = Double.parseDouble(str7.substring(i + 1));
        d3 = d2;
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
    }
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.VEventResultParser
 * JD-Core Version:    0.6.0
 */