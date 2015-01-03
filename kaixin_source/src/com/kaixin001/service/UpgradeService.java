package com.kaixin001.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.kaixin001.activity.UpgradeDialogActivity;
import com.kaixin001.engine.UpgradeEngine;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.UpgradeModel;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UpgradeDownloadFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpgradeService extends Service
{
  private static final int GET_UPGRADE_INFORMATION_COMPLETE = 202;
  public static final int INTERNAL_GET_UPGRADE_INFORMATION = 604800000;
  private static boolean TEST_UPDATE = false;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
      case 202:
      }
      do
      {
        return;
        if (!UpgradeService.TEST_UPDATE)
          continue;
        UpgradeDownloadFile.getInstance().setVersion("3.0");
        UpgradeDownloadFile.getInstance().mDescription = "哈哈哈，有新版本了！";
        UpgradeService.this.showNotification(UpgradeDownloadFile.getInstance().mDescription);
      }
      while (!UpgradeModel.getInstance().getDownloadFile());
      UpgradeService.this.showNotification(UpgradeDownloadFile.getInstance().mDescription);
      UpgradeModel.enableUpgradeService(false);
    }
  };
  private volatile boolean mStopFlag = false;
  private Thread mThread = null;

  private PendingIntent createContentIntent(int paramInt, String paramString)
  {
    Intent localIntent = new Intent(this, UpgradeDialogActivity.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("stringMessage", paramString);
    localBundle.putInt("dialogtype", paramInt);
    localIntent.putExtras(localBundle);
    localIntent.addFlags(536870912);
    return PendingIntent.getActivity(this, 0, localIntent, 134217728);
  }

  private void showNewVersionDialog(String paramString)
  {
    Intent localIntent = new Intent();
    localIntent.setFlags(268435456);
    localIntent.setClass(this, UpgradeDialogActivity.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("stringMessage", paramString);
    localBundle.putInt("dialogtype", 1);
    localIntent.putExtras(localBundle);
    localIntent.addFlags(536870912);
    startActivity(localIntent);
  }

  private void showNotification(String paramString)
  {
    showNewVersionDialog(paramString);
    if (TextUtils.isEmpty(paramString))
      paramString = getString(2131428014);
    String str1 = getResources().getString(2131427329);
    String str2 = getString(2131428015);
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    boolean bool1 = localSharedPreferences.getBoolean("notification_vibrate_preference", true);
    boolean bool2 = localSharedPreferences.getBoolean("notification_led_preference", true);
    boolean bool3 = localSharedPreferences.getBoolean("notification_ringtone_enabled_preference", true);
    NotificationManager localNotificationManager = (NotificationManager)getSystemService("notification");
    Notification localNotification = new Notification();
    RemoteViews localRemoteViews = new RemoteViews(getPackageName(), 2130903263);
    localRemoteViews.setTextViewText(2131363281, str1);
    localRemoteViews.setImageViewResource(2131363280, 17301642);
    localRemoteViews.setTextViewText(2131363284, paramString);
    localRemoteViews.setTextViewText(2131363285, formatTimestamp(System.currentTimeMillis()));
    localNotification.icon = 17301642;
    localNotification.contentView = localRemoteViews;
    localNotification.flags = (0x10 | localNotification.flags);
    localNotification.tickerText = str2;
    localNotification.contentIntent = createContentIntent(1, paramString);
    if (bool1)
      localNotification.defaults = (0x2 | localNotification.defaults);
    if (bool2)
      localNotification.defaults = (0x4 | localNotification.defaults);
    if (bool3)
      localNotification.sound = Uri.parse("android.resource://com.kaixin001.activity/2131099652");
    localNotificationManager.notify(KaixinConst.ID_CAN_UPGRADE_NOTIIFICATION, localNotification);
  }

  public String formatTimestamp(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    int i = localCalendar.get(10);
    if ("24".equals(Settings.System.getString(getApplicationContext().getContentResolver(), "time_12_24")))
    {
      if (i < 10);
      for (str = "H:mm"; ; str = "HH:mm")
      {
        Date localDate = new Date(paramLong);
        return new SimpleDateFormat(str).format(localDate);
      }
    }
    if (i < 10);
    for (String str = "a h:mm"; ; str = "a hh:mm")
      break;
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
  }

  public void onDestroy()
  {
    this.mStopFlag = true;
    try
    {
      this.mHandler.removeCallbacks(this.mThread);
      if (this.mThread != null)
      {
        boolean bool = this.mThread.isAlive();
        if (!bool);
      }
    }
    catch (Exception localException1)
    {
      try
      {
        this.mThread.stop();
        label42: this.mThread = null;
        UpgradeModel.enableUpgradeService(true);
        UpgradeModel.clearLoadedFlag();
        while (true)
        {
          super.onDestroy();
          return;
          localException1 = localException1;
          localException1.printStackTrace();
        }
      }
      catch (Exception localException2)
      {
        break label42;
      }
    }
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    super.onStart(paramIntent, paramInt);
    if (this.mThread == null)
    {
      this.mThread = new UpgradeThread(null);
      this.mThread.start();
    }
  }

  private class UpgradeThread extends Thread
  {
    private UpgradeThread()
    {
    }

    public void run()
    {
      if (UpgradeService.this.mStopFlag)
        return;
      if (UpgradeModel.isNeedCheckVersion(UpgradeService.this))
      {
        boolean bool = UpgradeEngine.getInstance().checkUpgradeInformation(UpgradeService.this.getApplicationContext());
        if (UpgradeService.TEST_UPDATE)
          bool = true;
        if ((bool) && (!UpgradeService.this.mStopFlag))
        {
          Message localMessage = Message.obtain();
          localMessage.what = 202;
          UpgradeService.this.mHandler.sendMessage(localMessage);
        }
      }
      while (true)
      {
        try
        {
          Thread.sleep(604800000L);
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
        break;
        KXLog.d("UpgradeThread", "UpgradeModel.mNoticeDone = True");
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.service.UpgradeService
 * JD-Core Version:    0.6.0
 */