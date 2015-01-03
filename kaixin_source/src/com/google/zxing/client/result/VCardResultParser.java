package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

final class VCardResultParser extends ResultParser
{
  private static String decodeQuotedPrintable(String paramString1, String paramString2)
  {
    int i = paramString1.length();
    StringBuffer localStringBuffer = new StringBuffer(i);
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int j = 0;
    if (j >= i)
    {
      maybeAppendFragment(localByteArrayOutputStream, paramString2, localStringBuffer);
      return localStringBuffer.toString();
    }
    char c1 = paramString1.charAt(j);
    switch (c1)
    {
    default:
      maybeAppendFragment(localByteArrayOutputStream, paramString2, localStringBuffer);
      localStringBuffer.append(c1);
    case '\n':
    case '\r':
    case '=':
    }
    while (true)
    {
      j++;
      break;
      if (j >= i - 2)
        continue;
      char c2 = paramString1.charAt(j + 1);
      if ((c2 == '\r') || (c2 == '\n'))
        continue;
      char c3 = paramString1.charAt(j + 2);
      try
      {
        localByteArrayOutputStream.write(16 * toHexValue(c2) + toHexValue(c3));
        label169: j += 2;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        break label169;
      }
    }
  }

  private static String formatAddress(String paramString)
  {
    if (paramString == null)
      return null;
    int i = paramString.length();
    StringBuffer localStringBuffer = new StringBuffer(i);
    int j = 0;
    if (j >= i)
      return localStringBuffer.toString().trim();
    char c = paramString.charAt(j);
    if (c == ';')
      localStringBuffer.append(' ');
    while (true)
    {
      j++;
      break;
      localStringBuffer.append(c);
    }
  }

  private static void formatNames(String[] paramArrayOfString)
  {
    int i;
    if (paramArrayOfString != null)
    {
      i = 0;
      if (i < paramArrayOfString.length);
    }
    else
    {
      return;
    }
    String str = paramArrayOfString[i];
    String[] arrayOfString = new String[5];
    int j = 0;
    int k = 0;
    while (true)
    {
      int m = str.indexOf(';', j);
      if (m <= 0)
      {
        arrayOfString[k] = str.substring(j);
        StringBuffer localStringBuffer = new StringBuffer(100);
        maybeAppendComponent(arrayOfString, 3, localStringBuffer);
        maybeAppendComponent(arrayOfString, 1, localStringBuffer);
        maybeAppendComponent(arrayOfString, 2, localStringBuffer);
        maybeAppendComponent(arrayOfString, 0, localStringBuffer);
        maybeAppendComponent(arrayOfString, 4, localStringBuffer);
        paramArrayOfString[i] = localStringBuffer.toString().trim();
        i++;
        break;
      }
      arrayOfString[k] = str.substring(j, m);
      k++;
      j = m + 1;
    }
  }

  private static boolean isLikeVCardDate(String paramString)
  {
    if (paramString == null);
    do
      return true;
    while ((isStringOfDigits(paramString, 8)) || ((paramString.length() == 10) && (paramString.charAt(4) == '-') && (paramString.charAt(7) == '-') && (isSubstringOfDigits(paramString, 0, 4)) && (isSubstringOfDigits(paramString, 5, 2)) && (isSubstringOfDigits(paramString, 8, 2))));
    return false;
  }

  static String matchSingleVCardPrefixedField(String paramString1, String paramString2, boolean paramBoolean)
  {
    String[] arrayOfString = matchVCardPrefixedField(paramString1, paramString2, paramBoolean);
    if (arrayOfString == null)
      return null;
    return arrayOfString[0];
  }

