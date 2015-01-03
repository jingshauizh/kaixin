package com.kaixin001.util;

import android.graphics.Bitmap;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import com.kaixin001.model.FaceModel;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
  private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

  public static String MD5Encode(String paramString)
  {
    try
    {
      String str = byteArrayToHexString(MessageDigest.getInstance("MD5").digest(paramString.getBytes()));
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static String byteArrayToHexString(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfByte.length)
        return localStringBuffer.toString();
      localStringBuffer.append(byteToHexString(paramArrayOfByte[i]));
    }
  }

  private static String byteToHexString(byte paramByte)
  {
    int i = paramByte;
    if (i < 0)
      i += 256;
    int j = i / 16;
    int k = i % 16;
    return hexDigits[j] + hexDigits[k];
  }

  public static int charLength(String paramString)
  {
    int i = paramString.length();
    int j = 0;
    int k = 0;
    if (k >= i)
      return j;
    int m = paramString.charAt(k);
    if ((m >= 19968) && (m <= 40891))
      j += 2;
    while (true)
    {
      k++;
      break;
      j++;
    }
  }

  public static SpannableString convertTextToMessageFace(String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    SpannableString localSpannableString = null;
    if (bool);
    ArrayList localArrayList1;
    ArrayList localArrayList2;
    do
    {
      do
      {
        return localSpannableString;
        FaceModel localFaceModel = FaceModel.getInstance();
        localArrayList1 = localFaceModel.getStateFaceStrings();
        localArrayList2 = localFaceModel.getStateFaceIcons();
        localSpannableString = null;
      }
      while (localArrayList1 == null);
      localSpannableString = null;
    }
    while (localArrayList2 == null);
    localSpannableString = new SpannableString(paramString);
    int i = 0;
    label55: String str;
    if (i < localArrayList1.size())
      str = (String)localArrayList1.get(i);
    int k;
    for (int j = paramString.indexOf(str); ; j = paramString.indexOf(str, k))
    {
      if (j < 0)
      {
        i++;
        break label55;
        break;
      }
      k = j + str.length();
      localSpannableString.setSpan(new ImageSpan((Bitmap)localArrayList2.get(i)), j, k, 33);
    }
  }

  public static SpannableString convertTextToStateFace(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      localSpannableString = null;
      return localSpannableString;
    }
    FaceModel localFaceModel = FaceModel.getInstance();
    ArrayList localArrayList1 = localFaceModel.getStateFaceStrings();
    ArrayList localArrayList2 = localFaceModel.getStateFaceIcons();
    if ((localArrayList1 == null) || (localArrayList2 == null))
      return null;
    SpannableString localSpannableString = new SpannableString(paramString);
    int i = 0;
    label50: String str;
    if (i < localArrayList1.size())
      str = (String)localArrayList1.get(i);
    int k;
    for (int j = paramString.indexOf(str); ; j = paramString.indexOf(str, k))
    {
      if (j < 0)
      {
        i++;
        break label50;
        break;
      }
      k = j + str.length();
      localSpannableString.setSpan(new ImageSpan(null, (Bitmap)localArrayList2.get(i), 1), j, k, 33);
    }
  }

  public static String getApiName(String paramString)
  {
    int i = paramString.indexOf("/");
    int j = paramString.indexOf("?");
    if (i > 0);
    while (j > 0)
    {
      return paramString.substring(i, j);
      i = 0;
    }
    return paramString.substring(i);
  }

  public static String getUrlHost(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    int i;
    do
    {
      return null;
      i = paramString.indexOf("//");
    }
    while (i < 0);
    int j = i + "//".length();
    int k = paramString.indexOf("?", j);
    if (k < 0)
      return paramString.substring(j);
    return paramString.substring(j, k);
  }

  public static boolean isChinese(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    while (true)
    {
      return true;
      int i = paramString.length();
      for (int j = 0; j < i; j++)
      {
        int k = paramString.charAt(j);
        if ((k < 19968) || (k > 40891))
          break label46;
      }
    }
    label46: return false;
  }

  public static boolean isContainChinese(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() <= 0));
    while (true)
    {
      return false;
      int i = paramString.length();
      for (int j = 0; j < i; j++)
      {
        int k = paramString.charAt(j);
        if ((k >= 19968) && (k <= 40891))
          return true;
      }
    }
  }

  public static boolean isEmail(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return false;
    return Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*$").matcher(paramString).matches();
  }

  public static String removeAllToken(String paramString1, String paramString2)
  {
    int i = paramString1.indexOf(paramString2);
    if (i < 0)
      return paramString1;
    String str1 = paramString1.substring(0, i);
    String str2 = paramString1.substring(i + paramString2.length());
    if (i == 0)
      paramString1 = str2;
    while (true)
    {
      i = paramString1.indexOf(paramString2);
      break;
      if (i + paramString2.length() == paramString1.length())
      {
        paramString1 = str1;
        continue;
      }
      paramString1 = str1 + str2;
    }
  }

  public static String replaceTokenWith(String paramString1, String paramString2, String paramString3)
  {
    int i = paramString1.indexOf(paramString2);
    if (i != -1)
      return paramString1.substring(0, i) + paramString3 + paramString1.substring(i + paramString2.length());
    return paramString1;
  }

  public static String toUtf8(String paramString)
  {
    try
    {
      String str = new String(paramString.getBytes("UTF-8"));
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return paramString;
  }

  public boolean isChinese(char paramChar)
  {
    Character.UnicodeBlock localUnicodeBlock = Character.UnicodeBlock.of(paramChar);
    return (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (localUnicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) || (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (localUnicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION) || (localUnicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || (localUnicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.StringUtil
 * JD-Core Version:    0.6.0
 */