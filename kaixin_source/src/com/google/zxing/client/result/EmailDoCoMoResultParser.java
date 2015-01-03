package com.google.zxing.client.result;

import com.google.zxing.Result;

final class EmailDoCoMoResultParser extends AbstractDoCoMoResultParser
{
  private static final char[] ATEXT_SYMBOLS = { 64, 46, 33, 35, 36, 37, 38, 39, 42, 43, 45, 47, 61, 63, 94, 95, 96, 123, 124, 125, 126 };

  private static boolean isAtextSymbol(char paramChar)
  {
    for (int i = 0; ; i++)
    {
      if (i >= ATEXT_SYMBOLS.length)
        return false;
      if (paramChar == ATEXT_SYMBOLS[i])
        return true;
    }
  }

  static boolean isBasicallyValidEmailAddress(String paramString)
  {
    int i;
    if (paramString == null)
      i = 0;
    while (true)
    {
      return i;
      i = 0;
      for (int j = 0; j < paramString.length(); j++)
      {
        char c = paramString.charAt(j);
        if (((c < 'a') || (c > 'z')) && ((c < 'A') || (c > 'Z')) && ((c < '0') || (c > '9')) && (!isAtextSymbol(c)))
          return false;
        if (c != '@')
          continue;
        if (i != 0)
          return false;
        i = 1;
      }
    }
  }

  public static EmailAddressParsedResult parse(Result paramResult)
  {
    String str1 = paramResult.getText();
    if ((str1 == null) || (!str1.startsWith("MATMSG:")));
    String str2;
    do
    {
      String[] arrayOfString;
      do
      {
        return null;
        arrayOfString = matchDoCoMoPrefixedField("TO:", str1, true);
      }
      while (arrayOfString == null);
      str2 = arrayOfString[0];
    }
    while (!isBasicallyValidEmailAddress(str2));
    return new EmailAddressParsedResult(str2, matchSingleDoCoMoPrefixedField("SUB:", str1, false), matchSingleDoCoMoPrefixedField("BODY:", str1, false), "mailto:" + str2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.EmailDoCoMoResultParser
 * JD-Core Version:    0.6.0
 */