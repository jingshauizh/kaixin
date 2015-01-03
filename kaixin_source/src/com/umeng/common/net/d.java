package com.umeng.common.net;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.umeng.common.Log;
import java.io.File;

class d extends Handler
{
  d(DownloadingService paramDownloadingService)
  {
  }

  public void handleMessage(Message paramMessage)
  {
    a.a locala = (a.a)paramMessage.obj;
    int i = paramMessage.arg2;
    try
    {
      String str1 = paramMessage.getData().getString("filename");
      Log.c(DownloadingService.a(), "Cancel old notification....");
      Notification localNotification = new Notification(17301634, "下载完成，请点击安装", System.currentTimeMillis());
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.addFlags(268435456);
      localIntent.setDataAndType(Uri.fromFile(new File(str1)), "application/vnd.android.package-archive");
      PendingIntent localPendingIntent = PendingIntent.getActivity(DownloadingService.a(this.a), 0, localIntent, 134217728);
      localNotification.setLatestEventInfo(DownloadingService.a(this.a), locala.b, "下载完成，请点击安装", localPendingIntent);
      localNotification.flags = 16;
      DownloadingService.a(this.a, (NotificationManager)this.a.getSystemService("notification"));
      DownloadingService.b(this.a).notify(i + 1, localNotification);
      Log.c(DownloadingService.a(), "Show new  notification....");
      boolean bool = DownloadingService.a(DownloadingService.a(this.a));
      String str2 = DownloadingService.a();
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Boolean.valueOf(bool);
      Log.c(str2, String.format("isAppOnForeground = %1$B", arrayOfObject1));
      if (bool)
      {
        DownloadingService.b(this.a).cancel(i + 1);
        DownloadingService.a(this.a).startActivity(localIntent);
      }
      String str3 = DownloadingService.a();
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = locala.b;
      arrayOfObject2[1] = str1;
      Log.a(str3, String.format("%1$10s downloaded. Saved to: %2$s", arrayOfObject2));
      return;
    }
    catch (Exception localException)
    {
      Log.b(DownloadingService.a(), "can not install. " + localException.getMessage());
      DownloadingService.b(this.a).cancel(i + 1);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.d
 * JD-Core Version:    0.6.0
 */