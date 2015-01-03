package com.umeng.analytics;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.umeng.common.Log;

class f
{
  public static String a(Context paramContext)
  {
    if (paramContext.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", paramContext.getPackageName()) != 0)
      return null;
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo == null)
        return null;
      if (localNetworkInfo.getType() == 1)
        return null;
      String str = localNetworkInfo.getExtraInfo();
      Log.a("TAG", "net type:" + str);
      if (str == null)
        return null;
      if ((str.equals("cmwap")) || (str.equals("3gwap")) || (str.equals("uniwap")))
        return "10.0.0.172";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.f
 * JD-Core Version:    0.6.0
 */