package com.weibo.sdk.android.net;

import com.weibo.sdk.android.WeiboException;
import java.io.IOException;

public abstract interface RequestListener
{
  public abstract void onComplete(String paramString);

  public abstract void onError(WeiboException paramWeiboException);

  public abstract void onIOException(IOException paramIOException);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.weibo.sdk.android.net.RequestListener
 * JD-Core Version:    0.6.0
 */