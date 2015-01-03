package com.amap.mapapi.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class b
{
  public static String a;
  private static b b = null;
  private static String c = null;
  private static Context d = null;
  private static TelephonyManager e;
  private static ConnectivityManager f;
  private static String g;
  private static String h;

  static
  {
    a = "";
  }

  public static b a(Context paramContext)
  {
    if (b == null)
    {
      b = new b();
      d = paramContext;
      e = (TelephonyManager)d.getSystemService("phone");
      f = (ConnectivityManager)d.getSystemService("connectivity");
      g = d.getApplicationContext().getPackageName();
      h = f();
      a = b(d);
    }
    return b;
  }

  public static String a()
  {
    return g;
  }

  public static String b(Context paramContext)
  {
    if ((a == null) || (a.equals("")));
    try
    {
      a = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128).metaData.getString("com.amap.api.v2.apikey");
      return a;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private static String f()
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    ((WindowManager)d.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
    int i = localDisplayMetrics.widthPixels;
    int j = localDisplayMetrics.heightPixels;
    if (j > i);
    for (String str = i + "*" + j; ; str = j + "*" + i)
    {
      h = str;
      return h;
    }
  }

  public String b()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ia=1&");
    if ((a != null) && (a.length() > 0))
    {
      localStringBuilder.append("key=");
      localStringBuilder.append(a);
      localStringBuilder.append("&");
    }
    localStringBuilder.append("ct=android");
    String str1 = e.getDeviceId();
    String str2 = e.getSubscriberId();
    localStringBuilder.append("&ime=" + str1);
    localStringBuilder.append("&sim=" + str2);
    localStringBuilder.append("&pkg=" + g);
    localStringBuilder.append("&mod=");
    localStringBuilder.append(d());
    localStringBuilder.append("&sv=");
    localStringBuilder.append(c());
    localStringBuilder.append("&nt=");
    localStringBuilder.append(e());
    String str3 = e.getNetworkOperatorName();
    localStringBuilder.append("&np=");
    localStringBuilder.append(str3);
    localStringBuilder.append("&ctm=" + System.currentTimeMillis());
    localStringBuilder.append("&re=" + h);
    localStringBuilder.append("&av=" + c.l);
    return localStringBuilder.toString();
  }

  public String c()
  {
    return Build.VERSION.RELEASE;
  }

  public String d()
  {
    return Build.MODEL;
  }

  public String e()
  {
    if (d.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0)
      return "";
    if (f == null)
      return "";
    NetworkInfo localNetworkInfo = f.getActiveNetworkInfo();
    if (localNetworkInfo == null)
      return "";
    return localNetworkInfo.getTypeName();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.b
 * JD-Core Version:    0.6.0
 */