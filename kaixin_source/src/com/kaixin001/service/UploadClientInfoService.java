package com.kaixin001.service;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.IBinder;
import android.text.TextUtils;
import com.kaixin001.engine.UploadClientInfoEngine;
import com.kaixin001.model.User;

public class UploadClientInfoService extends Service
{
  private static final int SLEEP_INTERVEL = 100000;
  private static final String TAG = "UploadClientInfoService";

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

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    super.onStart(paramIntent, paramInt);
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    new UploadClientInfoThread().start();
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }

  class UploadClientInfoThread extends Thread
  {
    UploadClientInfoThread()
    {
    }

    public void run()
    {
      super.run();
      try
      {
        if (UploadClientInfoService.this.isHasNet())
        {
          if ((TextUtils.isEmpty(User.getInstance().getOauthToken())) || (TextUtils.isEmpty(User.getInstance().getUID())))
          {
            UploadClientInfoService.this.stopSelf();
            return;
          }
          UploadClientInfoEngine.getInstance().UploadClientInfo(UploadClientInfoService.this);
          UploadClientInfoService.this.stopSelf();
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        UploadClientInfoService.this.stopSelf();
        return;
      }
      sleep(100000L);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.service.UploadClientInfoService
 * JD-Core Version:    0.6.0
 */