package com.tencent.tauth;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class QQToken
{
  private Context appContext;
  private String mAccessToken;
  private String mAppId;
  private long mExpiresIn = -1L;
  private String mOpenId;

  public QQToken(String paramString, Context paramContext)
  {
    this.mAppId = paramString;
    this.appContext = paramContext;
  }

  public void copyData(QQToken paramQQToken)
  {
    this.mAppId = paramQQToken.mAppId;
    this.mAccessToken = paramQQToken.mAccessToken;
    this.mExpiresIn = paramQQToken.mExpiresIn;
    this.appContext = paramQQToken.appContext;
    this.mOpenId = paramQQToken.mOpenId;
  }

  @JavascriptInterface
  public String getAccessToken()
  {
    return this.mAccessToken;
  }

  @JavascriptInterface
  public Context getAppContext()
  {
    return this.appContext;
  }

  @JavascriptInterface
  public String getAppId()
  {
    return this.mAppId;
  }

  @JavascriptInterface
  public long getExpiresIn()
  {
    return this.mExpiresIn;
  }

  @JavascriptInterface
  public String getOpenId()
  {
    return this.mOpenId;
  }

  @JavascriptInterface
  public boolean isSessionValid()
  {
    return (this.mAccessToken != null) && (System.currentTimeMillis() < this.mExpiresIn);
  }

  @JavascriptInterface
  public void setAccessToken(String paramString1, String paramString2)
  {
    this.mExpiresIn = 0L;
    this.mAccessToken = null;
    if (paramString2 == null)
      paramString2 = "0";
    this.mExpiresIn = (System.currentTimeMillis() + 1000L * Long.parseLong(paramString2));
    this.mAccessToken = paramString1;
  }

  @JavascriptInterface
  public void setAppContext(Context paramContext)
  {
    this.appContext = paramContext;
  }

  public void setAppId(String paramString)
  {
    this.mAppId = paramString;
  }

  @JavascriptInterface
  public void setOpenId(String paramString)
  {
    this.mOpenId = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.QQToken
 * JD-Core Version:    0.6.0
 */