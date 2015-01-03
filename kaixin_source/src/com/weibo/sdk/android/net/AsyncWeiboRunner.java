package com.weibo.sdk.android.net;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.WeiboParameters;

public class AsyncWeiboRunner
{
  public static void request(String paramString1, WeiboParameters paramWeiboParameters, String paramString2, RequestListener paramRequestListener)
  {
    new Thread(paramString1, paramString2, paramWeiboParameters, paramRequestListener)
    {
      public void run()
      {
        try
        {
          String str = HttpManager.openUrl(AsyncWeiboRunner.this, this.val$httpMethod, this.val$params, this.val$params.getValue("pic"));
          this.val$listener.onComplete(str);
          return;
        }
        catch (WeiboException localWeiboException)
        {
          this.val$listener.onError(localWeiboException);
        }
      }
    }
    .start();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.weibo.sdk.android.net.AsyncWeiboRunner
 * JD-Core Version:    0.6.0
 */