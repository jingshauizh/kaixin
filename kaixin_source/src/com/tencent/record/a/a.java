package com.tencent.record.a;

import android.os.Environment;

public class a
{
  public static boolean a()
  {
    String str = Environment.getExternalStorageState();
    return ("mounted".equals(str)) || ("mounted_ro".equals(str));
  }

  public static e b()
  {
    if (!a())
      return null;
    return e.b(Environment.getExternalStorageDirectory());
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.record.a.a
 * JD-Core Version:    0.6.0
 */