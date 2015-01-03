package com.tencent.mm.sdk.platformtools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SemiXml
{
  public static final String MAGIC_HEAD = "~SEMI_XML~";

  public static Map<String, String> decode(String paramString)
  {
    if ((paramString == null) || (!paramString.startsWith("~SEMI_XML~")))
    {
      localHashMap = null;
      return localHashMap;
    }
    String str1 = paramString.substring(10);
    HashMap localHashMap = new HashMap();
    int i = 0;
    int j = -4 + str1.length();
    while (i < j)
    {
      int k = i + 1;
      try
      {
        int m = str1.charAt(i) << '\020';
        int n = k + 1;
        int i1 = n + (m + str1.charAt(k));
        String str2 = str1.substring(n, i1);
        int i2 = i1 + 1;
        int i3 = str1.charAt(i1) << '\020';
        int i4 = i2 + 1;
        i = i4 + (i3 + str1.charAt(i2));
        localHashMap.put(str2, str1.substring(i4, i));
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return localHashMap;
  }

  public static String encode(Map<String, String> paramMap)
  {
    if (paramMap == null)
      return null;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("~SEMI_XML~");
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str1 = (String)localEntry.getKey();
      String str2 = (String)localEntry.getValue();
      if (str2 == null)
        continue;
      int i = str1.length();
      int j = str2.length();
      localStringBuilder.append((char)(i >> 16)).append((char)i).append(str1);
      localStringBuilder.append((char)(j >> 16)).append((char)j).append(str2);
    }
    return localStringBuilder.toString();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.SemiXml
 * JD-Core Version:    0.6.0
 */