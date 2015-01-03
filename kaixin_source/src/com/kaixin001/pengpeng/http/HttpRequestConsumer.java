package com.kaixin001.pengpeng.http;

import android.content.Context;

public class HttpRequestConsumer
  implements Runnable
{
  private Context mContext;
  private final Queue q;

  public HttpRequestConsumer(Context paramContext, Queue paramQueue)
  {
    this.q = paramQueue;
    this.mContext = paramContext;
  }

  public void run()
  {
    while (true)
    {
      AsyncHttpRequest localAsyncHttpRequest = null;
      try
      {
        localAsyncHttpRequest = this.q.getRequest();
        if ((localAsyncHttpRequest == null) || (localAsyncHttpRequest.isCanceled()) || (!LinkManager.isValidHttpRequest()))
          continue;
        String str = HttpAdapter.doRequest(this.mContext, localAsyncHttpRequest);
        HttpRequestCallback localHttpRequestCallback = localAsyncHttpRequest.getHttpCallBack();
        if (localHttpRequestCallback == null)
          continue;
        localHttpRequestCallback.asyncRequestSuccess(localAsyncHttpRequest, str);
      }
      catch (HttpException localHttpException)
      {
        localHttpException.printStackTrace();
      }
      if ((localAsyncHttpRequest == null) || (localAsyncHttpRequest.getHttpCallBack() == null))
        continue;
      localAsyncHttpRequest.getHttpCallBack().asyncRequestFail(localAsyncHttpRequest, localHttpException);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.http.HttpRequestConsumer
 * JD-Core Version:    0.6.0
 */