package com.kaixin001.pengpeng.http;

import java.util.Vector;

public class Queue
{
  private static final int MAX_SIZE = 14;
  private static Vector<AsyncHttpRequest> httpJobs = new Vector();
  private static volatile Queue instance;

  public static Queue getInstance()
  {
    if (instance == null)
      monitorenter;
    try
    {
      if (instance == null)
        instance = new Queue();
      return instance;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void addRequest(AsyncHttpRequest paramAsyncHttpRequest)
  {
    monitorenter;
    try
    {
      while (true)
      {
        if (httpJobs.size() < 14)
        {
          httpJobs.add(paramAsyncHttpRequest);
          notifyAll();
          return;
        }
        try
        {
          wait();
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clearQ()
  {
    monitorenter;
    try
    {
      httpJobs.removeAllElements();
      notifyAll();
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public AsyncHttpRequest getRequest()
  {
    monitorenter;
    try
    {
      while (true)
      {
        if (!httpJobs.isEmpty())
        {
          AsyncHttpRequest localAsyncHttpRequest = (AsyncHttpRequest)httpJobs.remove(0);
          notifyAll();
          return localAsyncHttpRequest;
        }
        try
        {
          wait();
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.http.Queue
 * JD-Core Version:    0.6.0
 */