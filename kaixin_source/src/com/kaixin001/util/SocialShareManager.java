package com.kaixin001.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.tencent.tauth.TAuthView;
import com.tencent.tauth.TencentOpenAPI;
import com.tencent.tauth.TencentOpenAPI2;
import com.tencent.tauth.bean.OpenId;
import com.tencent.tauth.http.Callback;
import com.weibo.sdk.android.Weibo;

public class SocialShareManager
{
  public static final String APP_ID_QQ = "100228505";
  public static final String APP_KEY_WEIBO = "3370195860";
  public static final String CALLBACK_QQ = "tencentauth://auth.qq.com";
  public static final String OPEN_ID = "openid";
  private static final String PREFE_SOCIAL_SHARE = "social_share";
  public static final int RECEIVE_QZONE = 547;
  public static final int RECEIVE_WEIBO = 548;
  public static final String REDIRECT_URL = "http://www.kaixin001.com";
  public static final String SCOPE_QQ = "get_user_info, get_user_profile, add_share, add_topic, list_album, upload_pic, add_album";
  private static final String SP_KEY_OPENID_QQ = "qq_openid";
  private static final String SP_KEY_TOKEN_QQ = "qq_token";
  private static final String SP_KEY_TOKEN_WEIBO = "weibo_token";
  private Context mContext;
  private OnRecieveListener receiveListener;
  private BroadcastReceiver receiver;
  private BroadcastReceiver receiver2;

  public SocialShareManager(Context paramContext)
  {
    this.mContext = paramContext;
  }

  private void registerIntentQQReceivers()
  {
    this.receiver = new QQAuthReceiver(null);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.tencent.auth.BROWSER");
    this.mContext.registerReceiver(this.receiver, localIntentFilter);
  }

  private void registerIntentWeiboReceiver()
  {
    this.receiver2 = new WeiboAuthReceiver(null);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.weibo.android.sdk.auth");
    this.mContext.registerReceiver(this.receiver2, localIntentFilter);
  }

  private void sinaAuth()
  {
    Weibo.getInstance("3370195860", "http://www.kaixin001.com").authorize(this.mContext);
  }

  private void tencentAuth(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(this.mContext, TAuthView.class);
    localIntent.putExtra("client_id", paramString1);
    localIntent.putExtra("scope", "get_user_info, get_user_profile, add_share, add_topic, list_album, upload_pic, add_album");
    localIntent.putExtra("target", paramString2);
    localIntent.putExtra("callback", "tencentauth://auth.qq.com");
    this.mContext.startActivity(localIntent);
  }

  public void authQzone()
  {
    registerIntentQQReceivers();
    tencentAuth("100228505", "_self");
  }

  public void authWeibo()
  {
    registerIntentWeiboReceiver();
    sinaAuth();
  }

  public void destroy()
  {
    this.mContext = null;
  }

  public String getQQOpenId()
  {
    return this.mContext.getSharedPreferences("social_share", 0).getString("qq_openid", "");
  }

  public String getQQToken()
  {
    return this.mContext.getSharedPreferences("social_share", 0).getString("qq_token", "");
  }

  public String getWeiboToken()
  {
    return this.mContext.getSharedPreferences("social_share", 0).getString("weibo_token", "");
  }

  public Bundle getparams(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("title", paramString1);
    localBundle.putString("url", paramString2);
    localBundle.putString("targetUrl", paramString2);
    localBundle.putString("comment", paramString3);
    localBundle.putString("summary", paramString4);
    localBundle.putString("images", paramString5);
    localBundle.putString("imageUrl", paramString5);
    localBundle.putString("type", "4");
    localBundle.putString("playurl", "");
    localBundle.putString("site", "开心网");
    localBundle.putString("appName", "开心网");
    return localBundle;
  }

  public void saveQQToken(String paramString1, String paramString2)
  {
    SharedPreferences localSharedPreferences = this.mContext.getSharedPreferences("social_share", 0);
    localSharedPreferences.edit().putString("qq_openid", paramString1).commit();
    localSharedPreferences.edit().putString("qq_token", paramString2).commit();
  }

  public void saveWeiboToken(String paramString)
  {
    this.mContext.getSharedPreferences("social_share", 0).edit().putString("weibo_token", paramString).commit();
  }

  public void setOnReceiverListener(OnRecieveListener paramOnRecieveListener)
  {
    this.receiveListener = paramOnRecieveListener;
  }

  public void shareRepostToQzone(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    TencentOpenAPI2.sendStore(paramContext, paramString1, paramString2, "100228505", "_self", paramBundle, new Callback()
    {
      public void onCancel(int paramInt)
      {
      }

      public void onFail(int paramInt, String paramString)
      {
      }

      public void onSuccess(Object paramObject)
      {
      }
    }
    , null);
  }

  public static abstract interface OnRecieveListener
  {
    public abstract void onReceived(SocialShareManager.ReceiveType paramReceiveType);
  }

  private class QQAuthReceiver extends BroadcastReceiver
  {
    private QQAuthReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      Bundle localBundle = paramIntent.getExtras();
      String str = localBundle.getString("access_token");
      localBundle.getString("expires_in");
      if (str != null)
        TencentOpenAPI.openid(str, new Callback(str)
        {
          public void onCancel(int paramInt)
          {
          }

          public void onFail(int paramInt, String paramString)
          {
          }

          public void onSuccess(Object paramObject)
          {
            String str = ((OpenId)paramObject).getOpenId();
            SocialShareManager.this.saveQQToken(str, this.val$access_token);
            if (SocialShareManager.this.receiveListener != null)
              ((Activity)SocialShareManager.this.mContext).runOnUiThread(new Runnable()
              {
                public void run()
                {
                  SocialShareManager.this.receiveListener.onReceived(SocialShareManager.ReceiveType.RECEIVE_QZONE);
                }
              });
          }
        });
    }
  }

  public static enum ReceiveType
  {
    static
    {
      ReceiveType[] arrayOfReceiveType = new ReceiveType[2];
      arrayOfReceiveType[0] = RECEIVE_QZONE;
      arrayOfReceiveType[1] = RECEIVE_WEIBO;
      ENUM$VALUES = arrayOfReceiveType;
    }
  }

  private class WeiboAuthReceiver extends BroadcastReceiver
  {
    private WeiboAuthReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str = paramIntent.getExtras().getString("access_token");
      SocialShareManager.this.saveWeiboToken(str);
      if (SocialShareManager.this.receiveListener != null)
        SocialShareManager.this.receiveListener.onReceived(SocialShareManager.ReceiveType.RECEIVE_WEIBO);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.SocialShareManager
 * JD-Core Version:    0.6.0
 */