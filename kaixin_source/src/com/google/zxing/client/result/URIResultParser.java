package com.google.zxing.client.result;

import com.google.zxing.Result;

final class URIResultParser extends ResultParser
{
  static boolean isBasicallyValidURI(String paramString)
  {
    if ((paramString == null) || (paramString.indexOf(' ') >= 0) || (paramString.indexOf('\n') >= 0));
    int i;
    int j;
    do
    {
      do
      {
        return false;
        i = paramString.indexOf('.');
      }
      while (i >= -2 + paramString.length());
      j = paramString.indexOf(':');
    }
    while ((i < 0) && (j < 0));
    int k;
    if (j >= 0)
    {
      if ((i >= 0) && (i <= j))
        break label120;
      k = 0;
      label72: if (k < j)
        break label79;
    }
    label170: 
    while (true)
    {
      return true;
      label79: int m = paramString.charAt(k);
      if (((m < 97) || (m > 122)) && ((m < 65) || (m > 90)))
        break;
      k++;
      break label72;
      label120: if (j >= -2 + paramString.length())
        break;
      for (int n = j + 1; ; n++)
      {
        if (n >= j + 3)
          break label170;
        int i1 = paramString.charAt(n);
        if ((i1 < 48) || (i1 > 57))
          break;
      }
    }
  }

  public static URIParsedResult parse(Result paramResult)
  {
    String str = paramResult.getText();
    if ((str != null) && (str.startsWith("URL:")))
      str = str.substring(4);
    if (str != null)
      str = str.trim();
    if (!isBasicallyValidURI(str))
      return null;
    return new URIParsedResult(str, null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.URIResultParser
 * JD-Core Version:    0.6.0
 */