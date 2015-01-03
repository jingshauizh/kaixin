package com.kaixin001.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ScreenUtil
{
  public static int getOrientation(Context paramContext)
  {
    if (paramContext == null)
      return 0;
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    if (localDisplayMetrics.widthPixels < localDisplayMetrics.heightPixels)
      return 1;
    if (localDisplayMetrics.widthPixels == localDisplayMetrics.heightPixels)
      return 3;
    return 2;
  }

  public static int getScreenHeight(Context paramContext)
  {
    if (paramContext == null)
      return -1;
    return paramContext.getResources().getDisplayMetrics().heightPixels;
  }

  public static int getScreenWidth(Context paramContext)
  {
    if (paramContext == null)
      return -1;
    return paramContext.getResources().getDisplayMetrics().widthPixels;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.ScreenUtil
 * JD-Core Version:    0.6.0
 */