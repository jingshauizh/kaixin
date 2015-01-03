package com.tencent.mm.sdk.platformtools;

import android.content.Context;

public final class MMApplicationContext
{
  private static String am;
  private static Context q = null;

  static
  {
    am = "com.tencent.mm";
  }

  public static Context getContext()
  {
    return q;
  }

  public static String getDefaultPreferencePath()
  {
    return am + "_preferences";
  }

  public static String getPackageName()
  {
    return am;
  }

  public static void setContext(Context paramContext)
  {
    q = paramContext;
    am = paramContext.getPackageName();
    Log.d("MicroMsg.MMApplicationContext", "setup application context for package: " + am);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.MMApplicationContext
 * JD-Core Version:    0.6.0
 */