  private static String[] matchVCardPrefixedField(String paramString1, String paramString2, boolean paramBoolean)
  {
    Vector localVector = null;
    int i = 0;
    int j = paramString2.length();
    while (true)
    {
      if (i >= j);
      int k;
      do
      {
        if ((localVector != null) && (!localVector.isEmpty()))
          break;
        return null;
        k = paramString2.indexOf(paramString1, i);
      }
      while (k < 0);
      if ((k > 0) && (paramString2.charAt(k - 1) != '\n'))
      {
        i = k + 1;
        continue;
      }
      i = k + paramString1.length();
      if ((paramString2.charAt(i) != ':') && (paramString2.charAt(i) != ';'))
        continue;
      int m = i;
      label107: int n;
      Object localObject;
      int i4;
      int i1;
      int i2;
      if (paramString2.charAt(i) == ':')
      {
        n = 0;
        localObject = null;
        if (i > m)
        {
          i4 = m + 1;
          if (i4 <= i)
            break label187;
        }
        i1 = i + 1;
        i2 = i1;
      }
      label154: int i3;
      while (true)
      {
        i3 = paramString2.indexOf('\n', i1);
        if (i3 < 0);
        label187: 
        do
        {
          if (i3 >= 0)
            break label392;
          i = j;
          break;
          i++;
          break label107;
          String str4;
          String str5;
          if ((paramString2.charAt(i4) == ';') || (paramString2.charAt(i4) == ':'))
          {
            String str3 = paramString2.substring(m + 1, i4);
            int i5 = str3.indexOf('=');
            if (i5 >= 0)
            {
              str4 = str3.substring(0, i5);
              str5 = str3.substring(i5 + 1);
              if (!"ENCODING".equalsIgnoreCase(str4))
                break label289;
              if ("QUOTED-PRINTABLE".equalsIgnoreCase(str5))
                n = 1;
            }
          }
          while (true)
          {
            m = i4;
            i4++;
            break;
            if (!"CHARSET".equalsIgnoreCase(str4))
              continue;
            localObject = str5;
          }
          if ((i3 >= -1 + paramString2.length()) || ((paramString2.charAt(i3 + 1) != ' ') && (paramString2.charAt(i3 + 1) != '\t')))
            continue;
          i1 = i3 + 2;
          break label154;
        }
        while ((n == 0) || ((paramString2.charAt(i3 - 1) != '=') && (paramString2.charAt(i3 - 2) != '=')));
        label289: i1 = i3 + 1;
      }
      label392: if (i3 > i2)
      {
        if (localVector == null)
          localVector = new Vector(1);
        if (paramString2.charAt(i3 - 1) == '\r')
          i3--;
        String str1 = paramString2.substring(i2, i3);
        if (paramBoolean)
          str1 = str1.trim();
        if (n != 0);
        for (String str2 = decodeQuotedPrintable(str1, localObject); ; str2 = stripContinuationCRLF(str1))
        {
          localVector.addElement(str2);
          i = i3 + 1;
          break;
        }
      }
      i = i3 + 1;
    }
    return toStringArray(localVector);
  }

  private static void maybeAppendComponent(String[] paramArrayOfString, int paramInt, StringBuffer paramStringBuffer)
  {
    if (paramArrayOfString[paramInt] != null)
    {
      paramStringBuffer.append(' ');
      paramStringBuffer.append(paramArrayOfString[paramInt]);
    }
  }

  private static void maybeAppendFragment(ByteArrayOutputStream paramByteArrayOutputStream, String paramString, StringBuffer paramStringBuffer)
  {
    byte[] arrayOfByte;
    String str;
    if (paramByteArrayOutputStream.size() > 0)
    {
      arrayOfByte = paramByteArrayOutputStream.toByteArray();
      if (paramString != null)
        break label38;
      str = new String(arrayOfByte);
    }
    while (true)
    {
      paramByteArrayOutputStream.reset();
      paramStringBuffer.append(str);
      return;
      try
      {
        label38: str = new String(arrayOfByte, paramString);
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        str = new String(arrayOfByte);
      }
    }
  }

  public static AddressBookParsedResult parse(Result paramResult)
  {
    String str1 = paramResult.getText();
    if ((str1 == null) || (!str1.startsWith("BEGIN:VCARD")))
      return null;
    String[] arrayOfString1 = matchVCardPrefixedField("FN", str1, true);
    if (arrayOfString1 == null)
    {
      arrayOfString1 = matchVCardPrefixedField("N", str1, true);
      formatNames(arrayOfString1);
    }
    String[] arrayOfString2 = matchVCardPrefixedField("TEL", str1, true);
    String[] arrayOfString3 = matchVCardPrefixedField("EMAIL", str1, true);
    String str2 = matchSingleVCardPrefixedField("NOTE", str1, false);
    String[] arrayOfString4 = matchVCardPrefixedField("ADR", str1, true);
    if (arrayOfString4 != null);
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfString4.length)
      {
        String str3 = matchSingleVCardPrefixedField("ORG", str1, true);
        String str4 = matchSingleVCardPrefixedField("BDAY", str1, true);
        if (!isLikeVCardDate(str4))
          str4 = null;
        return new AddressBookParsedResult(arrayOfString1, null, arrayOfString2, arrayOfString3, str2, arrayOfString4, str3, str4, matchSingleVCardPrefixedField("TITLE", str1, true), matchSingleVCardPrefixedField("URL", str1, true));
      }
      arrayOfString4[i] = formatAddress(arrayOfString4[i]);
    }
  }

  private static String stripContinuationCRLF(String paramString)
  {
    int i = paramString.length();
    StringBuffer localStringBuffer = new StringBuffer(i);
    int j = 0;
    int k = 0;
    if (k >= i)
      return localStringBuffer.toString();
    if (j != 0)
      j = 0;
    while (true)
    {
      k++;
      break;
      char c = paramString.charAt(k);
      j = 0;
      switch (c)
      {
      case '\r':
      case '\013':
      case '\f':
      default:
        localStringBuffer.append(c);
        j = 0;
        break;
      case '\n':
        j = 1;
      }
    }
  }

  private static int toHexValue(char paramChar)
  {
    if ((paramChar >= '0') && (paramChar <= '9'))
      return paramChar - '0';
    if ((paramChar >= 'A') && (paramChar <= 'F'))
      return 10 + (paramChar - 'A');
    if ((paramChar >= 'a') && (paramChar <= 'f'))
      return 10 + (paramChar - 'a');
    throw new IllegalArgumentException();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.VCardResultParser
 * JD-Core Version:    0.6.0
 */