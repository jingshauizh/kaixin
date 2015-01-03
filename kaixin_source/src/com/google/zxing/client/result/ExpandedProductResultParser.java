package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import java.util.Hashtable;

final class ExpandedProductResultParser extends ResultParser
{
  private static String findAIvalue(int paramInt, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString.charAt(paramInt) != '(')
      return "ERROR";
    String str = paramString.substring(paramInt + 1);
    for (int i = 0; ; i++)
    {
      if (i >= str.length())
        return localStringBuffer.toString();
      char c = str.charAt(i);
      switch (c)
      {
      case '*':
      case '+':
      case ',':
      case '-':
      case '.':
      case '/':
      default:
        return "ERROR";
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        localStringBuffer.append(c);
      case ')':
      }
    }
    return localStringBuffer.toString();
  }

  private static String findValue(int paramInt, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = paramString.substring(paramInt);
    int i = 0;
    if (i >= str.length());
    char c;
    while (true)
    {
      return localStringBuffer.toString();
      c = str.charAt(i);
      if (c != '(')
        break;
      if (!"ERROR".equals(findAIvalue(i, str)))
        continue;
      localStringBuffer.append('(');
    }
    while (true)
    {
      i++;
      break;
      localStringBuffer.append(c);
    }
  }

  public static ExpandedProductParsedResult parse(Result paramResult)
  {
    BarcodeFormat localBarcodeFormat = paramResult.getBarcodeFormat();
    if (!BarcodeFormat.RSS_EXPANDED.equals(localBarcodeFormat))
      return null;
    String str1 = paramResult.getText();
    if (str1 == null)
      return null;
    Object localObject1 = "-";
    Object localObject2 = "-";
    Object localObject3 = "-";
    Object localObject4 = "-";
    Object localObject5 = "-";
    Object localObject6 = "-";
    Object localObject7 = "-";
    Object localObject8 = "-";
    String str2 = "-";
    String str3 = "-";
    Object localObject9 = "-";
    String str4 = "-";
    String str5 = "-";
    Hashtable localHashtable = new Hashtable();
    int i = 0;
    while (true)
    {
      int j = str1.length();
      if (i >= j)
        return new ExpandedProductParsedResult((String)localObject1, (String)localObject2, (String)localObject3, (String)localObject4, (String)localObject5, (String)localObject6, (String)localObject7, (String)localObject8, str2, str3, (String)localObject9, str4, str5, localHashtable);
      String str6 = findAIvalue(i, str1);
      if ("ERROR".equals(str6))
        return null;
      int k = i + (2 + str6.length());
      String str7 = findValue(k, str1);
      i = k + str7.length();
      if ("00".equals(str6))
      {
        localObject2 = str7;
        continue;
      }
      if ("01".equals(str6))
      {
        localObject1 = str7;
        continue;
      }
      if ("10".equals(str6))
      {
        localObject3 = str7;
        continue;
      }
      if ("11".equals(str6))
      {
        localObject4 = str7;
        continue;
      }
      if ("13".equals(str6))
      {
        localObject5 = str7;
        continue;
      }
      if ("15".equals(str6))
      {
        localObject6 = str7;
        continue;
      }
      if ("17".equals(str6))
      {
        localObject7 = str7;
        continue;
      }
      if (("3100".equals(str6)) || ("3101".equals(str6)) || ("3102".equals(str6)) || ("3103".equals(str6)) || ("3104".equals(str6)) || ("3105".equals(str6)) || ("3106".equals(str6)) || ("3107".equals(str6)) || ("3108".equals(str6)) || ("3109".equals(str6)))
      {
        localObject8 = str7;
        str2 = "KG";
        str3 = str6.substring(3);
        continue;
      }
      if (("3200".equals(str6)) || ("3201".equals(str6)) || ("3202".equals(str6)) || ("3203".equals(str6)) || ("3204".equals(str6)) || ("3205".equals(str6)) || ("3206".equals(str6)) || ("3207".equals(str6)) || ("3208".equals(str6)) || ("3209".equals(str6)))
      {
        localObject8 = str7;
        str2 = "LB";
        str3 = str6.substring(3);
        continue;
      }
      if (("3920".equals(str6)) || ("3921".equals(str6)) || ("3922".equals(str6)) || ("3923".equals(str6)))
      {
        localObject9 = str7;
        str4 = str6.substring(3);
        continue;
      }
      if (("3930".equals(str6)) || ("3931".equals(str6)) || ("3932".equals(str6)) || ("3933".equals(str6)))
      {
        if (str7.length() < 4)
          return null;
        localObject9 = str7.substring(3);
        str5 = str7.substring(0, 3);
        str4 = str6.substring(3);
        continue;
      }
      localHashtable.put(str6, str7);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.ExpandedProductResultParser
 * JD-Core Version:    0.6.0
 */