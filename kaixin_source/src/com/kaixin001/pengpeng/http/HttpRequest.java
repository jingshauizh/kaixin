package com.kaixin001.pengpeng.http;

public class HttpRequest
{
  public static int DEFAULT_TIME_OUT = 15000;
  private static long mReqId = 0L;
  private boolean mAllowCaching = false;
  private volatile boolean mCanceled = false;
  private String mReqTag;
  private double mStartTime;
  private int mTimeout;
  private String mUrl;

  public HttpRequest(String paramString, int paramInt, boolean paramBoolean)
  {
    mReqId = 1L + mReqId;
    this.mUrl = paramString;
    if (paramInt < 0);
    for (this.mTimeout = DEFAULT_TIME_OUT; ; this.mTimeout = paramInt)
    {
      this.mAllowCaching = paramBoolean;
      return;
    }
  }

  public long getRequestId()
  {
    return mReqId;
  }

  public String getRequestTag()
  {
    return this.mReqTag;
  }

  public double getStartTime()
  {
    return this.mStartTime;
  }

  public int getTimeout()
  {
    return this.mTimeout;
  }

  public String getUrl()
  {
    return this.mUrl;
  }

  public boolean isAllowCaching()
  {
    return this.mAllowCaching;
  }

  public boolean isCanceled()
  {
    return this.mCanceled;
  }

  public void setAllowCaching(boolean paramBoolean)
  {
    this.mAllowCaching = paramBoolean;
  }

  public void setCancelRequest(boolean paramBoolean)
  {
    this.mCanceled = paramBoolean;
  }

  public void setRequestTag(String paramString)
  {
    this.mReqTag = paramString;
  }

  public void setStartTime(double paramDouble)
  {
    this.mStartTime = paramDouble;
  }

  public void setTimeout(int paramInt)
  {
    this.mTimeout = paramInt;
  }

  public void setUrl(String paramString)
  {
    this.mUrl = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.http.HttpRequest
 * JD-Core Version:    0.6.0
 */