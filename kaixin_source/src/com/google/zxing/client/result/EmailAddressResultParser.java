package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Hashtable;

final class EmailAddressResultParser extends ResultParser
{
  public static EmailAddressParsedResult parse(Result paramResult)
  {
    String str1 = paramResult.getText();
    if (str1 == null);
    do
    {
      return null;
      if ((!str1.startsWith("mailto:")) && (!str1.startsWith("MAILTO:")))
        continue;
      String str2 = str1.substring(7);
      int i = str2.indexOf('?');
      if (i >= 0)
        str2 = str2.substring(0, i);
      Hashtable localHashtable = parseNameValuePairs(str1);
      String str3 = null;
      String str4 = null;
      if (localHashtable != null)
      {
        if (str2.length() == 0)
          str2 = (String)localHashtable.get("to");
        str4 = (String)localHashtable.get("subject");
        str3 = (String)localHashtable.get("body");
      }
      return new EmailAddressParsedResult(str2, str4, str3, str1);
    }
    while (!EmailDoCoMoResultParser.isBasicallyValidEmailAddress(str1));
    return new EmailAddressParsedResult(str1, null, null, "mailto:" + str1);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.EmailAddressResultParser
 * JD-Core Version:    0.6.0
 */