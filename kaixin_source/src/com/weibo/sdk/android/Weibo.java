package com.weibo.sdk.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieSyncManager;
import com.weibo.sdk.android.util.Utility;

public class Weibo
{
  public static final String KEY_EXPIRES = "expires_in";
  public static final String KEY_REFRESHTOKEN = "refresh_token";
  public static final String KEY_TOKEN = "access_token";
  public static String URL_OAUTH2_ACCESS_AUTHORIZE = "https://open.weibo.cn/oauth2/authorize";
  public static String app_key;
  public static boolean isWifi;
  private static Weibo mWeiboInstance = null;
  public static String redirecturl;
  public Oauth2AccessToken accessToken = null;

  static
  {
    app_key = "";
    redirecturl = "";
    isWifi = false;
  }

  public static Weibo getInstance(String paramString1, String paramString2)
  {
    monitorenter;
    try
    {
      if (mWeiboInstance == null)
        mWeiboInstance = new Weibo();
      app_key = paramString1;
      redirecturl = paramString2;
      Weibo localWeibo = mWeiboInstance;
      return localWeibo;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void authorize(Context paramContext)
  {
    isWifi = Utility.isWifi(paramContext);
    startAuthDialog(paramContext);
  }

  public void setupConsumerConfig(String paramString1, String paramString2)
  {
    app_key = paramString1;
    redirecturl = paramString2;
  }

  public void startAuthDialog(Context paramContext)
  {
    startDialog(paramContext, new WeiboParameters(), new WeiboAuthListener()
    {
      public void onCancel()
      {
        Log.d("Weibo-authorize", "Login canceled");
      }

      public void onComplete(Bundle paramBundle)
      {
        CookieSyncManager.getInstance().sync();
        if (Weibo.this.accessToken == null)
          Weibo.this.accessToken = new Oauth2AccessToken();
        Weibo.this.accessToken.setToken(paramBundle.getString("access_token"));
        Weibo.this.accessToken.setExpiresIn(paramBundle.getString("expires_in"));
        Weibo.this.accessToken.setRefreshToken(paramBundle.getString("refresh_token"));
        if (Weibo.this.accessToken.isSessionValid())
        {
          Log.d("Weibo-authorize", "Login Success! access_token=" + Weibo.this.accessToken.getToken() + " expires=" + Weibo.this.accessToken.getExpiresTime() + " refresh_token=" + Weibo.this.accessToken.getRefreshToken());
          return;
        }
        Log.d("Weibo-authorize", "Failed to receive access token");
      }

      public void onError(WeiboDialogError paramWeiboDialogError)
      {
        Log.d("Weibo-authorize", "Login failed: " + paramWeiboDialogError);
      }

      public void onWeiboException(WeiboException paramWeiboException)
      {
        Log.d("Weibo-authorize", "Login failed: " + paramWeiboException);
      }
    });
  }

  public void startDialog(Context paramContext, WeiboParameters paramWeiboParameters, WeiboAuthListener paramWeiboAuthListener)
  {
    paramWeiboParameters.add("client_id", app_key);
    paramWeiboParameters.add("response_type", "token");
    paramWeiboParameters.add("redirect_uri", redirecturl);
    paramWeiboParameters.add("display", "mobile");
    if ((this.accessToken != null) && (this.accessToken.isSessionValid()))
      paramWeiboParameters.add("access_token", this.accessToken.getToken());
    String str = URL_OAUTH2_ACCESS_AUTHORIZE + "?" + Utility.encodeUrl(paramWeiboParameters);
    if (paramContext.checkCallingOrSelfPermission("android.permission.INTERNET") != 0)
    {
      Utility.showAlert(paramContext, "Error", "Application requires permission to access the Internet");
      return;
    }
    Intent localIntent = new Intent();
    localIntent.setClass(paramContext, WeiboActivity.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("url", str);
    localIntent.putExtras(localBundle);
    paramContext.startActivity(localIntent);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.weibo.sdk.android.Weibo
 * JD-Core Version:    0.6.0
 */