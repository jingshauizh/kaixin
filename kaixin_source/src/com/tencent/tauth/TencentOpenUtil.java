package com.tencent.tauth;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TencentOpenUtil
{
  public static final String PNAME_MQZONE = "com.qzone";
  public static final String SDK_VERSION = "0.0.2";

  public static boolean isAvilible(Context paramContext, String paramString)
  {
    List localList = paramContext.getPackageManager().getInstalledPackages(0);
    ArrayList localArrayList = new ArrayList();
    if (localList != null);
    for (int i = 0; ; i++)
    {
      if (i >= localList.size())
        return localArrayList.contains(paramString);
      localArrayList.add(((PackageInfo)localList.get(i)).packageName);
    }
  }

  public static boolean isInstallOnSDCard(Context paramContext, String paramString)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      int i = localPackageManager.getApplicationInfo(paramString, 0).flags;
      int j = i & 0x40000;
      int k = 0;
      if (j != 0)
        k = 1;
      return k;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.TencentOpenUtil
 * JD-Core Version:    0.6.0
 */