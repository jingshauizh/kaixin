package com.kaixin001.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.kaixin001.activity.UpgradeDialogActivity;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.UpgradeModel;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UpgradeDownloadFile;

public class UpgradeDownloadService extends Service
{
  public static final int MSG_CANCEL_DOWNLAODING = 9001;
  public static final int MSG_DOWNLAODING_COMPLETE = 9003;
  public static final int MSG_DOWNLAODING_ERROR = 9004;
  public static final int MSG_DOWNLAODING_PROCESS = 9002;
  private static final String TAG = "DownloadService";
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        return;
      case 9001:
        UpgradeDownloadService.this.stopSelf();
        return;
      case 9002:
        UpgradeDownloadService.this.updateNotification(paramMessage.what, paramMessage.arg1, paramMessage.obj.toString());
        return;
      case 9003:
        UpgradeDownloadService.this.updateNotification(paramMessage.what, paramMessage.arg1, paramMessage.obj.toString());
        Toast.makeText(UpgradeDownloadService.this, paramMessage.obj.toString(), 0).show();
        return;
      case 9004:
      }
      UpgradeDownloadService.this.updateNotification(paramMessage.what, paramMessage.arg1, paramMessage.obj.toString());
      Toast.makeText(UpgradeDownloadService.this, paramMessage.obj.toString(), 1).show();
    }
  };
  DownloadThread mThread = null;
  private boolean mbRunning = false;
  private boolean mbStop = false;
  Notification notification;
  NotificationManager notificationManager;

  private void cancelNotification()
  {
    KXLog.d("DownloadService", "cancelNotification");
    try
    {
      this.notificationManager.cancel(KaixinConst.ID_DOWNLOADING_NOTIFICATION);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("DownloadService", "cancelNotification", localException);
    }
  }

  private PendingIntent createContentIntent(int paramInt)
  {
    Intent localIntent = new Intent(this, UpgradeDialogActivity.class);
    Bundle localBundle = new Bundle();
    localBundle.putInt("dialogtype", paramInt);
    localIntent.putExtras(localBundle);
    localIntent.addFlags(536870912);
    return PendingIntent.getActivity(this, 0, localIntent, 134217728);
  }

  private void showNotification()
  {
    this.notification = new Notification(17301633, getString(2131428016), System.currentTimeMillis());
    Notification localNotification = this.notification;
    localNotification.flags = (0x2 | localNotification.flags);
    RemoteViews localRemoteViews = new RemoteViews(getPackageName(), 2130903262);
    localRemoteViews.setImageViewResource(2131363280, 17301633);
    localRemoteViews.setTextViewText(2131363281, "Download:Kaixin001 for android");
    localRemoteViews.setTextViewText(2131363282, "0%");
    localRemoteViews.setProgressBar(2131363283, 100, 0, true);
    this.notification.contentView = localRemoteViews;
    this.notification.contentIntent = createContentIntent(2);
    this.notificationManager.notify(KaixinConst.ID_DOWNLOADING_NOTIFICATION, this.notification);
  }

  private void updateNotification(int paramInt1, int paramInt2, String paramString)
  {
    if (!this.mbRunning)
      return;
    this.notification.contentView.setTextViewText(2131363281, paramString);
    if (paramInt1 == 9003)
    {
      this.notification.tickerText = getString(2131428017);
      this.notification.icon = 17301634;
      this.notification.contentView.setTextViewText(2131363282, paramInt2 + "%");
      this.notification.contentView.setProgressBar(2131363283, 100, 100, false);
      this.notification.contentIntent = createContentIntent(3);
    }
    while (true)
    {
      this.notificationManager.notify(KaixinConst.ID_DOWNLOADING_NOTIFICATION, this.notification);
      return;
      if (paramInt1 == 9004)
      {
        if (TextUtils.isEmpty(paramString))
          this.notification.contentView.setTextViewText(2131363281, getString(2131428018));
        this.notification.tickerText = getString(2131428018);
        this.notification.contentIntent = createContentIntent(4);
        continue;
      }
      if (paramInt2 > 0)
      {
        KXLog.d("DownloadService", "downloading " + paramInt2);
        this.notification.tickerText = getString(2131428016);
        this.notification.contentView.setViewVisibility(2131363282, 0);
        this.notification.contentView.setTextViewText(2131363282, paramInt2 + "%");
        this.notification.contentView.setProgressBar(2131363283, 100, paramInt2, false);
        continue;
      }
      KXLog.d("DownloadService", "downloading " + paramInt2);
      this.notification.contentView.setViewVisibility(2131363282, 8);
    }
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
    KXLog.d("DownloadService", "onCreate");
    UpgradeDownloadFile.getInstance().setContext(this);
    this.mbStop = false;
    this.notificationManager = ((NotificationManager)getSystemService("notification"));
  }

  public void onDestroy()
  {
    super.onDestroy();
    KXLog.d("DownloadService", "onDestroy");
    this.mbStop = true;
    UpgradeDownloadFile.getInstance().cancel();
    if ((this.mThread != null) && (this.mThread.isAlive()));
    try
    {
      this.mThread.stop();
      label46: this.mThread = null;
      this.mbRunning = false;
      cancelNotification();
      UpgradeModel.enableUpgradeService(true);
      return;
    }
    catch (Exception localException)
    {
      break label46;
    }
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    super.onStart(paramIntent, paramInt);
    KXLog.d("DownloadService", "onStart");
    if (this.mbRunning)
      return;
    UpgradeDownloadFile.getInstance().setHandler(this.mHandler);
    this.mbStop = false;
    if (this.mThread == null)
      this.mThread = new DownloadThread();
    showNotification();
    this.mThread.start();
    UpgradeModel.enableUpgradeService(false);
  }

  public class DownloadThread extends Thread
  {
    public DownloadThread()
    {
    }

    public void run()
    {
      super.run();
      UpgradeDownloadService.this.mbRunning = true;
      if (!UpgradeDownloadService.this.mbStop);
      try
      {
        Process.setThreadPriority(10);
        UpgradeDownloadFile.getInstance().start();
        sleep(200L);
        UpgradeDownloadService.this.mbRunning = false;
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.service.UpgradeDownloadService
 * JD-Core Version:    0.6.0
 */