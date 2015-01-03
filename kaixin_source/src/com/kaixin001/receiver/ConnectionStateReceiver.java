package com.kaixin001.receiver;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.activity.StubActivity;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.service.RefreshNewMessageService;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.KXLog;
import java.util.List;

public class ConnectionStateReceiver extends BroadcastReceiver
{
  public static boolean isServiceRunning(Context paramContext, String paramString)
  {
    int i;
    try
    {
      List localList = ((ActivityManager)paramContext.getSystemService("activity")).getRunningServices(30);
      if (localList.size() <= 0)
      {
        return false;
        while (true)
        {
          if (j >= localList.size())
          {
            i = 0;
            break;
          }
          boolean bool = ((ActivityManager.RunningServiceInfo)localList.get(j)).service.getClassName().equals(paramString);
          if (bool)
          {
            i = 1;
            break;
          }
          j++;
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        i = 0;
        break;
        int j = 0;
      }
    }
    return i;
  }

  private void sendLoggedInNotification(Context paramContext, boolean paramBoolean)
  {
    if (!Setting.getInstance().showLoginNotification())
      return;
    NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
    Notification localNotification = new Notification();
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, 0, new Intent(paramContext, StubActivity.class), 0);
    String str1 = paramContext.getResources().getString(2131427329);
    String str2 = NewsModel.getMyHomeModel().getRealname();
    if (paramBoolean)
    {
      localNotification.icon = 2130838348;
      str3 = paramContext.getResources().getString(2131427660);
      if ((str2 == null) || (str2.length() <= 0));
    }
    for (String str3 = str3 + "(" + str2 + ")"; ; str3 = str3 + "(" + str2 + ")")
      do
      {
        localNotification.tickerText = str1;
        localNotification.setLatestEventInfo(paramContext, str1, str3, localPendingIntent);
        localNotification.flags = 32;
        localNotificationManager.notify(KaixinConst.ID_ONLINE_NOTIFICATION, localNotification);
        return;
        localNotification.icon = 2130838703;
        str3 = paramContext.getResources().getString(2131427709);
      }
      while ((str2 == null) || (str2.length() <= 0));
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    boolean bool1 = paramIntent.getBooleanExtra("noConnectivity", false);
    boolean bool2;
    boolean bool3;
    boolean bool4;
    if (bool1)
    {
      bool2 = false;
      sendLoggedInNotification(paramContext, bool2);
      bool3 = isServiceRunning(paramContext, "com.kaixin001.service.RefreshNewMessageService");
      if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
        User.getInstance().loadUserData(paramContext);
      if (!TextUtils.isEmpty(User.getInstance().getOauthToken()))
        break label153;
      bool4 = false;
      label65: KXLog.d("HELLO", "logged:" + bool4 + ", serviceRunning:" + bool3 + ", noConnectivity:" + bool1);
      if (bool4)
      {
        if ((bool1) || (bool3))
          break label159;
        paramContext.startService(new Intent(paramContext, RefreshNewMessageService.class));
      }
    }
    while (true)
    {
      CloudAlbumManager.getInstance().wifiStateChanged(paramContext);
      KXEnvironment.wifiStateChanged(paramContext);
      return;
      bool2 = true;
      break;
      label153: bool4 = true;
      break label65;
      label159: if ((!bool1) || (!bool3))
        continue;
      paramContext.stopService(new Intent(paramContext, RefreshNewMessageService.class));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.receiver.ConnectionStateReceiver
 * JD-Core Version:    0.6.0
 */