package com.umeng.common.net;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class l
{
  public static final String a = "pause";
  public static final String b = "continue";
  public static final String c = "cancel";
  public static final String d = "com.umeng.intent.DOWNLOAD";
  public static final String e = "com.umeng.broadcast.download.msg";

  public static int a(a.a parama)
  {
    int i = (int)System.currentTimeMillis();
    if (i < 0)
      i = -i;
    return i;
  }

  public static PendingIntent a(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent("com.umeng.intent.DOWNLOAD");
    localIntent.addFlags(1073741824);
    localIntent.putExtra("com.umeng.broadcast.download.msg", paramString);
    return PendingIntent.getBroadcast(paramContext, paramString.hashCode(), localIntent, 134217728);
  }

  public static String a(int paramInt, String paramString)
  {
    if (paramInt == 0)
      return null;
    StringBuilder localStringBuilder = new StringBuilder(paramInt + "");
    localStringBuilder.append(":");
    localStringBuilder.append(paramString);
    return localStringBuilder.toString();
  }

  public static PendingIntent b(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent(paramContext, DownloadingService.class);
    localIntent.putExtra("com.umeng.broadcast.download.msg", paramString);
    return PendingIntent.getService(paramContext, paramString.hashCode(), localIntent, 134217728);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.l
 * JD-Core Version:    0.6.0
 */