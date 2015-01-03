package com.umeng.common.net;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.RemoteViews;
import com.umeng.common.Log;
import com.umeng.common.a.a;
import java.util.Map;

class e
  implements DownloadingService.a
{
  e(DownloadingService paramDownloadingService)
  {
  }

  public void a(int paramInt)
  {
    if (DownloadingService.c().containsKey(Integer.valueOf(paramInt)))
    {
      DownloadingService.d locald = (DownloadingService.d)DownloadingService.c().get(Integer.valueOf(paramInt));
      long[] arrayOfLong = locald.f;
      int i = 0;
      if (arrayOfLong != null)
      {
        boolean bool = arrayOfLong[1] < 0L;
        i = 0;
        if (bool)
        {
          i = (int)(100.0F * ((float)arrayOfLong[0] / (float)arrayOfLong[1]));
          if (i > 100)
            i = 99;
        }
      }
      Notification localNotification = DownloadingService.a(this.a, locald.e, paramInt, i);
      locald.b = localNotification;
      DownloadingService.b(this.a).notify(paramInt, localNotification);
    }
  }

  public void a(int paramInt1, int paramInt2)
  {
    if (DownloadingService.c().containsKey(Integer.valueOf(paramInt1)))
    {
      DownloadingService.d locald = (DownloadingService.d)DownloadingService.c().get(Integer.valueOf(paramInt1));
      a.a locala = locald.e;
      Notification localNotification = locald.b;
      localNotification.contentView.setProgressBar(a.c(DownloadingService.a(this.a)), 100, paramInt2, false);
      localNotification.contentView.setTextViewText(a.b(DownloadingService.a(this.a)), String.valueOf(paramInt2) + "%");
      DownloadingService.b(this.a).notify(paramInt1, localNotification);
      String str = DownloadingService.a();
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(paramInt1);
      arrayOfObject[1] = Integer.valueOf(paramInt2);
      arrayOfObject[2] = locala.b;
      Log.c(str, String.format("%3$10s Notification: mNotificationId = %1$15s\t|\tprogress = %2$15s", arrayOfObject));
    }
  }

  public void a(int paramInt, Exception paramException)
  {
    if (DownloadingService.c().containsKey(Integer.valueOf(paramInt)))
    {
      DownloadingService.d locald = (DownloadingService.d)DownloadingService.c().get(Integer.valueOf(paramInt));
      a.a locala = locald.e;
      Notification localNotification = locald.b;
      localNotification.contentView.setTextViewText(a.d(DownloadingService.a(this.a)), locala.b + " 下载失败，请检查网络。");
      DownloadingService.b(this.a).notify(paramInt, localNotification);
      DownloadingService.a(this.a, paramInt);
    }
  }

  public void a(int paramInt, String paramString)
  {
    a.a locala;
    Message localMessage2;
    if (DownloadingService.c().containsKey(Integer.valueOf(paramInt)))
    {
      DownloadingService.d locald = (DownloadingService.d)DownloadingService.c().get(Integer.valueOf(paramInt));
      if (locald != null)
      {
        locala = locald.e;
        locald.b.contentView.setTextViewText(a.b(DownloadingService.a(this.a)), String.valueOf(100) + "%");
        c.a(DownloadingService.a(this.a)).a(locala.a, locala.c, 100);
        Bundle localBundle = new Bundle();
        localBundle.putString("filename", paramString);
        Message localMessage1 = Message.obtain();
        localMessage1.what = 5;
        localMessage1.arg1 = 1;
        localMessage1.obj = locala;
        localMessage1.arg2 = paramInt;
        localMessage1.setData(localBundle);
        DownloadingService.c(this.a).sendMessage(localMessage1);
        localMessage2 = Message.obtain();
        localMessage2.what = 5;
        localMessage2.arg1 = 1;
        localMessage2.setData(localBundle);
      }
    }
    try
    {
      if (DownloadingService.b().get(locala) != null)
        ((Messenger)DownloadingService.b().get(locala)).send(localMessage2);
      DownloadingService.a(this.a, paramInt);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      DownloadingService.a(this.a, paramInt);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.e
 * JD-Core Version:    0.6.0
 */