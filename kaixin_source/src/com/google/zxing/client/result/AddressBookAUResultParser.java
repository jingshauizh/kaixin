package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Vector;

final class AddressBookAUResultParser extends ResultParser
{
  private static String[] matchMultipleValuePrefix(String paramString1, int paramInt, String paramString2, boolean paramBoolean)
  {
    Vector localVector = null;
    for (int i = 1; ; i++)
    {
      if (i > paramInt);
      String str;
      do
      {
        if (localVector != null)
          break;
        return null;
        str = matchSinglePrefixedField(paramString1 + i + ':', paramString2, '\r', paramBoolean);
      }
      while (str == null);
      if (localVector == null)
        localVector = new Vector(paramInt);
      localVector.addElement(str);
    }
    return toStringArray(localVector);
  }

  public static AddressBookParsedResult parse(Result paramResult)
  {
    String str1 = paramResult.getText();
    if ((str1 == null) || (str1.indexOf("MEMORY") < 0) || (str1.indexOf("\r\n") < 0))
      return null;
    String str2 = matchSinglePrefixedField("NAME1:", str1, '\r', true);
    String str3 = matchSinglePrefixedField("NAME2:", str1, '\r', true);
    String[] arrayOfString1 = matchMultipleValuePrefix("TEL", 3, str1, true);
    String[] arrayOfString2 = matchMultipleValuePrefix("MAIL", 3, str1, true);
    String str4 = matchSinglePrefixedField("MEMORY:", str1, '\r', false);
    String str5 = matchSinglePrefixedField("ADD:", str1, '\r', true);
    if (str5 == null);
    for (String[] arrayOfString3 = null; ; arrayOfString3 = new String[] { str5 })
      return new AddressBookParsedResult(maybeWrap(str2), str3, arrayOfString1, arrayOfString2, str4, arrayOfString3, null, null, null, null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.AddressBookAUResultParser
 * JD-Core Version:    0.6.0
 */