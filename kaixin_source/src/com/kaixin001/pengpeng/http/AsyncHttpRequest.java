package com.kaixin001.pengpeng.http;

public class AsyncHttpRequest extends HttpRequest
{
  private HttpRequestCallback mDelegate;

  public AsyncHttpRequest(String paramString, int paramInt, boolean paramBoolean, HttpRequestCallback paramHttpRequestCallback)
  {
    super(paramString, paramInt, paramBoolean);
    this.mDelegate = paramHttpRequestCallback;
  }

  public HttpRequestCallback getHttpCallBack()
  {
    return this.mDelegate;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.http.AsyncHttpRequest
 * JD-Core Version:    0.6.0
 */