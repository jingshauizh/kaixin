package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Hashtable;
import java.util.Vector;

final class SMSMMSResultParser extends ResultParser
{
  private static void addNumberVia(Vector paramVector1, Vector paramVector2, String paramString)
  {
    int i = paramString.indexOf(';');
    if (i < 0)
    {
      paramVector1.addElement(paramString);
      paramVector2.addElement(null);
      return;
    }
    paramVector1.addElement(paramString.substring(0, i));
    String str1 = paramString.substring(i + 1);
    if (str1.startsWith("via="));
    for (String str2 = str1.substring(4); ; str2 = null)
    {
      paramVector2.addElement(str2);
      return;
    }
  }

  public static SMSParsedResult parse(Result paramResult)
  {
    String str1 = paramResult.getText();
    if (str1 == null)
      return null;
    if ((!str1.startsWith("sms:")) && (!str1.startsWith("SMS:")) && (!str1.startsWith("mms:")) && (!str1.startsWith("MMS:")))
      return null;
    Hashtable localHashtable = parseNameValuePairs(str1);
    String str2 = null;
    int i = 0;
    String str3 = null;
    if (localHashtable != null)
    {
      boolean bool = localHashtable.isEmpty();
      str2 = null;
      i = 0;
      str3 = null;
      if (!bool)
      {
        str3 = (String)localHashtable.get("subject");
        str2 = (String)localHashtable.get("body");
        i = 1;
      }
    }
    int j = str1.indexOf('?', 4);
    String str4;
    int k;
    Vector localVector1;
    Vector localVector2;
    if ((j < 0) || (i == 0))
    {
      str4 = str1.substring(4);
      k = -1;
      localVector1 = new Vector(1);
      localVector2 = new Vector(1);
    }
    while (true)
    {
      int m = str4.indexOf(',', k + 1);
      if (m <= k)
      {
        addNumberVia(localVector1, localVector2, str4.substring(k + 1));
        return new SMSParsedResult(toStringArray(localVector1), toStringArray(localVector2), str3, str2);
        str4 = str1.substring(4, j);
        break;
      }
      addNumberVia(localVector1, localVector2, str4.substring(k + 1, m));
      k = m;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.SMSMMSResultParser
 * JD-Core Version:    0.6.0
 */