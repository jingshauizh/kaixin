package com.kaixin001.pengpeng.http;

import android.content.Context;

public class LinkManager
{
  private static final String TAG = "LinkManager";
  private static final int THREAD_NUM = 1;
  private static volatile LinkManager instance;
  private static boolean isValidHttpRequest = false;
  private Thread[] _consumerThreads = new Thread[1];
  private final Queue mHttpRequestQ = Queue.getInstance();

  private LinkManager(Context paramContext)
  {
    for (int i = 0; ; i++)
    {
      if (i >= 1)
        return;
      this._consumerThreads[i] = new Thread(new HttpRequestConsumer(paramContext, this.mHttpRequestQ));
      this._consumerThreads[i].start();
    }
  }

  public static LinkManager getInstance(Context paramContext)
  {
    if (instance == null)
      monitorenter;
    try
    {
      if (instance == null)
        instance = new LinkManager(paramContext);
      return instance;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static boolean isValidHttpRequest()
  {
    return isValidHttpRequest;
  }

  public boolean cancelRequest(AsyncHttpRequest paramAsyncHttpRequest)
  {
    if (paramAsyncHttpRequest != null)
    {
      paramAsyncHttpRequest.setCancelRequest(true);
      return true;
    }
    return false;
  }

  public void removeAllRequestFromQ()
  {
    this.mHttpRequestQ.clearQ();
    isValidHttpRequest = false;
  }

  public void shutDown()
  {
    this.mHttpRequestQ.clearQ();
    for (int i = 0; ; i++)
    {
      if (i >= 1)
      {
        this._consumerThreads = null;
        return;
      }
      this._consumerThreads[i].interrupt();
    }
  }

  public void submitRequest(AsyncHttpRequest paramAsyncHttpRequest)
  {
    if (paramAsyncHttpRequest != null)
    {
      if (!isValidHttpRequest)
        isValidHttpRequest = true;
      this.mHttpRequestQ.addRequest(paramAsyncHttpRequest);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.http.LinkManager
 * JD-Core Version:    0.6.0
 */