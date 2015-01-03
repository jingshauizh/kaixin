package com.umeng.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.umeng.common.Log;
import com.umeng.common.b;

public class ReportPolicy
{
  public static final int BATCH_AT_LAUNCH = 1;
  public static final int BATCH_BY_INTERVAL = 6;
  public static final int DAILY = 4;
  public static final int REALTIME = 0;
  public static final int WIFIONLY = 5;
  static final int a = 2;
  static final int b = 3;
  private static long c = 10000L;
  private int d = -1;
  private long e = -1L;
  private long f = -1L;

  static void a(Context paramContext, int paramInt)
  {
    if ((paramInt < 0) || (paramInt > 6))
    {
      Log.b("MobclickAgent", "Illegal value of report policy");
      return;
    }
    h.b(paramContext).edit().putInt("umeng_local_report_policy", paramInt).commit();
  }

  static void b(Context paramContext, int paramInt, long paramLong)
  {
    if ((paramInt < 0) || (paramInt > 6))
    {
      Log.b("MobclickAgent", "Illegal value of report policy");
      return;
    }
    if ((paramInt == 6) && (paramLong < c))
    {
      paramLong = c;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Long.valueOf(c);
      arrayOfObject[1] = Long.valueOf(c);
      Log.b("MobclickAgent", String.format("Report interval can't be less than %dms, reset to %dms", arrayOfObject));
    }
    h.b(paramContext).edit().putInt("umeng_local_report_policy", paramInt).putLong("report_interval", paramLong).commit();
  }

  void a(Context paramContext)
  {
    if (this.d == 4)
    {
      SharedPreferences.Editor localEditor = h.d(paramContext).edit();
      localEditor.putString(b.c(), "true");
      localEditor.commit();
    }
    do
      return;
    while (this.d != 6);
    this.e = System.currentTimeMillis();
    h.b(paramContext).edit().putLong("last_report_time", this.e).commit();
  }

  void a(Context paramContext, int paramInt, long paramLong)
  {
    if ((paramInt < 0) || (paramInt > 6))
    {
      Log.b("MobclickAgent", "Illegal value of report policy");
      return;
    }
    this.d = paramInt;
    this.f = paramLong;
    SharedPreferences.Editor localEditor = h.b(paramContext).edit().putInt("umeng_net_report_policy", paramInt);
    if (paramLong < c)
      paramLong = c;
    localEditor.putLong("report_interval", paramLong).commit();
  }

  boolean a()
  {
    if (this.d == 0);
    do
      return true;
    while ((this.d == 6) && (System.currentTimeMillis() - this.e > this.f));
    return false;
  }

  boolean a(String paramString, Context paramContext)
  {
    int i = 1;
    if ((!b.a(paramContext, "android.permission.ACCESS_NETWORK_STATE")) || (!b.m(paramContext)))
      i = 0;
    do
    {
      do
      {
        do
          return i;
        while (("flush".equals(paramString)) || ("error".equals(paramString)));
        switch (this.d)
        {
        case 0:
        case 3:
        default:
          this.d = b(paramContext);
          return i;
        case 1:
        case 6:
        case 2:
        case 4:
        case 5:
        }
      }
      while (paramString == "launch");
      do
      {
        do
          return false;
        while (System.currentTimeMillis() - this.e <= this.f);
        return i;
      }
      while (paramString != "terminate");
      return i;
    }
    while ((!h.d(paramContext).getString(b.c(), "false").equals("true")) && (paramString.equals("launch")));
    return false;
    return b.k(paramContext);
  }

  int b(Context paramContext)
  {
    SharedPreferences localSharedPreferences = h.b(paramContext);
    if (localSharedPreferences.getInt("umeng_net_report_policy", -1) != -1)
      return localSharedPreferences.getInt("umeng_net_report_policy", 1);
    return localSharedPreferences.getInt("umeng_local_report_policy", 1);
  }

  void c(Context paramContext)
  {
    if (this.d == -1)
      this.d = b(paramContext);
    if ((this.d == 6) && (this.f == -1L))
    {
      SharedPreferences localSharedPreferences = h.b(paramContext);
      this.f = localSharedPreferences.getLong("report_interval", c);
      this.e = localSharedPreferences.getLong("last_report_time", -1L);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.ReportPolicy
 * JD-Core Version:    0.6.0
 */