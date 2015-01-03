package com.tencent.record.a;

import android.content.Context;
import java.io.File;

public final class c
{
  private static Context a;

  public static final Context a()
  {
    if (a == null)
      return null;
    return a;
  }

  public static final void a(Context paramContext)
  {
    a = paramContext;
  }

  public static final String b()
  {
    return a().getPackageName();
  }

  public static final File c()
  {
    return a().getFilesDir();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.record.a.c
 * JD-Core Version:    0.6.0
 */