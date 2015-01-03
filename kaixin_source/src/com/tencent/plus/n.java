package com.tencent.plus;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class n
{
  public static int a(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.plus.n
 * JD-Core Version:    0.6.0
 */