package com.kaixin001.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import com.kaixin001.engine.SocialShareEngine;
import com.kaixin001.util.SocialShareManager;

public class SyncShareService extends Service
{
  public static final String KEY_COMMENT = "comment";
  public static final String KEY_IS_SHARE_QZONE = "isShareToQzone";
  public static final String KEY_IS_SHARE_WEIBO = "isShareToWeibo";
  public static final String KEY_SUMMARY = "summary";
  public static final String KEY_TITLE = "title";
  private static final String PARAM_COMMENT = "";
  private static final String PARAM_TITLE = "我在开心有好运";
  private static final int SLEEP_INTERVEL = 100000;
  private boolean isShareQzone = false;
  private boolean isShareWeibo = false;
  private Context mContext;
  private String qzoneOpenId;
  private String qzoneToken;
  private SocialShareManager shareManager;
  private String summary;
  private String weiboToken;

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

  public void onDestroy()
  {
    super.onDestroy();
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    this.mContext = getApplicationContext();
    Bundle localBundle = paramIntent.getExtras();
    if (localBundle != null)
    {
      this.summary = localBundle.getString("summary");
      this.isShareWeibo = localBundle.getBoolean("isShareToWeibo", false);
      this.isShareQzone = localBundle.getBoolean("isShareToQzone", false);
    }
    this.shareManager = new SocialShareManager(this.mContext);
    this.qzoneOpenId = this.shareManager.getQQOpenId();
    this.qzoneToken = this.shareManager.getQQToken();
    this.weiboToken = this.shareManager.getWeiboToken();
    new SyncShareThread().start();
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }

  class SyncShareThread extends Thread
  {
    SyncShareThread()
    {
    }

    public void run()
    {
      super.run();
      try
      {
        if (SyncShareService.this.isHasNet())
        {
          if ((SyncShareService.this.isShareQzone) && (!TextUtils.isEmpty(SyncShareService.this.qzoneOpenId)))
            SocialShareEngine.getInstance().shareToQzone(SyncShareService.this.mContext, SyncShareService.this.qzoneOpenId, SyncShareService.this.qzoneToken, "我在开心有好运", SyncShareService.this.summary, "");
          if ((SyncShareService.this.isShareWeibo) && (!TextUtils.isEmpty(SyncShareService.this.weiboToken)))
            SocialShareEngine.getInstance().shareToWeibo(SyncShareService.this.mContext, SyncShareService.this.weiboToken, SyncShareService.this.summary);
          SyncShareService.this.stopSelf();
          return;
        }
        sleep(100000L);
        return;
      }
      catch (Exception localException)
      {
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.service.SyncShareService
 * JD-Core Version:    0.6.0
 */