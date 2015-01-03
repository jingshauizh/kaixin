package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Vector;

final class BizcardResultParser extends AbstractDoCoMoResultParser
{
  private static String buildName(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return paramString2;
    if (paramString2 == null);
    while (true)
    {
      return paramString1;
      paramString1 = paramString1 + ' ' + paramString2;
    }
  }

  private static String[] buildPhoneNumbers(String paramString1, String paramString2, String paramString3)
  {
    Vector localVector = new Vector(3);
    if (paramString1 != null)
      localVector.addElement(paramString1);
    if (paramString2 != null)
      localVector.addElement(paramString2);
    if (paramString3 != null)
      localVector.addElement(paramString3);
    int i = localVector.size();
    String[] arrayOfString;
    if (i == 0)
      arrayOfString = null;
    while (true)
    {
      return arrayOfString;
      arrayOfString = new String[i];
      for (int j = 0; j < i; j++)
        arrayOfString[j] = ((String)localVector.elementAt(j));
    }
  }

  public static AddressBookParsedResult parse(Result paramResult)
  {
    String str1 = paramResult.getText();
    if ((str1 == null) || (!str1.startsWith("BIZCARD:")))
      return null;
    String str2 = buildName(matchSingleDoCoMoPrefixedField("N:", str1, true), matchSingleDoCoMoPrefixedField("X:", str1, true));
    String str3 = matchSingleDoCoMoPrefixedField("T:", str1, true);
    String str4 = matchSingleDoCoMoPrefixedField("C:", str1, true);
    String[] arrayOfString = matchDoCoMoPrefixedField("A:", str1, true);
    String str5 = matchSingleDoCoMoPrefixedField("B:", str1, true);
    String str6 = matchSingleDoCoMoPrefixedField("M:", str1, true);
    String str7 = matchSingleDoCoMoPrefixedField("F:", str1, true);
    String str8 = matchSingleDoCoMoPrefixedField("E:", str1, true);
    return new AddressBookParsedResult(maybeWrap(str2), null, buildPhoneNumbers(str5, str6, str7), maybeWrap(str8), null, arrayOfString, str4, null, str3, null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.BizcardResultParser
 * JD-Core Version:    0.6.0
 */