package com.umeng.common.b;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class i
{
  private static float a = 1.0F;

  public i(Context paramContext)
  {
    a(paramContext);
  }

  public static int a(float paramFloat)
  {
    return (int)(0.5F + paramFloat * a);
  }

  public static void a(Context paramContext)
  {
    a = paramContext.getResources().getDisplayMetrics().density;
  }

  public static int b(float paramFloat)
  {
    return (int)(0.5F + paramFloat / a);
  }

  public static int c(float paramFloat)
  {
    return (int)(0.5F + paramFloat * a);
  }

  public static int d(float paramFloat)
  {
    return (int)(0.5F + paramFloat / a);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.b.i
 * JD-Core Version:    0.6.0
 */