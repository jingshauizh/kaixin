package com.google.zxing.client.result;

import com.google.zxing.Result;

final class GeoResultParser extends ResultParser
{
  public static GeoParsedResult parse(Result paramResult)
  {
    String str1 = paramResult.getText();
    if ((str1 == null) || ((!str1.startsWith("geo:")) && (!str1.startsWith("GEO:"))))
      return null;
    int i = str1.indexOf('?', 4);
    String str2;
    if (i < 0)
      str2 = null;
    int j;
    for (String str3 = str1.substring(4); ; str3 = str1.substring(4, i))
    {
      j = str3.indexOf(',');
      if (j >= 0)
        break;
      return null;
      str2 = str1.substring(i + 1);
    }
    int k = str3.indexOf(',', j + 1);
    double d1;
    double d2;
    double d3;
    try
    {
      d1 = Double.parseDouble(str3.substring(0, j));
      if ((d1 <= 90.0D) && (d1 >= -90.0D))
      {
        if (k < 0)
        {
          d2 = Double.parseDouble(str3.substring(j + 1));
          d3 = 0.0D;
          break label215;
        }
        d2 = Double.parseDouble(str3.substring(j + 1, k));
        double d4 = Double.parseDouble(str3.substring(k + 1));
        d3 = d4;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return null;
    }
    label215: 
    do
    {
      return new GeoParsedResult(d1, d2, d3, str2);
      return null;
    }
    while ((d2 <= 180.0D) && (d2 >= -180.0D) && (d3 >= 0.0D));
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.GeoResultParser
 * JD-Core Version:    0.6.0
 */