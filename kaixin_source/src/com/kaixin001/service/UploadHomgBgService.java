package com.kaixin001.service;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.IBinder;
import com.kaixin001.engine.UploadPhotoEngine;

public class UploadHomgBgService extends Service
{
  public static final String KEY_FILEPATH = "FILEPATH";
  private static final int SLEEP_INTERVEL = 100000;
  private String filePathName;

  private boolean isHasNet()
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)getSystemService("connectivity");
    NetworkInfo.State localState1 = localConnectivityManager.getNetworkInfo(0).getState();
    NetworkInfo.State localState2 = localConnectivityManager.getNetworkInfo(1).getState();
    int i;
    if ((localState2 != NetworkInfo.State.CONNECTING) && (localState2 != NetworkInfo.State.CONNECTED) && (localState1 != NetworkInfo.State.CONNECTING))
    {
      NetworkInfo.State localState3 = NetworkInfo.State.CONNECTED;
      i = 0;
      if (localState1 != localState3);
    }
    else
    {
      i = 1;
    }
    return i;
  }

  private void uploadPic()
  {
    UploadPhotoEngine.getInstance().uploadHomePhoto(this, "background", this.filePathName, "手机背景图", "", "homebg", "1", "", "", "");
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    Bundle localBundle = paramIntent.getExtras();
    if (localBundle != null)
    {
      this.filePathName = localBundle.getString("FILEPATH");
      if (this.filePathName != null)
        new UploadHomeBgThread().start();
    }
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }

  class UploadHomeBgThread extends Thread
  {
    UploadHomeBgThread()
    {
    }

    public void run()
    {
      super.run();
      try
      {
        if (UploadHomgBgService.this.isHasNet())
        {
          UploadHomgBgService.this.uploadPic();
          com.kaixin001.fragment.KaixinMenuListFragment.isRefreshBackground = true;
          UploadHomgBgService.this.stopSelf();
          return;
        }
        sleep(100000L);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.service.UploadHomgBgService
 * JD-Core Version:    0.6.0
 */