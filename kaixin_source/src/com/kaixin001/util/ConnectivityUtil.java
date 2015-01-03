package com.kaixin001.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class ConnectivityUtil
{
  public static boolean isMobileConnected(Context paramContext)
  {
    if (paramContext == null);
    while (true)
    {
      return true;
      try
      {
        NetworkInfo.State localState1 = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(0).getState();
        NetworkInfo.State localState2 = NetworkInfo.State.CONNECTED;
        if (localState2 != localState1)
          return false;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return false;
  }

  public static boolean isNetworkAvailable(Context paramContext)
  {
    if (paramContext == null)
      return true;
    try
    {
      boolean bool = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo().isAvailable();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static boolean isWifiConnected(Context paramContext)
  {
    if (paramContext == null);
    while (true)
    {
      return true;
      try
      {
        NetworkInfo.State localState1 = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(1).getState();
        NetworkInfo.State localState2 = NetworkInfo.State.CONNECTED;
        if (localState2 != localState1)
          return false;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.ConnectivityUtil
 * JD-Core Version:    0.6.0
 */