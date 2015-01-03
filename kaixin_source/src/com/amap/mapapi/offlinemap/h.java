package com.amap.mapapi.offlinemap;

import android.util.Log;

public class h
{
  public static void a(int paramInt)
  {
    long l = paramInt;
    try
    {
      Thread.sleep(l);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static void a(String paramString)
  {
    Log.e(paramString, paramString);
  }

  public static void b(int paramInt)
  {
    Log.e("status:" + paramInt, "complete:" + paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.offlinemap.h
 * JD-Core Version:    0.6.0
 */