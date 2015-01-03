package com.tencent.sdkutil;

import com.tencent.tauth.Tencent;
import java.util.HashMap;

public class DataStorage
{
  static HashMap<String, Tencent> sessionMap = new HashMap();

  public static Tencent getTencentInstance(String paramString)
  {
    if (sessionMap.containsKey(paramString))
      return (Tencent)sessionMap.get(paramString);
    return null;
  }

  public static boolean hasTencentInstance(String paramString)
  {
    return sessionMap.containsKey(paramString);
  }

  public static void setTencentInstance(String paramString, Tencent paramTencent)
  {
    sessionMap.put(paramString, paramTencent);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.DataStorage
 * JD-Core Version:    0.6.0
 */