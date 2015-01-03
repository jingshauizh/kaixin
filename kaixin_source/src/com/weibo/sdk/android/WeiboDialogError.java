package com.weibo.sdk.android;

public class WeiboDialogError extends Throwable
{
  private static final long serialVersionUID = 1L;
  private int mErrorCode;
  private String mFailingUrl;

  public WeiboDialogError(String paramString1, int paramInt, String paramString2)
  {
    super(paramString1);
    this.mErrorCode = paramInt;
    this.mFailingUrl = paramString2;
  }

  int getErrorCode()
  {
    return this.mErrorCode;
  }

  String getFailingUrl()
  {
    return this.mFailingUrl;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.weibo.sdk.android.WeiboDialogError
 * JD-Core Version:    0.6.0
 */