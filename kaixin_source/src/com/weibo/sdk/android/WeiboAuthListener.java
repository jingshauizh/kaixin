package com.weibo.sdk.android;

import android.os.Bundle;

public abstract interface WeiboAuthListener
{
  public abstract void onCancel();

  public abstract void onComplete(Bundle paramBundle);

  public abstract void onError(WeiboDialogError paramWeiboDialogError);

  public abstract void onWeiboException(WeiboException paramWeiboException);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.weibo.sdk.android.WeiboAuthListener
 * JD-Core Version:    0.6.0
 */