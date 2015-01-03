package com.tencent.mm.sdk.platformtools;

import java.util.HashMap;

public final class SystemProperty
{
  private static final HashMap<String, String> bl = new HashMap();

  public static String getProperty(String paramString)
  {
    return (String)bl.get(paramString);
  }

  public static void setProperty(String paramString1, String paramString2)
  {
    bl.put(paramString1, paramString2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.SystemProperty
 * JD-Core Version:    0.6.0
 */