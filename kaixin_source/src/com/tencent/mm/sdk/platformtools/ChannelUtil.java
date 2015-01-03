package com.tencent.mm.sdk.platformtools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build.VERSION;
import java.util.Map;

public final class ChannelUtil
{
  public static final int FLAG_NULL = 0;
  public static final int FLAG_UPDATE_EXTERNAL = 1;
  public static final int FLAG_UPDATE_NOTIP = 2;
  public static int buildRev;
  public static int channelId = 0;
  public static boolean fullVersionInfo;
  public static String marketURL;
  public static String profileDeviceType = Build.VERSION.SDK_INT;
  public static boolean shouldShowGprsAlert;
  public static int updateMode = 0;

  static
  {
    buildRev = 0;
    marketURL = "market://details?id=" + MMApplicationContext.getPackageName();
    fullVersionInfo = false;
    shouldShowGprsAlert = false;
  }

  public static String formatVersion(Context paramContext, int paramInt)
  {
    int i = 0xFF & paramInt >> 8;
    String str;
    if (i == 0)
      str = (0xF & paramInt >> 24) + "." + (0xFF & paramInt >> 16);
    while (true)
    {
      Log.d("MicroMsg.SDK.ChannelUtil", "minminor " + i);
      int j = 0xFFFFFFF & paramInt;
      if (paramContext != null);
      try
      {
        PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 128);
        if (localPackageInfo != null)
        {
          j = localPackageInfo.versionCode;
          str = localPackageInfo.versionName;
        }
        if (fullVersionInfo)
        {
          str = str + " r" + j + "(build." + buildRev + ")";
          Log.d("MicroMsg.SDK.ChannelUtil", "full version: " + str);
        }
        return str;
        str = (0xF & paramInt >> 24) + "." + (0xFF & paramInt >> 16) + "." + i;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  public static void loadProfile(Context paramContext)
  {
    try
    {
      Map localMap = KVConfig.parseIni(Util.convertStreamToString(paramContext.getAssets().open("profile.ini")));
      String str1 = Util.nullAsNil((String)localMap.get("PROFILE_DEVICE_TYPE"));
      profileDeviceType = str1;
      if (str1.length() <= 0)
        profileDeviceType = Build.VERSION.SDK_INT;
      updateMode = Integer.parseInt((String)localMap.get("UPDATE_MODE"));
      buildRev = Integer.parseInt((String)localMap.get("BUILD_REVISION"));
      shouldShowGprsAlert = Boolean.parseBoolean((String)localMap.get("GPRS_ALERT"));
      android.util.Log.w("MicroMsg.SDK.ChannelUtil", "profileDeviceType=" + profileDeviceType);
      android.util.Log.w("MicroMsg.SDK.ChannelUtil", "updateMode=" + updateMode);
      android.util.Log.w("MicroMsg.SDK.ChannelUtil", "shouldShowGprsAlert=" + shouldShowGprsAlert);
      String str2 = (String)localMap.get("MARKET_URL");
      if ((str2 != null) && (str2.trim().length() != 0) && (Uri.parse(str2) != null))
        marketURL = str2;
      android.util.Log.w("MicroMsg.SDK.ChannelUtil", "marketURL=" + marketURL);
      return;
    }
    catch (Exception localException)
    {
      android.util.Log.e("MicroMsg.SDK.ChannelUtil", "setup profile from profile.ini failed");
      localException.printStackTrace();
    }
  }

  public static void setupChannelId(Context paramContext)
  {
    try
    {
      channelId = Integer.parseInt((String)KVConfig.parseIni(Util.convertStreamToString(paramContext.getAssets().open("channel.ini"))).get("CHANNEL"));
      return;
    }
    catch (Exception localException)
    {
      Log.e("MicroMsg.SDK.ChannelUtil", "setup channel id from channel.ini failed");
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.ChannelUtil
 * JD-Core Version:    0.6.0
 */