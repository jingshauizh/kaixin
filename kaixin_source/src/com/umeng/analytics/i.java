package com.umeng.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import java.util.Vector;

public class i
{
  private static final int b = 4;
  private Vector<Long> a;
  private String c;

  public i(String paramString)
  {
    this.a = new Vector(4);
    this.c = paramString;
  }

  public i(String paramString, int paramInt)
  {
    this.c = paramString;
    if (paramInt < 0)
    {
      this.a = new Vector(4);
      return;
    }
    this.a = new Vector(paramInt);
  }

  public static i a(Context paramContext, String paramString)
  {
    return a(paramString, h.e(paramContext).getString(paramString, null));
  }

  public static i a(String paramString1, String paramString2)
  {
    i locali = new i(paramString1);
    if (TextUtils.isEmpty(paramString2))
      return locali;
    String[] arrayOfString = paramString2.split(",");
    int i = arrayOfString.length;
    int j = 0;
    label32: String str;
    if (j < i)
    {
      str = arrayOfString[j].trim();
      if (!TextUtils.isEmpty(str))
        break label62;
    }
    while (true)
    {
      j++;
      break label32;
      break;
      label62: Long.valueOf(-1L);
      try
      {
        Long localLong = Long.valueOf(Long.parseLong(str));
        locali.a(localLong);
      }
      catch (Exception localException)
      {
      }
    }
  }

  public Long a()
  {
    int i = this.a.size();
    if (i <= 0)
      return Long.valueOf(-1L);
    return (Long)this.a.remove(i - 1);
  }

  public void a(Context paramContext)
  {
    String str = toString();
    SharedPreferences.Editor localEditor = h.e(paramContext).edit();
    if (TextUtils.isEmpty(str))
    {
      localEditor.remove(this.c).commit();
      return;
    }
    localEditor.putString(this.c, str).commit();
  }

  public void a(Long paramLong)
  {
    while (this.a.size() >= 4)
      this.a.remove(0);
    this.a.add(paramLong);
  }

  public int b()
  {
    return this.a.size();
  }

  public String toString()
  {
    int i = this.a.size();
    if (i <= 0)
      return null;
    StringBuffer localStringBuffer = new StringBuffer(4);
    for (int j = 0; j < i; j++)
    {
      localStringBuffer.append(this.a.get(j));
      if (j == i - 1)
        continue;
      localStringBuffer.append(",");
    }
    this.a.clear();
    return localStringBuffer.toString();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.i
 * JD-Core Version:    0.6.0
 */