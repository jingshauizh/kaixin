package com.tencent.record.debug;

import android.webkit.JavascriptInterface;

public class WnsClientLog extends WnsTracer
{
  public static WnsClientLog instance = null;

  public WnsClientLog()
  {
    this.fileTracer = new a(CLIENT_CONFIG);
  }

  public static void ensureLogsToFile()
  {
    getInstance().flush();
  }

  public static WnsClientLog getInstance()
  {
    if (instance == null)
      monitorenter;
    try
    {
      if (instance == null)
        instance = new WnsClientLog();
      return instance;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  @JavascriptInterface
  public void d(String paramString1, String paramString2)
  {
    trace(2, paramString1, paramString2, null);
  }

  @JavascriptInterface
  public void d(String paramString1, String paramString2, Throwable paramThrowable)
  {
    trace(2, paramString1, paramString2, paramThrowable);
  }

  @JavascriptInterface
  public void e(String paramString1, String paramString2)
  {
    trace(16, paramString1, paramString2, null);
  }

  @JavascriptInterface
  public void e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    trace(16, paramString1, paramString2, paramThrowable);
  }

  public void i(String paramString1, String paramString2)
  {
    trace(4, paramString1, paramString2, null);
  }

  public void i(String paramString1, String paramString2, Throwable paramThrowable)
  {
    trace(4, paramString1, paramString2, paramThrowable);
  }

  @JavascriptInterface
  public void stop()
  {
    monitorenter;
    try
    {
      if (this.fileTracer != null)
      {
        this.fileTracer.a();
        this.fileTracer.b();
      }
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  @JavascriptInterface
  public void v(String paramString1, String paramString2)
  {
    trace(1, paramString1, paramString2, null);
  }

  @JavascriptInterface
  public void v(String paramString1, String paramString2, Throwable paramThrowable)
  {
    trace(1, paramString1, paramString2, paramThrowable);
  }

  public void w(String paramString1, String paramString2)
  {
    trace(8, paramString1, paramString2, null);
  }

  public void w(String paramString1, String paramString2, Throwable paramThrowable)
  {
    trace(8, paramString1, paramString2, paramThrowable);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.record.debug.WnsClientLog
 * JD-Core Version:    0.6.0
 */