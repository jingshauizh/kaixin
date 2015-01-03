package com.kaixin001.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.KXLog;

public class WifiStateListener extends BroadcastReceiver
{
  public static final String INITIAL_USER_ACTION = "com.kaixin001.INITIAL_USER";
  private static final String LOG = "CloudAlbumManager";
  public static final String LOGOUT_ACTION = "com.kaixin001.LOGOUT";
  public static final String SYNC_START = "com.kaixin001.SYNC_START";
  public static final String SYNC_STOP = "com.kaixin001.SYNC_STOP";

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getAction();
    KXLog.d("CloudAlbumManager", "action:" + str);
    if ("android.net.wifi.WIFI_STATE_CHANGED".equals(str))
      switch (paramIntent.getIntExtra("wifi_state", 0))
      {
      default:
      case 3:
      case 2:
      case 1:
      case 0:
      }
    do
    {
      return;
      KXLog.d("CloudAlbumManager", "WIFI_STATE_ENABLED");
      CloudAlbumManager.getInstance().wifiStateChanged(paramContext);
      KXEnvironment.wifiStateChanged(paramContext);
      return;
      KXLog.d("CloudAlbumManager", "WIFI_STATE_ENABLING");
      return;
      KXLog.d("CloudAlbumManager", "WIFI_STATE_DISABLED");
      CloudAlbumManager.getInstance().wifiStateChanged(paramContext);
      KXEnvironment.wifiStateChanged(paramContext);
      return;
      KXLog.d("CloudAlbumManager", "WIFI_STATE_DISABLING");
      return;
      if ("com.kaixin001.INITIAL_USER".equals(str))
      {
        KXLog.d("CloudAlbumManager", "INITIAL_USER_ACTION");
        CloudAlbumManager.getInstance().startUploadDeamon(paramContext);
        return;
      }
      if ("com.kaixin001.LOGOUT".equals(str))
      {
        KXLog.d("CloudAlbumManager", "LOGOUT_ACTION");
        CloudAlbumManager.getInstance().stopUploadDeamon();
        return;
      }
      if (!"com.kaixin001.SYNC_START".equals(str))
        continue;
      KXLog.d("CloudAlbumManager", "SYNC_START");
      CloudAlbumManager.getInstance().startUploadDeamon(paramContext);
      return;
    }
    while (!"com.kaixin001.SYNC_STOP".equals(str));
    KXLog.d("CloudAlbumManager", "SYNC_STOP");
    CloudAlbumManager.getInstance().stopUploadDeamon();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.receiver.WifiStateListener
 * JD-Core Version:    0.6.0
 */